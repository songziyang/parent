package com.ydzb.account.service.impl;

import com.ydzb.account.context.IDRange;
import com.ydzb.account.entity.WmInfoSmsTimer;
import com.ydzb.account.repository.WmInfoSmsTimerRepository;
import com.ydzb.account.service.IWmInfoSmsTimerService;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.sms.service.ISmsHandleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class WmInfoSmsTimerServiceImpl implements IWmInfoSmsTimerService {


    @Autowired
    private WmInfoSmsTimerRepository infoSmsTimerRepository;

    @Autowired
    private ISmsHandleService smsHandleService;


    /**
     * 短信自动定时发送记录 最大ID 和 最小ID
     *
     * @return
     */
    @Override
    public IDRange findMaxIdAndMinId() {
        return infoSmsTimerRepository.findMaxIdAndMinId();
    }


    /**
     * 自动发送短信
     *
     * @param id
     * @throws Exception
     */
    @Override
    public void autoSendSmsTimer(Long id) throws Exception {
        WmInfoSmsTimer infoSmsTimer = infoSmsTimerRepository.findWmInfoSmsTimer(id);
        if (infoSmsTimer != null) {
            //发送短信
            smsHandleService.sendContent(infoSmsTimer.getMobile(), infoSmsTimer.getContent());
            //删除短信
            infoSmsTimerRepository.removeWmInfoSmsTimer(infoSmsTimer);
        }
    }


    /**
     * 定时发送短信
     *
     * @param flag       模板
     * @param mobile     手机号
     * @param replaceStr 替换字符串
     * @return
     * @throws Exception
     */
    public String sendWmInfoSmsTimer(String flag, String mobile, String replaceStr) throws Exception {

        String template = smsHandleService.querySmsTemplateByFlag(flag);

        if (null == template || "".equals(template)) {
            return "error";
        }
        String sendContent = replaceTemplateContent(template, replaceStr);
        if (sendContent.equals("error")) {
            return "error";
        }

        WmInfoSmsTimer smsTimer = new WmInfoSmsTimer();
        smsTimer.setMobile(mobile);
        smsTimer.setContent(sendContent);
        smsTimer.setCreated(DateUtil.getSystemTimeSeconds());
        infoSmsTimerRepository.saveWmInfoSmsTimer(smsTimer);

        return "ok";
    }


    /**
     * 替换字符串
     *
     * @param template   模板
     * @param replaceStr 字符串
     * @return
     */
    private String replaceTemplateContent(String template, String replaceStr) {
        String sendContent = template;
        if (replaceStr != null && replaceStr.length() > 0 && replaceStr.indexOf(":") > 0) {
            String[] group = replaceStr.split(",");
            for (String g : group) {
                String[] mapstr = g.split(":");
                sendContent = StringUtils.replace(sendContent, "[" + mapstr[0] + "]", mapstr[1]);
            }
        } else {
            return "error";
        }
        return sendContent;
    }

    public WmInfoSmsTimerRepository getInfoSmsTimerRepository() {
        return infoSmsTimerRepository;
    }

    public void setInfoSmsTimerRepository(WmInfoSmsTimerRepository infoSmsTimerRepository) {
        this.infoSmsTimerRepository = infoSmsTimerRepository;
    }

    public ISmsHandleService getSmsHandleService() {
        return smsHandleService;
    }

    public void setSmsHandleService(ISmsHandleService smsHandleService) {
        this.smsHandleService = smsHandleService;
    }
}
