package com.ydzb.account.quartz;


import com.ydzb.account.context.IDRange;
import com.ydzb.account.service.IWmInfoSmsTimerService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 平台短信自动发送
 */
public class AutoSendSmsTimerQuartz {


    @Autowired
    private IWmInfoSmsTimerService infoSmsTimerService;

    public void accountJob() {
        try {
            //自动发送短信
            autoSendSmsTimer();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 自动发送短信
     *
     * @throws Exception
     */
    public void autoSendSmsTimer() throws Exception {
        // 定时发送短信最大ID和最小ID
        IDRange idRange = infoSmsTimerService.findMaxIdAndMinId();
        // 判断是否有要发送的定时短信
        if (idRange != null && idRange.getMaxId() > 0 && idRange.getMinId() > 0) {
            // 根据ID遍历用户的定时短信
            for (long id = idRange.getMinId(); id <= idRange.getMaxId(); id++) {
                try {
                    //发送短信
                    infoSmsTimerService.autoSendSmsTimer(id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public IWmInfoSmsTimerService getInfoSmsTimerService() {
        return infoSmsTimerService;
    }

    public void setInfoSmsTimerService(IWmInfoSmsTimerService infoSmsTimerService) {
        this.infoSmsTimerService = infoSmsTimerService;
    }
}
