package com.ydzb.account.quartz;


import com.ydzb.account.service.IWmPlatformStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;

public class PlatformStatisticsQuartz {

    @Autowired
    private IWmPlatformStatisticsService statisticsService;


    public void accountJob() {
        //调用网站接口
        try {
            statisticsService.accountPlatformStatistics();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //统计数据
        try {
            statisticsService.accountBuyUserCount();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public IWmPlatformStatisticsService getStatisticsService() {
        return statisticsService;
    }

    public void setStatisticsService(IWmPlatformStatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

}