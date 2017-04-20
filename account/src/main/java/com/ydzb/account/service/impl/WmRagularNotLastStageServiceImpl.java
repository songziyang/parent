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
 * 定存不是最后一期service实现
 */
@Service("ragularNotLastStageService")
public class WmRagularNotLastStageServiceImpl implements IWmPeriodicProductHandleService {

    @Autowired
    private IWmUserIncomeService userIncomeService;
    @Autowired
    private IWmUserIncomeRecordService userIncomeRecordService;
    @Autowired
    private IWmRagularUserAccountService ragularUserAccountService;
    @Autowired
    private WmProductInfoRepository wmProductInfoRepository;
    @Autowired
    private IWmUserMoneyService userMoneyService;
    @Autowired
    private IWmUserFundInLogService userFundInLogService;
    @Autowired
    private IWmUserFundRecordService userFundRecordService;
    @Autowired
    private IWmUserFuncGrandProfitRecordService userFuncGrandProfitRecordService;
    @Autowired
    private IWmRagularRefundService ragularRefundService;

    /**
     * 执行老用户结算
     * @param periodicProductRefund 周期产品还息记录
     * @param periodicProductAccount 周期产品账户
     * @return 复投生成的新ragularUserAccount,此处为null
     * @throws Exception
     */
    @Override
    public WmRagularUserAccount executeOldUserSettlement(IWmPeriodicProductRefund periodicProductRefund, IWmPeriodicProductAccount periodicProductAccount) throws Exception {

        WmRagularRefund ragularRefund = (WmRagularRefund)periodicProductRefund;
        WmRagularUserAccount ragularUserAccount = (WmRagularUserAccount)periodicProductAccount;
        if (ragularRefund != null && ragularUserAccount != null) {

            Long userId = ragularUserAccount.getUserId();
            WmProductInfo productInfo = wmProductInfoRepository.queryById(ragularUserAccount.getProductId(), WmProductInfo.class, LockModeType.NONE);
            //更新用户资金
            userMoneyService.increaseTotalAndUsableFund(userId, ragularRefund.getFund());
            //增加用户收益
            userIncomeService.increaseRagularNotLastStageIncome(userId, ragularRefund.getFund(), ragularRefund.getRedpacketFund(), ragularRefund.getVipFund(), ragularRefund.getVouchersFund());
            //创建用户收益记录
            userIncomeRecordService.createOne(userId, getIncomeName(ragularRefund, productInfo), ragularRefund.getFund(), WmUserIncomeRecord.RAGULAR, DateUtil.getSystemTimeSeconds());
            //更新用户定存购买交易
            ragularUserAccountService.decreaseRagularSettlementAccount(ragularUserAccount, BigDecimal.ZERO, ragularRefund.getFund(), WmRagularUserAccount.STATUS_NOTEXPIRE);
            //定存收益进账日志
            WmUserMoney userMoney = userMoneyService.queryOne(userId, LockModeType.PESSIMISTIC_WRITE);
            WmUserFundInLog incomeFundInLog = userFundInLogService.createOne(userId, WmUserFundInLog.TYPE_RAGULAR_INCOME, DateUtil.getSystemTimeSeconds(), BigDecimal.ZERO, ragularRefund.getFund(),
                    userMoney.getUsableFund(), null, ragularUserAccount.getId());
            //定存资金记录
            userFundRecordService.createOne(userId, getIncomeName(ragularRefund, productInfo), ragularRefund.getFund(), incomeFundInLog == null? null: incomeFundInLog.getId(), WmUserFundRecord.TYPE_INFUND,
                    WmUserFundRecord.FUNDTYPE_INCOME, userMoney.getUsableFund(), DateUtil.getSystemTimeSeconds());
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

    @Override
    public void executeDepositoryUserPretreatment(WmUser user, IWmPeriodicProductRefund periodicProductRefund, IWmPeriodicProductAccount periodicProductAccount) throws Exception {
        //存管用户不执行
    }

    @Override
    public void executeDepositoryUserPretreatmentSuccess(Long jxFreezeRecordId) throws Exception {
        //存管用户不执行
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