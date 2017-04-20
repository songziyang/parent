package com.ydzb.account.quartz;

import com.ydzb.account.service.IWmInfoSmsTimerService;
import com.ydzb.account.service.IWmUserRedPacketService;
import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 红包临期提醒定时任务
 */
public class RedpacketOvertimeRemindQuartz {

    @Autowired
    private IWmUserRedPacketService wmUserRedPacketService;
    @Autowired
    private IWmInfoSmsTimerService infoSmsTimerService;

    /**
     * 自动提醒
     */
    public void autoRemind() {
        Long today = DateUtil.getSystemTimeDay(DateUtil.getCurrentDate());
        String mobile;
        Byte triggerType;

        //查询3天后要到期的红包
        List<Object[]> redpacketInfos = wmUserRedPacketService.queryRedpacketInfoByFinishTime(today + 24 * 3600 * 3);
        if (redpacketInfos != null) {
            for (Object[] redpacketInfo: redpacketInfos) {
                if (redpacketInfo != null && redpacketInfo.length >= 3) {
                    mobile = (String) redpacketInfo[1];
                    triggerType = (Byte) redpacketInfo[2];
                    try {
                        if (triggerType == null) continue;
                        if (triggerType == 2) { //加息券
                            infoSmsTimerService.sendWmInfoSmsTimer("interest_overtime", mobile, " : ");
                        } else if (triggerType == 3) {  //定存红包
                            infoSmsTimerService.sendWmInfoSmsTimer("voucher_overtime", mobile, " : ");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}