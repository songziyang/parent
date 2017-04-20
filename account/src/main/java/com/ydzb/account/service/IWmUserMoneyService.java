package com.ydzb.account.service;

import com.ydzb.account.entity.WmUserMoney;

import javax.persistence.LockModeType;
import java.math.BigDecimal;

/**
 * Created by sy on 2016/6/28.
 */
public interface IWmUserMoneyService {

    WmUserMoney queryOne(Long userId, LockModeType lockType);

    WmUserMoney saveOrUpdate(WmUserMoney entity);

    /**
     * 增加总金额
     * @param userId 用户id
     * @param fund 增加的金额
     * @return
     */
    WmUserMoney increaseTotalFund(Long userId, BigDecimal fund);


    /**
     * 增加总金额和余额
     * @param userId 用户id
     * @param fund 增加的金额
     * @return
     */
    WmUserMoney increaseTotalAndUsableFund(Long userId, BigDecimal fund);

    /**
     * 增加用户定存到期不复投后的资金
     * @param userId 用户ID
     * @param fund   本金
     * @param income 收益
     */
    WmUserMoney increaseRagularNoRecastFund(Long userId, BigDecimal fund, BigDecimal income);

    /**
     * 增加用户定存到期本息复投的资金
     * @param userId 用户ID
     * @param allIncome   全部收益
     * @param decimalIncome 小数部分收益
     */
    WmUserMoney increaseRagularAllRecastFund(Long userId, BigDecimal allIncome, BigDecimal decimalIncome);

    /**
     * 增加用户定存到期本金复投的资金
     * @param userId 用户ID
     * @param fund 金额
     * @param income 收益
     */
    WmUserMoney increaseRagularPrincipalRecastFund(Long userId, BigDecimal fund, BigDecimal income);

    /**
     * 增加随心存到期的资金
     * @param userId 用户ID
     * @param fund   本金
     * @param income 收益
     */
    WmUserMoney increaseFreeExpireFund(Long userId, BigDecimal fund, BigDecimal income);
}
