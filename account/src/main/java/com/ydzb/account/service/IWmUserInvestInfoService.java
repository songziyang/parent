package com.ydzb.account.service;

import com.ydzb.account.entity.WmUserInvestinfo;

import javax.persistence.LockModeType;
import java.math.BigDecimal;

/**
 * 用户投资service接口
 */
public interface IWmUserInvestInfoService {

    WmUserInvestinfo findByUser(Long userId);

    WmUserInvestinfo findByUser(Long userId, LockModeType lockType);

    WmUserInvestinfo saveOrUpdate(WmUserInvestinfo entity) throws Exception;

    /**
     * 增加活期宝投资
     * @param userId 用户ID
     * @param fund 金额
     */
    WmUserInvestinfo increaseCurrentInvest(Long userId, BigDecimal fund) throws Exception;

    /**
     * 减少定存宝投资
     * @param userId 用户ID
     * @param fund 金额
     */
    WmUserInvestinfo decreaseRagularInvest(Long userId, BigDecimal fund) throws Exception;

    /**
     * 增加定存宝投资
     * @param userId 用户ID
     * @param fund 金额
     */
    WmUserInvestinfo increaseRagularInvest(Long userId, BigDecimal fund) throws Exception;

    /**
     * 减少随心存投资
     * @param userId 用户ID
     * @param fund 金额
     */
    WmUserInvestinfo decreaseFreeInvest(Long userId, BigDecimal fund) throws Exception;

    /**
     * 增加定存类赎回冻结金额
     * @param userId 用户id
     * @param fund 金额
     * @return
     * @throws Exception
     */
    WmUserInvestinfo increaseAllRedeemFreeze(Long userId, BigDecimal fund) throws Exception;

    /**
     * 增加定存类购买冻结金额
     * @param userId 用户id
     * @param fund 金额
     * @return
     * @throws Exception
     */
    WmUserInvestinfo increaseAllBuyFreeze(Long userId, BigDecimal fund) throws Exception;

    /**
     * 将新手宝投资转换为活期宝投资
     * @param userId 用户id
     * @param fund 金额
     * @return
     * @throws Exception
     */
    WmUserInvestinfo convertPrvilegeToCurrent(Long userId, BigDecimal fund) throws Exception;

    /**
     * 增加新手宝投资
     * @param userId 用户ID
     * @param fund 金额
     */
    WmUserInvestinfo increasePrivilegeInvest(Long userId, BigDecimal fund) throws Exception;
}
