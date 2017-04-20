package com.ydzb.account.quartz;


import com.ydzb.account.service.IWmUserWithDrawNumService;
import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountQueryWithDrawQuartz {

    @Autowired
    private IWmUserWithDrawNumService wmUserWithDrawNumService;

    public void accountJob() {
        try {
            // 系统当前日期
            Long endDate = DateUtil.getSystemTimeDay(DateUtil.curDate());
            wmUserWithDrawNumService.accountQueryWithDraw(endDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public IWmUserWithDrawNumService getWmUserWithDrawNumService() {
        return wmUserWithDrawNumService;
    }

    public void setWmUserWithDrawNumService(IWmUserWithDrawNumService wmUserWithDrawNumService) {
        this.wmUserWithDrawNumService = wmUserWithDrawNumService;
    }
}
