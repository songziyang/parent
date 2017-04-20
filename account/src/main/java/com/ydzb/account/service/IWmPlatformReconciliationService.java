package com.ydzb.account.service;


/**
 * 平台对账数据记录
 */
public interface IWmPlatformReconciliationService {

    /**
     * 平台对账
     *
     * @throws Exception
     */
    public void accountPlatformReconciliation() throws Exception;

    /**
     * 余额提醒
     *
     * @throws Exception
     */
    public void balanceRemind() throws Exception;

}
