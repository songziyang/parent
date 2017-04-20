package com.ydzb.account.service.impl;

import com.ydzb.account.service.*;
import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 30亿活动每日兑换统计service实现
 */
@Service
public class Activity3BillionDailyExchangeServiceImpl implements IActivity3BillionDailyExchangeService {

    @Autowired
    private IWmSilverExchangeThirtyService wmSilverExchangeThirtyService;
    @Autowired
    private IWmThirtyExchangeStatisticsService wmThirtyExchangeStatisticsService;
    @Autowired
    private IWmSilverTradeRecordThirtyService wmSilverTradeRecordThirtyService;
    @Autowired
    private IWmThirtyLotteryStatisticsService wmThirtyLotteryStatisticsService;

    @Override
    public void account() throws Exception {

        Long yesterday = DateUtil.getSystemTimeDay(DateUtil.subDay(DateUtil.getCurrentDate()));
        Long today = DateUtil.getSystemTimeDay(DateUtil.getCurrentDate());

        Integer allCount = wmSilverExchangeThirtyService.queryAllExchangeUserCount(yesterday, today);
        Integer goodsCount = wmSilverExchangeThirtyService.queryGoodsExchangeUserCount(yesterday, today);

        wmThirtyExchangeStatisticsService.createOne(allCount, goodsCount, yesterday);
    }

    @Override
    public void accountLottery() throws Exception {

        Long yesterday = DateUtil.getSystemTimeDay(DateUtil.subDay(DateUtil.getCurrentDate()));
        Long today = DateUtil.getSystemTimeDay(DateUtil.getCurrentDate());

        Integer todayCount = wmSilverTradeRecordThirtyService.queryLotteryUserCount(yesterday, today);
        wmThirtyLotteryStatisticsService.createOne(todayCount, yesterday);
    }
}
