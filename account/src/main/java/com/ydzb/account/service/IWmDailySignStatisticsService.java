package com.ydzb.account.service;

import com.ydzb.account.entity.WmDailySignStatistics;

/**
 * Created by sy on 2016/6/29.
 */
public interface IWmDailySignStatisticsService {

    WmDailySignStatistics createOne(Integer signCount, Long statisticsDate);
}
