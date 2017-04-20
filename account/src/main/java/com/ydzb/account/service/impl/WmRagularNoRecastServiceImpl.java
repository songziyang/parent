package com.ydzb.account.service.impl;

import com.ydzb.account.context.WmPlatformRecordLinkType;
import com.ydzb.account.entity.*;
import com.ydzb.account.repository.WmProductInfoRepository;
import com.ydzb.account.service.*;
import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.LockModeType;
import java.math.BigDecimal;

/**
 * 定存非复投service实现
 */
@Service("ragularNoRecastService")
public class WmRagularNoRecastServiceImpl implements IWmPeriodicProductHandleService {

    @Autowired
    private IWmUserIncomeService userIncomeService;
    @Autowired
    private IWmUserIncomeRecordService userIncomeRecordService;
    @Autowired
    private IWmUserInvestInfoService userInvestInfoService;
    @Autowired
    private IWmRagularUserAccountService ragularUserAccountService;
    @Autowired
    private IWmRagularOverLogService ragularOverLogService;
    @Autowired
    private IWmRagularTradeRecordService ragularTradeRecordService;
    @Autowired
    private WmProductInfoRepository wmProductInfoRepository;
    @Autowired
    private IWmUserMoneyService userMoneyService;
    @Autowired
    private IWmUserFundInLogService userFundInLogService;
    @Autowired
    private IWmUserFundRecordService userFundRecordService;
    @Autowired
    private IWmPlatformUserService wmPlatformUserService;
    @Autowired
    private IWmRedpacketUserService redpacketUserService;
    @Autowired
    private IWmJxFreezeRecordService jxFreezeRecordService;
    @Autowired
    private IWmCmsRedeemQueueService wmCmsRedeemQueueService;
    @Autowired
    private IWmCmsCreditorCanmatchTotalService cmsCreditorCanmatchTotalService;
    @Autowired
    private IWmUserFuncGrandProfitRecordService userFuncGrandProfitRecordService;
    @Autowired
    private IWmRagularRefundService ragularRefundService;

    /**
     * 执行老用户结算
     * @param periodicProductRefund 周期产品还息记录
     * @param periodicProductAccount 周期产品账户
     * @return 复投生称的新ragularUserAccount，此处为Null
     * @throws Exception
     */
    @Override
    public WmRagularUserAccount executeOldUserSettlement(IWmPeriodicProductRefund periodicProductRefund, IWmPeriodicProductAccount periodicProductAccount) throws Exception{

        WmRagularRefund ragularRefund = (WmRagularRefund)periodicProductRefund;
        WmRagularUserAccount ragularUserAccount = (WmRagularUserAccount)periodicProductAccount;

        if (ragularRefund != null && ragularUserAccount != null) {
            Long userId = ragularRefund.getUserId();
            WmProductInfo productInfo = wmProductInfoRepository.queryById(ragularUserAccount.getProductId(), WmProductInfo.class, LockModeType.NONE);
            WmUserMoney userMoney = userMoneyService.queryOne(userId, LockModeType.PESSIMISTIC_WRITE);

            //创建定存宝到期日志
            WmRagularOverLog ragularOverLog = ragularOverLogService.createOne(userId, ragularUserAccount.getId(), WmRagularOverLog.TYPE_EXPIRE,
                    ragularUserAccount.getBuyFund(), DateUtil.getSystemTimeSeconds(), ragularUserAccount.getGrandFund());
            Long logId = ragularOverLog.getId();
            //创建定存宝到期交易记录
            ragularTradeRecordService.createOne(productInfo == null? null: productInfo.getName() + "到期", ragularUserAccount.getDays(), ragularUserAccount.getBuyFund(), DateUtil.getSystemTimeSeconds(),
                    WmRagularTradeRecored.TYPE_EXPIRE, userId, WmRagularTradeRecored.FUNDSOURCE_USABLE, logId);
            //创建收益记录（定存宝收益）
            userIncomeRecordService.createOne(userId, getIncomeName(ragularRefund, productInfo), ragularRefund.getFund(), WmUserIncomeRecord.RAGULAR, DateUtil.getSystemTimeSeconds());
            //创建入账日志（定存宝收益）
            WmUserFundInLog incomeFundInLog = userFundInLogService.createOne(userId, WmUserFundInLog.TYPE_RAGULAR_INCOME, DateUtil.getSystemTimeSeconds(), BigDecimal.ZERO, ragularRefund.getFund(),
                    userMoney.getUsableFund(), null, ragularUserAccount.getId());
            //创建资金记录（定存宝收益，入账）
            userFundRecordService.createOne(userId, getIncomeName(ragularRefund, productInfo), ragularRefund.getFund(), incomeFundInLog == null? null: incomeFundInLog.getId(), WmUserFundRecord.TYPE_INFUND,
                    WmUserFundRecord.FUNDTYPE_INCOME, ragularRefund.getFund(), DateUtil.getSystemTimeSeconds());
            //定存本金进账日志
            WmUserFundInLog principalFundInLog = userFundInLogService.createOne(userId, WmUserFundInLog.TYPE_RAGULAR_EXPIRE, DateUtil.getSystemTimeSeconds(), ragularUserAccount.getBuyFund(), BigDecimal.ZERO,
                    userMoney.getUsableFund(), null, ragularUserAccount.getId());
            //创建资金记录（定存宝本金，入账）
            userFundRecordService.createOne(userId, FundFlow.R_OVER, ragularRefund.getFund(), principalFundInLog == null? null: principalFundInLog.getId(), WmUserFundRecord.TYPE_INFUND,
                    WmUserFundRecord.FUNDTYPE_EXPIE_PRINCIPAL, userMoney.getUsableFund(), DateUtil.getSystemTimeSeconds());
            //增加用户定存结算收益
            userIncomeService.increaseRagularSettlementIncome(userId, ragularRefund.getFund(), ragularRefund.getRedpacketFund(), ragularRefund.getVipFund(), ragularRefund.getVouchersFund());
            //减少用户投资
            userInvestInfoService.decreaseRagularInvest(userId, ragularUserAccount.getBuyFund());
            //更新用户定存购买交易
            ragularUserAccountService.decreaseRagularSettlementAccount(ragularUserAccount, ragularUserAccount.getBuyFund(), ragularRefund.getFund(), WmRagularUserAccount.STATUS_EXPIRE_EXPIRE);
            //更新用户资金（收益，本金）
            userMoneyService.increaseRagularNoRecastFund(userId, ragularUserAccount.getBuyFund(), ragularRefund.getFund());
            //定存到期
            wmPlatformUserService.redeem(ragularUserAccount.getBuyFund(), ragularUserAccount.getUserId(), WmPlatformRecordLinkType.EXPIRE_RAGULAR, WmPlatformRecordLinkType.EXPIRE_RAGULAR.getDesc());
            //加息券收益
            userFuncGrandProfitRecordService.createOne(ragularUserAccount.getUserId(), ragularUserAccount.getProductId(), ragularRefund.getRedpacketFund(),
                    WmUserFuncGrandProfitRecord.PTYPE_RAGULAR, WmUserFuncGrandProfitRecord.TYPE_INTEREST, DateUtil.getSystemTimeSeconds());
            //VIP加息收益
            userFuncGrandProfitRecordService.createOne(ragularUserAccount.getUserId(), ragularUserAccount.getProductId(), ragularRefund.getVipFund(),
                    WmUserFuncGrandProfitRecord.PTYPE_RAGULAR, WmUserFuncGrandProfitRecord.TYPE_VIP, DateUtil.getSystemTimeSeconds());
            //代金券收益
            userFuncGrandProfitRecordService.createOne(ragularUserAccount.getUserId(), ragularUserAccount.getProductId(), ragularRefund.getVouchersFund(),
                    WmUserFuncGrandProfitRecord.PTYPE_RAGULAR, WmUserFuncGrandProfitRecord.TYPE_VOUCHER, DateUtil.getSystemTimeSeconds());
            //更新状态为已经结算
            ragularRefundService.updateStatus(ragularRefund, WmRagularRefund.STATE_REFUND);

        }
        return null;
    }

    /**
     * 执行存管用户预处理
     * @param user 用户
     * @param periodicProductRefund 周期产品还息记录
     * @param periodicProductAccount 周期产品账户
     * @throws Exception
     */
    @Override
    public void executeDepositoryUserPretreatment(WmUser user, IWmPeriodicProductRefund periodicProductRefund, IWmPeriodicProductAccount periodicProductAccount) throws Exception {

        WmRagularRefund ragularRefund = (WmRagularRefund)periodicProductRefund;
        WmRagularUserAccount ragularUserAccount = (WmRagularUserAccount)periodicProductAccount;
        if (ragularRefund != null && ragularUserAccount != null && user != null) {

            Long userId = user.getId();
            //更改定存宝账户状态
            ragularUserAccountService.updateStatus(ragularUserAccount, WmRagularUserAccount.STATUS_EXPIRE_REFUNDING);

            //查询该比定存使用的加息券以及定存红包
            Long investRedpacketId = redpacketUserService.queryRedpacketId(userId, ragularUserAccount.getId(), WmRedpacketUsed.PRODUCTTYPE_RAGULAR);
            Long ragularRedpacketId = redpacketUserService.queryRedpacketId(userId, ragularUserAccount.getId(), WmRedpacketUsed.PRODUCTTYPE_VOUCHER);
            //新增交易冻结记录
            WmJxFreezeRecord record = jxFreezeRecordService.createOne(userId, user.getAccountId(), ragularUserAccount.getBuyFund(), ragularRefund.getFund(), WmJxFreezeRecord.TYPE_EXPIRE,
                    WmJxFreezeRecord.LINKTYPE_RAGULAR, ragularUserAccount.getId(), ragularUserAccount.getProductId(), investRedpacketId == null ? null : investRedpacketId.toString(),
                    ragularRedpacketId == null ? null : ragularRedpacketId.toString(), WmRagularUserAccount.EXPIREMODE_NONE, 5, null, null, null,
                    WmJxFreezeRecord.STATE_UNDERHANDLE, DateUtil.getSystemTimeSeconds(),
                    null);
            //新增赎回队列
            wmCmsRedeemQueueService.createOne(userId, null, ragularUserAccount.getBuyFund(),
                    WmCmsRedeemQueue.PRODUCTTYPE_RAGULAR, ragularRefund.getFund(), WmCmsRedeemQueue.TYPE_EXPIRE, WmCmsRedeemQueue.STATUS_UNDERHANDLE,
                    WmCmsRedeemQueue.WARN_NORMAL, record == null ? null : record.getId(), null);
            //增加总冻结赎回金额
            userInvestInfoService.increaseAllRedeemFreeze(userId, ragularUserAccount.getBuyFund());
            //增加债权总数-赎回总数
            cmsCreditorCanmatchTotalService.increaseRedeem(ragularUserAccount.getBuyFund());
        }
    }

    /**
     * 执行存管用户预处理成功
     * @param jxFreezeRecordId 即信交易冻结记录
     * @throws Exception
     */
    @Override
    public void executeDepositoryUserPretreatmentSuccess(Long jxFreezeRecordId) throws Exception {
        if (jxFreezeRecordId != null) {
            WmJxFreezeRecord jxFreezeRecord = jxFreezeRecordService.queryById(jxFreezeRecordId, LockModeType.NONE);
            if (jxFreezeRecord != null) {
                WmRagularUserAccount ragularUserAccount = ragularUserAccountService.queryById(jxFreezeRecord.getLinkId());
                WmRagularRefund ragularRefund = ragularRefundService.queryByAccount(jxFreezeRecord.getLinkId(), WmRagularRefund.ORG_RAGULAR, WmRagularRefund.EXPIRE_LAST);
                if (ragularUserAccount != null && ragularRefund != null) {
                    //过程同老用户结算
                    this.executeOldUserSettlement(ragularRefund, ragularUserAccount);
                    //减掉与处理时增加的冻结资金
                    userInvestInfoService.increaseAllRedeemFreeze(ragularUserAccount.getUserId(),
                            ragularUserAccount.getBuyFund() == null? BigDecimal.ZERO: ragularUserAccount.getBuyFund().multiply(new BigDecimal(-1)));
                    //更改冻结记录状态
                    jxFreezeRecordService.updateStatus(jxFreezeRecord, WmJxFreezeRecord.STATE_HANDLEFINISH);
                }
            }
        }
    }

    /**
     * 获得收益名称
     * @param ragularRefund
     * @param productInfo
     * @return
     */
    private String getIncomeName(WmRagularRefund ragularRefund, WmProductInfo productInfo) {
        //收益名称
        String productInfoNames;
        if (ragularRefund.getStage() == 1) {
            productInfoNames = productInfo.getName() + FundFlow.INCOME_;
        } else {
            productInfoNames = productInfo.getName() + FundFlow.INCOME;
        }
        return productInfoNames;
    }
}