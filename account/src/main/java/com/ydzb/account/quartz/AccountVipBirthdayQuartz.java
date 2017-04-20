package com.ydzb.account.quartz;


import com.ydzb.account.entity.WmRedPacketInterest;
import com.ydzb.account.entity.WmUser;
import com.ydzb.account.service.IVipBirthdayService;
import com.ydzb.account.service.IWmInfoSmsTimerService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AccountVipBirthdayQuartz {

    public Logger logger = Logger.getLogger(AccountVipBirthdayQuartz.class);

    @Autowired
    private IVipBirthdayService vipBirthdayService;

    @Autowired
    private IWmInfoSmsTimerService infoSmsTimerService;

    public void accountJob() {
        WmRedPacketInterest redPacketInterest = vipBirthdayService.findWmRedPacketInterest();
        if (redPacketInterest != null) {
            DateFormat dateFormat = new SimpleDateFormat("MMdd");
            String date = dateFormat.format(new Date());
            if (!StringUtils.isEmpty(date) && date.length() == 4) {
                List<WmUser> wmUserList = vipBirthdayService.findVipBirthdayByDate(date);
                if (wmUserList != null && wmUserList.size() > 0) {
                    for (WmUser user : wmUserList) {
                        if (user != null) {
                            try {
                                vipBirthdayService.sendRedpacket(user.getId(), redPacketInterest.getId(), "系统");
                                infoSmsTimerService.sendWmInfoSmsTimer("vip_birthday", user.getMobile(), "name:" + user.getUsername());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } else {
            logger.info("VIP生日福利红包不存在");
        }

    }


    public void accountBirthdayJob() {
        DateFormat dateFormat = new SimpleDateFormat("MMdd");
        String date = dateFormat.format(new Date());
        if (!StringUtils.isEmpty(date) && date.length() == 4) {
            List<WmUser> wmUserList = vipBirthdayService.findBirthdayByDate(date);
            if (wmUserList != null && wmUserList.size() > 0) {
                for (WmUser user : wmUserList) {
                    if (user != null) {
                        try {
                            infoSmsTimerService.sendWmInfoSmsTimer("user_birthday", user.getMobile(), "name:" + user.getUsername());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public IVipBirthdayService getVipBirthdayService() {
        return vipBirthdayService;
    }

    public void setVipBirthdayService(IVipBirthdayService vipBirthdayService) {
        this.vipBirthdayService = vipBirthdayService;
    }
}
