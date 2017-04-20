package com.ydzb.account.quartz;


import com.ydzb.account.service.IWmPlatformAprService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 平台综合年化
 */
public class PlatformAprQuartz {

    @Autowired
    private IWmPlatformAprService platformAprService;

    //平台综合年化
    public void accountJob() {
        try {
            platformAprService.accountPlatformApr();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public IWmPlatformAprService getPlatformAprService() {
        return platformAprService;
    }

    public void setPlatformAprService(IWmPlatformAprService platformAprService) {
        this.platformAprService = platformAprService;
    }
}
