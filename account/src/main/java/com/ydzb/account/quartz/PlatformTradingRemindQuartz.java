package com.ydzb.account.quartz;


import com.ydzb.account.service.IPlatformTradingRemindService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 平台充值提现提醒
 */
public class PlatformTradingRemindQuartz {

    @Autowired
    private IPlatformTradingRemindService platformTradingRemindService;


    /**
     * 充值提现短信提醒
     */
    public void accountJob() {
        try {
            platformTradingRemindService.tradingRemind();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public IPlatformTradingRemindService getPlatformTradingRemindService() {
        return platformTradingRemindService;
    }

    public void setPlatformTradingRemindService(IPlatformTradingRemindService platformTradingRemindService) {
        this.platformTradingRemindService = platformTradingRemindService;
    }
}
