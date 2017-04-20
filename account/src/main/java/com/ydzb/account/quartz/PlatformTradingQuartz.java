package com.ydzb.account.quartz;


import com.ydzb.account.service.IWmPlatformPayService;
import com.ydzb.account.service.IWmPlatformTradingService;
import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class PlatformTradingQuartz {

    @Autowired
    private IWmPlatformTradingService platformTradingService;

    @Autowired
    private IWmPlatformPayService platformPayService;

    public void accountJob() {

        // 系统当前日期
        Long endDate = DateUtil.getSystemTimeDay(DateUtil.curDate());

        //平台交易统计
        accountPlatformTrading(endDate);

        //平台支出统计结算
        accountPlatformPay(endDate);

    }


    /**
     * 平台交易统计
     *
     * @param endDate 系统当前日期
     */
    public void accountPlatformTrading(Long endDate) {
        try {
            //平台交易统计
            platformTradingService.accountPlatformTrading(endDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    /**
     * 平台支出统计结算
     *
     * @param endDate 系统当前日期
     */
    public void accountPlatformPay(Long endDate) {
        try {
            //平台支出统计结算
            platformPayService.accountPlatformPay(endDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public IWmPlatformTradingService getPlatformTradingService() {
        return platformTradingService;
    }

    public void setPlatformTradingService(IWmPlatformTradingService platformTradingService) {
        this.platformTradingService = platformTradingService;
    }
}
