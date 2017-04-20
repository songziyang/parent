package com.ydzb.account.quartz;


import com.ydzb.account.service.IWmPlatformReconciliationService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 平台对账数据记录
 */
public class PlatformReconciliationQuartz {

    @Autowired
    private IWmPlatformReconciliationService platformReconciliationService;

    public void accountJob() {
        try {
            //定时统计平台和第三方账户信息
            platformReconciliationService.accountPlatformReconciliation();
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
