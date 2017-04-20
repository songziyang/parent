package com.ydzb.account.service;


public interface IWmPlatformStatisticsService {

    /**
     * 统计数据 调用接口
     *
     * @throws Exception
     */
    public void accountPlatformStatistics() throws Exception;


    /**
     * 统计活期宝/定存宝购买人数
     *
     * @throws Exception
     */
    public void accountBuyUserCount() throws Exception;


}

