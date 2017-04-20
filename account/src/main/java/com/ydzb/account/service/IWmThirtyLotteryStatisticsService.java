package com.ydzb.account.service;

import com.ydzb.account.entity.WmThirtyLotteryStatistics;

/**
 * 30亿活动抽奖统计service接口
 */
public interface IWmThirtyLotteryStatisticsService {

    WmThirtyLotteryStatistics createOne(Integer todayCount, Long statisticsDate);
}