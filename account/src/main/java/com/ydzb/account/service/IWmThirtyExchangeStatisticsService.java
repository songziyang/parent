package com.ydzb.account.service;

import com.ydzb.account.entity.WmThirtyExchangeStatistics;

/**
 */
public interface IWmThirtyExchangeStatisticsService {

    WmThirtyExchangeStatistics createOne(Integer allCount, Integer goodsCount, Long statisticsDate);
}
