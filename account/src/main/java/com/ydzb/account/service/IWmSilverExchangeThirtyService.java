package com.ydzb.account.service;

/**
 * 30亿活动兑换service接口
 */
public interface IWmSilverExchangeThirtyService {

    /**
     * 查询兑换所有产品的人数
     * @param startTime
     * @param endTime
     * @return
     */
    Integer queryAllExchangeUserCount(Long startTime, Long endTime);

    /**
     * 查询兑换实物产品的人数
     * @param startTime
     * @param endTime
     * @return
     */
    Integer queryGoodsExchangeUserCount(Long startTime, Long endTime);
}
