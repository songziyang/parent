package com.ydzb.account.service;

import com.ydzb.account.entity.IWmDailyProductAccount;
import com.ydzb.account.entity.WmProductInfo;

import java.math.BigDecimal;

/**
 * 每日产品结算流程service
 */
public interface IWmDailyProductSettlementProcessService extends IWmProductSettlementProcessService {

    /**
     * 处理存管用户结算
     * @param dailyProductAccount 每日产品账户(currentUserAccount等）
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
    void handleDepositorySettlement(IWmDailyProductAccount dailyProductAccount, WmProductInfo productInfo,
            BigDecimal allFund, BigDecimal expFund, BigDecimal dayApr, BigDecimal monthApr, BigDecimal vipApr,
            BigDecimal income, BigDecimal investIncome, BigDecimal interestIncome, BigDecimal vipIncome, Long curDate) throws Exception;

    /**
     * 处理管存用户预处理
     * @param userId 用户id
     */
    void handleDepositoryPretreatment(Long userId) throws Exception;

    /**
     * 处理老用户结算
     * @param dailyProductAccount 每日产品账户(currentUserAccount等）
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
    void handleOldUserSettlement(IWmDailyProductAccount dailyProductAccount, WmProductInfo productInfo,
            BigDecimal allFund, BigDecimal expFund, BigDecimal dayApr, BigDecimal monthApr, BigDecimal vipApr,
            BigDecimal income, BigDecimal investIncome, BigDecimal interestIncome, BigDecimal vipIncome, Long curDate) throws Exception;

}