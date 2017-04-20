package com.ydzb.account.service;


public interface IWmPlatformTradingService {

    /**
     * 平台交易统计
     *
     * @param endDate 系统当前日期
     *
     * @throws Exception
     */
    public void accountPlatformTrading(Long endDate) throws Exception;

}
