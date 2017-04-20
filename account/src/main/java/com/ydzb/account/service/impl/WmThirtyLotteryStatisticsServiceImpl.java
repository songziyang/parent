package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmThirtyLotteryStatistics;
import com.ydzb.account.repository.WmThirtyLotteryStatisticsRepository;
import com.ydzb.account.service.IWmThirtyLotteryStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 30亿活动抽奖统计service实现
 */
@Service
public class WmThirtyLotteryStatisticsServiceImpl implements IWmThirtyLotteryStatisticsService {

    @Autowired
    private WmThirtyLotteryStatisticsRepository wmThirtyLotteryStatisticsRepository;

    /**
     * 创建
     * @param todayCount
     * @param statisticsDate
     * @return
     */
    @Override
    public WmThirtyLotteryStatistics createOne(Integer todayCount, Long statisticsDate) {

        WmThirtyLotteryStatistics entity = new WmThirtyLotteryStatistics();
        entity.setTodayCount(todayCount);
        entity.setStatisticsDate(statisticsDate);
        return wmThirtyLotteryStatisticsRepository.saveOrUpdate(entity);
    }
}