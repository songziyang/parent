package com.ydzb.account.quartz;

import com.ydzb.account.service.IWmActivity3BillionAccountService;
import com.ydzb.account.service.IWmRagularUserAccountService;
import com.ydzb.core.utils.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 30亿活动结算
 */
public class Activity3BillionQuartz {

    @Autowired
    private IWmRagularUserAccountService wmRagularUserAccountService;
    @Autowired
    private IWmActivity3BillionAccountService wmActivity3BillionAccountService;

    public static final Calendar ACTIVITY_CALENDAR_START = Calendar.getInstance();
    public static final Calendar ACTIVITY_CALENDAR_END = Calendar.getInstance();

    static {
        ACTIVITY_CALENDAR_START.set(2016, Calendar.JULY, 4, 9, 0);
        ACTIVITY_CALENDAR_END.set(2016, Calendar.JULY, 23, 18, 0);
    }

    Logger logger = Logger.getLogger(Activity3BillionQuartz.class);

    /**
     * 结算
     */
    public void accountJob() {

        Calendar now = Calendar.getInstance();
        if (now.compareTo(ACTIVITY_CALENDAR_START) >= 0 && now.compareTo(ACTIVITY_CALENDAR_END) < 0) {

            Long today = DateUtil.getSystemTimeDay(DateUtil.getCurrentDate());
            Long tomorrow = DateUtil.getSystemTimeDay(DateUtil.addDay(DateUtil.getCurrentDate()));

            List<Object[]> ragularInfos = wmRagularUserAccountService.queryRagularInfoBetweenTime(today, tomorrow);
            if (ragularInfos != null) {
                for (Object[] ragularInfo: ragularInfos) {
                    try {
                        wmActivity3BillionAccountService.account(ragularInfo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        reaccount();
    }

    private void reaccount() {
        Long curTime = DateUtil.getSystemTimeSeconds();
        if (curTime < DateUtil.getSystemTimeDay("2016-07-10") && curTime >= DateUtil.getSystemTimeDay("2016-07-09")) {
            logger.info("30亿活动银多币补发开始-------时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            Long startTime = 1467734400L;   //7月6日0点
            Long endTime = 1467745200L; //7月6日3点
            reaccount(startTime, endTime);
            startTime = 1467820800L;    //7月7日0点
            endTime = 1467831600L;  //7月7日3点
            reaccount(startTime, endTime);
            startTime = 1467907200L;    //7月8日0点
            endTime = 1467918000L;  //7月8日3点
            reaccount(startTime, endTime);
        }
    }

    /**
     * 补发
     */
    private void reaccount(Long startTime, Long endTime) {
        try{
            List<Object[]> ragularInfos = wmRagularUserAccountService.queryRagularInfoBetweenTime(startTime, endTime);
            if (ragularInfos != null) {
                for (Object[] ragularInfo: ragularInfos) {
                    try {
                        wmActivity3BillionAccountService.account(ragularInfo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (Exception e) {
            logger.error("银多币补发失败: startTime:" + startTime + ", endTime:" + endTime);
            e.printStackTrace();
        }
    }
}