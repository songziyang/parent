package com.ydzb.account.service;

import com.ydzb.account.entity.WmUserIncome;

import javax.persistence.LockModeType;
import java.math.BigDecimal;

/**
 * Created by sy on 2016/7/25.
 */
public interface IWmUserIncomeService {

    WmUserIncome findByUser(Long userId, LockModeType lockModeType);

    WmUserIncome saveOrUpdate(WmUserIncome entity);

    /**
     * 增加新手宝结算收益
     * @param userId         用户ID
     * @param income         总收益
     */
    WmUserIncome increasePrivilegetSettlementIncome(Long userId, BigDecimal income);
    /**
     * 增加活期宝结算收益
     * @param userId         用户ID
     * @param income         总收益
     * @param investIncome   体验金收益
     * @param interestIncome 加息收益
     * @param vipIncome      VIP收益
     */
    WmUserIncome increaseCurrentSettlementIncome(Long userId, BigDecimal income, BigDecimal investIncome, BigDecimal interestIncome, BigDecimal vipIncome);

    /**
     * 增加定存宝结算收益
     * @param userId 用户id
     * @param income 收益
     * @param interestIncome 加息券收益
     * @param vipIncome vip收益
     * @param vouchersIncome 代金券收益
     */
    void increaseRagularSettlementIncome(Long userId, BigDecimal income, BigDecimal interestIncome, BigDecimal vipIncome, BigDecimal vouchersIncome);

    /**
     * 更新定存本息复投的收益
     * @param userId 用户id
     * @param income 收益
     * @param interestIncome 加息券收益
     * @param vipIncome vip收益
     * @param vouchersIncome 定存红包收益
     * @throws Exception
     */
     void increaseRagularAllExpireIncome(Long userId, BigDecimal income, BigDecimal interestIncome, BigDecimal vipIncome,
            BigDecimal vouchersIncome) throws Exception;

    /**
     * 更新定存本金复投的收益
     * @param userId 用户id
     * @param income 收益
     * @param interestIncome 加息券收益
     * @param vipIncome vip收益
     * @param vouchersIncome 定存红包收益
     * @throws Exception
     */
    void increaseRagularPrincipalExpireIncome(Long userId, BigDecimal income, BigDecimal interestIncome, BigDecimal vipIncome,
            BigDecimal vouchersIncome) throws Exception;


    /**
     * 更新定存不是最后一期还款的收益
     * @param userId 用户id
     * @param income 收益
     * @param interestIncome 加息券收益
     * @param vipIncome vip收益
     * @param vouchersIncome 定存红包收益
     * @throws Exception
     */
    void increaseRagularNotLastStageIncome(Long userId, BigDecimal income, BigDecimal interestIncome, BigDecimal vipIncome,
            BigDecimal vouchersIncome) throws Exception;

    /**
     * 增加预期收益
     * @param userId 用户id
     * @param fund 金额
     */
    WmUserIncome increasePredictIncome(Long userId, BigDecimal fund) throws Exception;

    /**
     * 更新随心存结算收益
     * @param userId 用户id
     * @param income 收益
     * @param vipIncome vip收益
     * @throws Exception
     */
    void increaseFreeExpire(Long userId, BigDecimal income, BigDecimal vipIncome) throws Exception;
}