package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmThirtyExchangeStatistics;
import com.ydzb.account.repository.WmThirtyExchangeStatisticsRepository;
import com.ydzb.account.service.IWmThirtyExchangeStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by sy on 2016/7/7.
 */
@Service
public class WmThirtyExchangeStatisticsServiceImpl implements IWmThirtyExchangeStatisticsService {

    @Autowired
    private WmThirtyExchangeStatisticsRepository wmThirtyExchangeStatisticsRepository;

    /**
     * 创建
     * @param allCount
     * @param goodsCount
     * @param statisticsDate
     * @return
     */
    @Override
    public WmThirtyExchangeStatistics createOne(Integer allCount, Integer goodsCount, Long statisticsDate) {

        WmThirtyExchangeStatistics wmThirtyExchangeStatistics = new WmThirtyExchangeStatistics();
        wmThirtyExchangeStatistics.setAllCount(allCount);
        wmThirtyExchangeStatistics.setGoodsCount(goodsCount);
        wmThirtyExchangeStatistics.setStatisticsDate(statisticsDate);
        return wmThirtyExchangeStatisticsRepository.saveOrUpdate(wmThirtyExchangeStatistics);
    }
}