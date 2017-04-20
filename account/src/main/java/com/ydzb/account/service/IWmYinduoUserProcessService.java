package com.ydzb.account.service;

import com.ydzb.account.entity.WmCurrentUserAccount;
import com.ydzb.account.entity.WmProductInfo;

import java.math.BigDecimal;

/**
 * 银多用户流程service
 */
public interface IWmYinduoUserProcessService {

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
    void handleCurrentSettlementProcess(WmCurrentUserAccount currentUserAccount, WmProductInfo productInfo, BigDecimal allFund,
            BigDecimal expFund, BigDecimal dayApr, BigDecimal monthApr, BigDecimal vipApr, BigDecimal income, BigDecimal investIncome,
            BigDecimal interestIncome, BigDecimal vipIncome, Long curDate) throws Exception;

    /**
     * 处理复利复投
     * @param userId
     */
    void handleProfitRecast(Long userId) throws Exception;
}