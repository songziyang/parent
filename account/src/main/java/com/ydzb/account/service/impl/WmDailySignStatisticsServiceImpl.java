package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmDailySignStatistics;
import com.ydzb.account.repository.WmDailySignStatisticsRepository;
import com.ydzb.account.service.IWmDailySignStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by sy on 2016/6/29.
 */
@Service
public class WmDailySignStatisticsServiceImpl implements IWmDailySignStatisticsService {

    @Autowired
    private WmDailySignStatisticsRepository wmDailySignStatisticsRepository;

    public WmDailySignStatistics createOne(Integer signCount, Long statisticsDate) {
        WmDailySignStatistics wmDailySignStatistics = new WmDailySignStatistics(signCount, statisticsDate);
        return wmDailySignStatisticsRepository.saveOrUpdate(wmDailySignStatistics);
    }
}