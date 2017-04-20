package com.ydzb.account.service;


import com.ydzb.account.context.IDRange;

public interface IWmInfoSmsTimerService {


    /**
     * 短信自动定时发送记录 最大ID 和 最小ID
     *
     * @return
     */
    public IDRange findMaxIdAndMinId();


    /**
     * 自动发送短信
     *
     * @param id
     * @throws Exception
     */
    public void autoSendSmsTimer(Long id) throws Exception;


    /**
     * 定时发送短信
     *
     * @param flag       模板
     * @param mobile     手机号
     * @param replaceStr 替换字符串
     * @return
     * @throws Exception
     */
    public String sendWmInfoSmsTimer(String flag, String mobile, String replaceStr) throws Exception;

}
