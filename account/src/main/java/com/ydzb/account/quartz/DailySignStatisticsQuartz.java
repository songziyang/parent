package com.ydzb.account.quartz;

import com.ydzb.account.service.IWmDailySignStatisticsService;
import com.ydzb.account.service.IWmUserInfoService;
import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;

/**
 * 每日签到统计
 */
public class DailySignStatisticsQuartz {

    @Autowired
    private IWmUserInfoService wmUserInfoService;
    @Autowired
    private IWmDailySignStatisticsService wmDailySignStatisticsService;

    public void accountJob() {

        Long startTime = DateUtil.getSystemTimeDay(DateUtil.subDay(DateUtil.getCurrentDate()));
        Long endTime = DateUtil.getSystemTimeDay(DateUtil.getCurrentDate());

        BigInteger signCount = wmUserInfoService.queryDailySignNumber(startTime, endTime);
        wmDailySignStatisticsService.createOne(signCount == null? 0: signCount.intValue(), startTime);
    }
}