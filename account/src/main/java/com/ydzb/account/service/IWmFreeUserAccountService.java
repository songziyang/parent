package com.ydzb.account.service;


import com.ydzb.account.entity.WmFreeUserAccount;

import java.math.BigDecimal;

/**
 * 随心存账户service接口
 */
public interface IWmFreeUserAccountService {

    /**
     * 更新状态
     * @param freeUserAccount 自由存账户
     * @param status 状态
     * @return
     */
    WmFreeUserAccount updateStatus(WmFreeUserAccount freeUserAccount, int status) throws Exception;

    /**
     * 处理随心存到期并且不是最后一期的
     * @param freeUserAccount 随心存账户
     * @param income 收益
     */
    void handleNotLatestStage(WmFreeUserAccount freeUserAccount, BigDecimal income) throws Exception;
}
