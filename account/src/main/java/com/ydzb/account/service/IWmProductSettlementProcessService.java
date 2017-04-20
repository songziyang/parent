package com.ydzb.account.service;

/**
 * 产品结算流程service
 */
public interface IWmProductSettlementProcessService {

    /**
     * 执行结算
     * @param userId 用户id
     * @throws Exception
     */
    void executeSettlement(Long userId) throws Exception;
}
