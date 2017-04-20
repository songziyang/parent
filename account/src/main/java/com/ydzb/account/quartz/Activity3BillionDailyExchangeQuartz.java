package com.ydzb.account.quartz;

import com.ydzb.account.service.IActivity3BillionDailyExchangeService;
import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;

/**
 * 
 */
public class Activity3BillionDailyExchangeQuartz {

    @Autowired
    private IActivity3BillionDailyExchangeService activity3BillionDailyExchangeService;

    public void accountJob() {

        //7月23日下活动,7月24日最后统计一天,大于等于7月25日不进行统计
        Calendar c = Calendar.getInstance();
        c.set(2016, Calendar.JULY, 25, 0 ,0 ,0);
        if (DateUtil.getSystemTimeSeconds() < c.getTimeInMillis() / 1000) {
            try {
                //每日兑换统计
                activity3BillionDailyExchangeService.account();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                //每日兑换统计
                activity3BillionDailyExchangeService.accountLottery();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}