package com.ydzb.account.quartz;


import com.ydzb.account.service.IWmPlatformReconciliationService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 平台对账数据记录
 */
public class PlatformBalanceRemindQuartz {

    @Autowired
    private IWmPlatformReconciliationService platformReconciliationService;

    public void accountJob() {
        try {
            platformReconciliationService.balanceRemind();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public IWmPlatformReconciliationService getPlatformReconciliationService() {
        return platformReconciliationService;
    }

    public void setPlatformReconciliationService(IWmPlatformReconciliationService platformReconciliationService) {
        this.platformReconciliationService = platformReconciliationService;
    }
}
