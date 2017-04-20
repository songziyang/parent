package com.ydzb.account.service;

import com.ydzb.account.entity.WmFreeRefund;
import com.ydzb.account.entity.WmFreeUserAccount;
import com.ydzb.account.entity.WmUser;

/**
 * 定存宝结算流程
 */
public interface IWmFreeSettlementProcessService {

    /**
     * 执行老用户结算
     * @param freeRefund 随心存还息记录
     * @param freeUserAccount 随心存账户
     * @throws Exception
     */
    void executeSettlement(WmFreeRefund freeRefund, WmFreeUserAccount freeUserAccount) throws Exception;

    /**
     * 执行存管用户结算
     * @param user 用户
     * @param freeRefund 随心存还息记录
     * @param freeUserAccount 随心存账户
     * @throws Exception
     */
    void executeDepositoryUserSettlement(WmUser user, WmFreeRefund freeRefund, WmFreeUserAccount freeUserAccount) throws Exception;

    /**
     * 执行存管用户预处理
     * @param user 用户
     * @param freeRefund 随心存还息记录
     * @param freeUserAccount 随心存账户
     * @throws Exception
     */
    void executeDepositoryUserPretreatment(WmUser user, WmFreeRefund freeRefund, WmFreeUserAccount freeUserAccount) throws Exception;
}