package com.ydzb.account.service;

import com.ydzb.account.entity.WmPrivilegeUserAccount;

import javax.persistence.LockModeType;
import java.math.BigDecimal;

/**
 * 新手特权标持有记录service接口
 */
public interface IWmPrivilegeUserAccountService {

    /**
     * 增加复利
     * @param privilegeUserAccount 新手特权持有记录
     * @param fund 金额
     * @param settlementDate 结算日期
     */
    WmPrivilegeUserAccount increaseProfit(WmPrivilegeUserAccount privilegeUserAccount, BigDecimal fund, Long settlementDate) throws Exception;
    /**
     * 根据主键查询
     * @param id
     * @param lockMode
     * @return
     */
    WmPrivilegeUserAccount queryOne(Long id, LockModeType lockMode);

    /**
     * 清空金额
     * @param privilegeUserAccount 新手特权持有记录
     */
    void emptyFund(WmPrivilegeUserAccount privilegeUserAccount);

    /**
     * 处理复利复投
     * @param privilegeUserAccount 新手宝账户
     * @param remainingProfit 剩余的复利金额
     * @param buyFund 复投金额
     */
    void handleProfileRecast(WmPrivilegeUserAccount privilegeUserAccount, BigDecimal remainingProfit, BigDecimal buyFund) throws Exception;

    /**
     * 处理复利复投成功
     * @param privilegeUserAccount 新手宝账户
     * @param buyFund 复投金额
     */
    void handleProfileRecastSuccess(WmPrivilegeUserAccount privilegeUserAccount, BigDecimal buyFund) throws Exception;
}