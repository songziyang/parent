package com.ydzb.account.quartz;

import com.ydzb.account.service.IWmUserIntegralService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户积分清零
 */
public class AccountUserIntegral {

    @Autowired
    private IWmUserIntegralService userIntegralService;

    public void accountJob() {
        try {
            userIntegralService.emptyUserIntegral();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public IWmUserIntegralService getUserIntegralService() {
        return userIntegralService;
    }

    public void setUserIntegralService(IWmUserIntegralService userIntegralService) {
        this.userIntegralService = userIntegralService;
    }
}
