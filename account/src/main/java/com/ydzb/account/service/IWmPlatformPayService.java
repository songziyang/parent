package com.ydzb.account.service;

/**
 * 平台支出统计结算service接口
 * @author sy
 */
public interface IWmPlatformPayService {

    /**
     * 平台支出结算
     * @param endDate
     * @throws Exception
     */
    public void accountPlatformPay(Long endDate) throws Exception;
}
