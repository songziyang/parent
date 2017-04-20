package com.ydzb.account.quartz;


import com.ydzb.account.service.IRagularExpireInfoService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 定存复投提示
 */
public class AccountRagularExpireInfo {

    @Autowired
    private IRagularExpireInfoService ragularExpireInfoService;

    public void accountJob() {
        try {
            //定存复投提示
            ragularExpireInfoService.accountRagularExpireInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public IRagularExpireInfoService getRagularExpireInfoService() {
        return ragularExpireInfoService;
    }

    public void setRagularExpireInfoService(IRagularExpireInfoService ragularExpireInfoService) {
        this.ragularExpireInfoService = ragularExpireInfoService;
    }
}