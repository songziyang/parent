package com.ydzb.account.service.impl;

import com.ydzb.account.entity.*;
import com.ydzb.account.repository.WmProductInfoRepository;
import com.ydzb.account.service.*;
import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.LockModeType;
import java.math.BigDecimal;

/**
 * 定存本息复投service实现
 */
@Service("ragularAllRecastService")
public class WmRagularAllRecastServiceImpl implements IWmPeriodicProductHandleService {

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
    private IWmRagularExpireService ragularExpireService;
    @Autowired
    private IWmRedpacketUserService redpacketUserService;
    @Autowired
    private IWmJxFreezeRecordService jxFreezeRecordService;
    @Autowired
    private IWmCmsBuyQueueService wmCmsBuyQueueService;
    @Autowired
    private IWmCmsCreditorCanmatchTotalService cmsCreditorCanmatchTotalService;
    @Autowired
    private IWmUserFuncGrandProfitRecordService userFuncGrandProfitRecordService;
    @Autowired
    private IWmRagularRefundService ragularRefundService;
    @Autowired
    private IWmCmsUserCreditorRecordService cmsUserCreditorRecordService;

    /**
     * 执行老用户结算
     * @param periodicProductRefund 周期产品还息记录
     * @param periodicProductAccount 周期产品账户
     * @return 复投生成的新ragularUserAccount
     * @throws Exception
     */
    @Override
    public WmRagularUserAccount executeOldUserSettlement(IWmPeriodicProductRefund periodicProductRefund, IWmPeriodicProductAccount periodicProductAccount) throws Exception {

        WmRagularRefund ragularRefund = (WmRagularRefund)periodicProductRefund;
        WmRagularUserAccount ragularUserAccount = (WmRagularUserAccount)periodicProductAccount;
        if (ragularRefund != null && ragularUserAccount != null) {

            WmProductInfo productInfo = wmProductInfoRepository.queryById(ragularUserAccount.getProductId(), WmProductInfo.class, LockModeType.NONE);
            Long userId = ragularRefund.getUserId();
            BigDecimal income = ragularRefund.getFund() == null? BigDecimal.ZERO: ragularRefund.getFund();

            //更新用户定存购买交易
            ragularUserAccountService.decreaseRagularSettlementAccount(ragularUserAccount, ragularUserAccount.getBuyFund(), ragularRefund.getFund(), WmRagularUserAccount.STATUS_EXPIRE_EXPIRE);
            //创建定存宝到期日志
            WmRagularOverLog ragularOverLog = ragularOverLogService.createOne(userId, ragularUserAccount.getId(), WmRagularOverLog.TYPE_EXPIRE,
                    ragularUserAccount.getBuyFund(), DateUtil.getSystemTimeSeconds(), ragularUserAccount.getGrandFund());
            Long logId = ragularOverLog.getId();
            //创建定存宝到期交易记录
            ragularTradeRecordService.createOne(productInfo == null? null: productInfo.getName() + "到期", ragularUserAccount.getDays(), ragularUserAccount.getBuyFund(), DateUtil.getSystemTimeSeconds(),
                    WmRagularTradeRecored.TYPE_EXPIRE, userId, WmRagularTradeRecored.FUNDSOURCE_USABLE, logId);
            //增加用户总资金
            userMoneyService.increaseTotalFund(userId, income);
            //增加用户收益
            userIncomeService.increaseRagularAllExpireIncome(userId, ragularRefund.getFund(), ragularRefund.getRedpacketFund(), ragularRefund.getVipFund(), ragularRefund.getVouchersFund());
            //创建用户收益记录
            userIncomeRecordService.createOne(userId, getIncomeName(ragularRefund, productInfo), ragularRefund.getFund(), WmUserIncomeRecord.RAGULAR, DateUtil.getSystemTimeSeconds());
            //增加定存宝投资
            userInvestInfoService.increaseRagularInvest(userId, income);
            //复投
            WmRagularUserAccount newRagularUserAccount = ragularExpireService.doExpire(productInfo, userId, ragularUserAccount.getBuyFund().add(income), ragularUserAccount.getExpireNum(), WmRagularUserAccount.EXPIREMODE_PRINCIPAL_INTEREST, WmRagularUserAccount.INCOMEMODE_EXPIRE);
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
            return newRagularUserAccount;
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
        if (user != null && ragularRefund != null && ragularUserAccount != null) {

            //更改状态
            ragularUserAccountService.updateStatus(ragularUserAccount, WmRagularUserAccount.STATUS_PRINCIAPAL_INTEREST_EXPIRING);
            //查询该比定存使用的加息券以及定存红包
            Long investRedpacketId = redpacketUserService.queryRedpacketId(user.getId(), ragularUserAccount.getId(), WmRedpacketUsed.PRODUCTTYPE_RAGULAR);
            Long ragularRedpacketId = redpacketUserService.queryRedpacketId(user.getId(), ragularUserAccount.getId(), WmRedpacketUsed.PRODUCTTYPE_VOUCHER);
            //新增交易冻结记录
            WmJxFreezeRecord record = jxFreezeRecordService.createOne(user.getId(), user.getAccountId(), ragularUserAccount.getBuyFund(), ragularRefund.getFund(), WmJxFreezeRecord.TYPE_PROFIT,
                    WmJxFreezeRecord.LINKTYPE_RAGULAR, ragularUserAccount.getId(), ragularUserAccount.getProductId(), investRedpacketId == null ? null : investRedpacketId.toString(),
                    ragularRedpacketId == null ? null : ragularRedpacketId.toString(), WmRagularUserAccount.EXPIREMODE_PRINCIPAL_INTEREST, WmJxFreezeRecord.DEVICE_SYSTEM, null, null, null,
                    WmJxFreezeRecord.STATE_UNDERHANDLE, DateUtil.getSystemTimeSeconds(),null);
            //新增购买队列
            wmCmsBuyQueueService.createOne(user.getId(), record == null? null: record.getId(), null, ragularRefund.getFund(), WmCmsBuyQueue.TYPE_PROFIT, null,
                    WmCmsBuyQueue.STATUS_UNDERMATCH, null);
            //增加债券总数-购买总数
            cmsCreditorCanmatchTotalService.increaseBuy(ragularRefund.getFund());
            //增加购买冻结金额
            userInvestInfoService.increaseAllBuyFreeze(user.getId(), ragularRefund.getFund());
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
                    WmRagularUserAccount newRagularUserAccount = this.executeOldUserSettlement(ragularRefund, ragularUserAccount);
                    //减掉与处理时增加的冻结资金
                    userInvestInfoService.increaseAllBuyFreeze(ragularUserAccount.getUserId(),
                            ragularRefund.getFund() == null? BigDecimal.ZERO: ragularRefund.getFund().multiply(new BigDecimal(-1)));
                    //更改用户债权中的交易id
                    cmsUserCreditorRecordService.updateDealId(newRagularUserAccount.getId(), ragularUserAccount.getId(), ragularUserAccount.getUserId(), WmCmsUserCreditorRecord.PRODUCTTYPE_RAGULAR);
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