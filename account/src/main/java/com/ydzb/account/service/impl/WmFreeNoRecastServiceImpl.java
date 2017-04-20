package com.ydzb.account.service.impl;

import com.ydzb.account.entity.*;
import com.ydzb.account.repository.WmFreeUserAccountRepository;
import com.ydzb.account.repository.WmProductInfoRepository;
import com.ydzb.account.service.*;
import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.LockModeType;
import java.math.BigDecimal;

/**
 * 随心存非复投service实现
 */
@Service("freeNoRecastService")
public class WmFreeNoRecastServiceImpl implements IWmPeriodicProductHandleService {

    @Autowired
    private IWmUserFuncGrandProfitRecordService userFuncGrandProfitRecordService;
    @Autowired
    private WmFreeUserAccountRepository freeUserAccountRepository;
    @Autowired
    private IWmFreeUserAccountService freeUserAccountService;
    @Autowired
    private IWmFreeOverLogService freeOverLogService;
    @Autowired
    private IWmFreeTradeRecordService freeTradeRecordService;
    @Autowired
    private IWmUserIncomeRecordService userIncomeRecordService;
    @Autowired
    private IWmUserFundInLogService userFundInLogService;
    @Autowired
    private IWmUserMoneyService userMoneyService;
    @Autowired
    private IWmUserFundRecordService userFundRecordService;
    @Autowired
    private IWmUserIncomeService userIncomeService;
    @Autowired
    private IWmUserInvestInfoService userInvestInfoService;
    @Autowired
    private IWmFreeRefundService freeRefundService;
    @Autowired
    private IWmJxFreezeRecordService jxFreezeRecordService;
    @Autowired
    private IWmCmsRedeemQueueService wmCmsRedeemQueueService;
    @Autowired
    private WmProductInfoRepository productInfoRepository;
    @Autowired
    private IWmCmsCreditorCanmatchTotalService cmsCreditorCanmatchTotalService;

    /**
     * 执行老用户结算
     * @param periodicProductRefund 周期产品还息记录
     * @param periodicProductAccount 周期产品账户
     * @throws Exception
     */
    @Override
    public IWmPeriodicProductAccount executeOldUserSettlement(IWmPeriodicProductRefund periodicProductRefund, IWmPeriodicProductAccount periodicProductAccount) throws Exception{

        WmFreeUserAccount freeUserAccount = (WmFreeUserAccount)periodicProductAccount;
        WmFreeRefund freeRefund = (WmFreeRefund)periodicProductRefund;
        if (freeUserAccount != null && freeRefund != null) {
            //查询产品
            WmProductInfo productInfo = productInfoRepository.queryById(freeUserAccount.getProductId(), LockModeType.NONE);
            if (productInfo == null) productInfo = new WmProductInfo();
            WmUserMoney userMoney = userMoneyService.queryOne(freeUserAccount.getUserId(), LockModeType.PESSIMISTIC_WRITE);
            //更新账户状态为到期
            freeUserAccountService.updateStatus(freeUserAccount, WmFreeUserAccount.STATUS_EXPIRE);
            //创建到期日志
            WmFreeOverLog freeOverLog = freeOverLogService.createOne(freeUserAccount.getUserId(), freeUserAccount.getId(), WmFreeOverLog.TYPE_EXPIRE,
                    freeUserAccount.getBuyFund(), DateUtil.getSystemTimeSeconds(), freeUserAccount.getGrandFund());
            //创建到期交易记录
            freeTradeRecordService.createOne(productInfo.getName() + FundFlow.EXPIRE, freeUserAccount.getDays(), freeUserAccount.getBuyFund(),
                    DateUtil.getSystemTimeSeconds(), WmFreeTradeRecored.TYPE_EXPIRE, freeUserAccount.getUserId(), WmFreeTradeRecored.FUNDSOURCE_UNSABLE,
                    freeOverLog == null? null: freeOverLog.getId());
            //创建收益记录
            userIncomeRecordService.createOne(freeUserAccount.getUserId(), productInfo.getName() +  FundFlow.INCOME_, freeRefund.getFund(),
                    WmUserIncomeRecord.PTYPE_FREE, DateUtil.getSystemTimeSeconds());
            //创建收益入账日志
            WmUserFundInLog incomeFundInLog = userFundInLogService.createOne(freeUserAccount.getUserId(), WmUserFundInLog.TYPE_FREE_EXPIRE, DateUtil.getSystemTimeSeconds(),
                    BigDecimal.ZERO, freeRefund.getFund(), userMoney.getUsableFund(), null, freeUserAccount.getId());
            //创建资金记录（收益, 入账）
            userFundRecordService.createOne(freeUserAccount.getUserId(), productInfo.getName() +  FundFlow.INCOME_, freeRefund.getFund(), incomeFundInLog == null? null: incomeFundInLog.getId(),
                    WmUserFundRecord.TYPE_INFUND, WmUserFundRecord.FUNDTYPE_INCOME, userMoney.getUsableFund(), DateUtil.getSystemTimeSeconds());
            //创建本金入账日志
            WmUserFundInLog principalFundInLog = userFundInLogService.createOne(freeUserAccount.getUserId(), WmUserFundInLog.TYPE_FREE_EXPIRE, DateUtil.getSystemTimeSeconds(),
                    freeUserAccount.getBuyFund(), BigDecimal.ZERO, userMoney.getUsableFund(), null, freeUserAccount.getId());
            //创建资金记录（本金, 入账）
            userFundRecordService.createOne(freeUserAccount.getUserId(), productInfo.getName() + FundFlow.INCOME_, freeUserAccount.getBuyFund(), principalFundInLog == null? null: principalFundInLog.getId(),
                    WmUserFundRecord.TYPE_INFUND, WmUserFundRecord.FUNDTYPE_EXPIE_PRINCIPAL, userMoney.getUsableFund(), DateUtil.getSystemTimeSeconds());

            //更新用户资金
            userMoneyService.increaseFreeExpireFund(freeUserAccount.getUserId(), freeUserAccount.getBuyFund(), freeRefund.getFund());
            //更新用户收益
            userIncomeService.increaseFreeExpire(freeUserAccount.getUserId(), freeRefund.getFund(), freeRefund.getVipFund());
            //减少随心存投资
            userInvestInfoService.decreaseRagularInvest(freeUserAccount.getUserId(), freeUserAccount.getBuyFund());
            //更新还息记录状态
            freeRefundService.updateState(freeRefund, WmFreeRefund.STATE_EXPIRE);
            //VIP加息记录
            //创建用户赠予（红包）体验金收益记录
            userFuncGrandProfitRecordService.createOne(freeUserAccount.getUserId(), productInfo.getId(), freeRefund.getVipFund(), WmUserFuncGrandProfitRecord.PTYPE_FREE, WmUserFuncGrandProfitRecord.TYPE_VIP, DateUtil.getSystemTimeSeconds());
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

        WmFreeUserAccount freeUserAccount = (WmFreeUserAccount)periodicProductAccount;
        WmFreeRefund freeRefund = (WmFreeRefund)periodicProductRefund;
        if (user != null && freeUserAccount != null && freeUserAccount.getExpireMode() == 0 && freeUserAccount.getIncomeMode() == 0) {

            //更改随心存账户状态
            freeUserAccountService.updateStatus(freeUserAccount, WmFreeUserAccount.STATUS_EXPIRE_REFUNDING);
            //创建交易冻结记录
            WmJxFreezeRecord record = jxFreezeRecordService.createOne(user.getId(), user.getAccountId(), freeUserAccount.getBuyFund(), freeRefund.getFund(), WmJxFreezeRecord.TYPE_EXPIRE,
                    WmJxFreezeRecord.LINKTYPE_FREE, freeUserAccount.getId(), freeUserAccount.getProductId(), null,
                    null, WmJxFreezeRecord.EXPIREMODE_NONE, WmJxFreezeRecord.DEVICE_SYSTEM,
                    freeUserAccount.getBuyTime() == null? null: DateUtil.convertToDay(freeUserAccount.getBuyTime()),
                    freeUserAccount.getExpireTime(), freeUserAccount.getBuyCopies() == null? null: String.valueOf(freeUserAccount.getBuyCopies()),
                    WmJxFreezeRecord.STATE_UNDERHANDLE, DateUtil.getSystemTimeSeconds(),
                    null);
            //创建赎回队列列表
            wmCmsRedeemQueueService.createOne(user.getId(), null, freeUserAccount.getBuyFund(),
                    WmCmsRedeemQueue.PRODUCTTYPE_FREERAGULAR, freeRefund.getFund(), WmCmsRedeemQueue.TYPE_EXPIRE, WmCmsRedeemQueue.STATUS_UNDERHANDLE,
                    null, record == null? null: record.getId(), null);
            //更新还息记录状态
            freeRefundService.updateState(freeRefund, WmFreeRefund.STATE_REFUNDING);
            //增加定存赎回冻结金额
            userInvestInfoService.increaseAllRedeemFreeze(freeUserAccount.getUserId(), freeUserAccount.getAllFund());
            //增加债券总数-赎回金额
            cmsCreditorCanmatchTotalService.increaseRedeem(freeUserAccount.getAllFund());
        }
    }

    /**
     * 执行存管用户预处理成功
     * @param jxFreezeRecordId 即信交易冻结记录
     * @throws Exception
     */
    @Override
    public void executeDepositoryUserPretreatmentSuccess(Long jxFreezeRecordId) throws Exception {
        WmJxFreezeRecord jxFreezeRecord = jxFreezeRecordService.queryById(jxFreezeRecordId, LockModeType.NONE);
        if (jxFreezeRecord != null) {
            Long freeUserAccountId = jxFreezeRecord.getLinkId();
            WmFreeUserAccount freeUserAccount = freeUserAccountRepository.queryById(freeUserAccountId, LockModeType.PESSIMISTIC_WRITE);
            if (freeUserAccount != null) {
                WmFreeRefund freeRefund = freeRefundService.queryOneByLinkId(freeUserAccount.getId(), LockModeType.PESSIMISTIC_WRITE);
                //流程同老用户结算流程
                executeOldUserSettlement(freeRefund, freeUserAccount);
                //减掉预处理中增加的冻结金额
                userInvestInfoService.increaseAllRedeemFreeze(freeUserAccount.getUserId(),
                        freeUserAccount.getAllFund() == null? BigDecimal.ZERO: freeUserAccount.getAllFund().multiply(new BigDecimal(-1)));
                //更改交易冻结记录状态
                jxFreezeRecordService.updateStatus(jxFreezeRecord, WmJxFreezeRecord.STATE_HANDLEFINISH);
                //更新还款记录状态
                freeRefundService.updateState(freeRefund, WmFreeRefund.STATE_EXPIRE);
            }
        }
    }
}