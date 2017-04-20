package com.ydzb.account.service.impl;

import com.ydzb.account.entity.*;
import com.ydzb.account.service.*;
import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 老用户业务流程service实现
 */
@Service("oldUserProcessService")
public class WmOldUserProcessServiceImpl implements IWmYinduoUserProcessService {

    @Autowired
    private IWmCurrentSettlementService currentSettlementService;
    @Autowired
    private IWmUserMoneyService userMoneyService;
    @Autowired
    private IWmUserInvestInfoService userInvestInfoService;
    @Autowired
    private IWmUserIncomeService userIncomeService;
    @Autowired
    private IWmUserFuncGrandProfitRecordService userFuncGrandProfitRecordService;
    @Autowired
    private IWmUserIncomeRecordService userIncomeRecordService;
    @Autowired
    private IWmCurrentBuylogService currentBuylogService;
    @Autowired
    private IWmCurrentTradeRecordService currentTradeRecordService;
    @Autowired
    private IWmCurrentUserAccountService currentUserAccountService;

    /**
     * 处理活期宝结算流程
     * @param currentUserAccount 用户活期宝账户
     * @param productInfo 活期宝产品
     * @param allFund 投资金额
     * @param expFund 体验金金额
     * @param dayApr 日利率
     * @param monthApr 月利率
     * @param vipApr vip利率
     * @param income 收益
     * @param investIncome 体验金收益
     * @param interestIncome 加息券收益
     * @param vipIncome vip收益
     * @param curDate 结算日期
     */
    @Override
    public void handleCurrentSettlementProcess(WmCurrentUserAccount currentUserAccount, WmProductInfo productInfo,
            BigDecimal allFund, BigDecimal expFund, BigDecimal dayApr, BigDecimal monthApr, BigDecimal vipApr,
            BigDecimal income, BigDecimal investIncome, BigDecimal interestIncome, BigDecimal vipIncome, Long curDate) throws Exception{

        Long userId = currentUserAccount.getUserId();
        Long productId = productInfo.getId();
        //保存活期每日结算
        currentSettlementService.createOne(userId, productId, allFund,
                expFund, productInfo.getInterestRate(), dayApr, monthApr, vipApr, income, DateUtil.getSystemTimeDay(DateUtil.getCurrentDate()), DateUtil.getSystemTimeSeconds());
        //增加用户总资金
        userMoneyService.increaseTotalFund(userId, income);
        //增加用户活期宝投资
        userInvestInfoService.increaseCurrentInvest(userId, income);
        //增加用户收益
        userIncomeService.increaseCurrentSettlementIncome(userId, income, investIncome, interestIncome, vipIncome);
        //创建用户赠予（红包）体验金收益记录
        userFuncGrandProfitRecordService.createOne(userId, productId, investIncome, WmUserFuncGrandProfitRecord.PTYPE_CURRENT, WmUserFuncGrandProfitRecord.TYPE_EXPERIENCE, DateUtil.getSystemTimeSeconds());
        //创建用户赠予（红包）加息券收益记录
        userFuncGrandProfitRecordService.createOne(userId, productId, investIncome, WmUserFuncGrandProfitRecord.PTYPE_CURRENT, WmUserFuncGrandProfitRecord.TYPE_INTEREST, DateUtil.getSystemTimeSeconds());
        //创建用户赠予（红包）VIP收益记录
        userFuncGrandProfitRecordService.createOne(userId, productId, investIncome, WmUserFuncGrandProfitRecord.PTYPE_CURRENT, WmUserFuncGrandProfitRecord.TYPE_VIP, DateUtil.getSystemTimeSeconds());
        //创建收益记录
        userIncomeRecordService.createOne(userId, FundFlow.HQBSY, income, WmUserIncomeRecord.CURRENT, DateUtil.getSystemTimeSeconds());
        //创建购买日志
        WmCurrentBuylog buylog = currentBuylogService.createOne(productId, userId, income, BigDecimal.ZERO, 0, productInfo.getInterestRate(),
            BigDecimal.ZERO, DateUtil.getSystemTimeSeconds(), WmCurrentBuylog.DEVICE_SYSTEM);
        //创建活期宝交易记录
        currentTradeRecordService.createOne(userId, FundFlow.HQBFLTJ, income, DateUtil.getSystemTimeSeconds(),
                WmCurrentTradeRecored.TYPE_PURCHASE, WmCurrentTradeRecored.FUNDSOURCE_INCOME, buylog == null? null: buylog.getId());
        //更新用户活期账户
        currentUserAccountService.increaseProfit(currentUserAccount, income, curDate);
    }

    /**
     * 处理复利复投
     * @param userId
     */
    public void handleProfitRecast(Long userId) {

    }
}
