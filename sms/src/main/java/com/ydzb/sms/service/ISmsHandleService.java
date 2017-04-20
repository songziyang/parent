package com.ydzb.sms.service;

import com.ydzb.sms.entity.Overage;

public interface ISmsHandleService {


    /**
     * 发送短信（对外接口）
     *
     * @param mobile
     * @param content
     * @return
     */
    public String sendContent(String mobile, String content);

    /**
     * 发送用户短信（对外接口）
     *
     * @param flag    模板标识
     * @param mobile  手机号码
     * @param content 发送内容
     * @return OK-成功，否则返回错误信息
     */
    public String sendUserSms(String flag, String mobile, String content);

    /**
     * 定时发送用户短信（对外接口）
     *
     * @param flag    模板标识
     * @param mobile  用户手机号
     * @param content 发送内容
     * @param timer   定时发送时间(格式2010-10-24 09:08:10)
     * @return OK-成功，否则返回错误信息
     */
    public String snedUserSmsTimer(String flag, String mobile, String content, String timer);

    /**
     * 群发用户短信(对外接口)
     *
     * @param flag    模板标识
     * @param mobile  手机号码数组
     * @param content 发送内容
     * @return OK-成功，否则返回错误信息
     */
    public String sendUserMassSms(String flag, String[] mobile, String content);

    /**
     * 群发用户短信
     *
     * @param mobile  手机号码
     * @param content 发送内容
     * @return OK-成功，否则返回错误信息
     */
    public String sendUserMassSms(String[] mobile, String content);

    /**
     * 定时群发用户短信（对外接口）
     *
     * @param flag    模板标识
     * @param mobile  手机号码数组
     * @param content 发送内容
     * @param timer   定时发送时间(格式2010-10-24 09:08:10)
     * @return OK-成功，否则返回错误信息
     */
    public String snedUserMassSmsTimer(String flag, String[] mobile, String content, String timer);

    /**
     * 发送用户验证码短信（对外接口）
     *
     * @param flag     模板标识
     * @param mobile   手机号码
     * @param content  发送内容
     * @param codeType 验证码类型
     * @param code     验证码
     * @return OK-成功，否则返回错误信息
     */
    public String sendUserCodeSms(String flag, String mobile, String content, Byte codeType, String code, String ips);


    /**
     * 验证身份证是否正确
     *
     * @param userid   前台用户ID
     * @param realName 用户真实姓名
     * @param idCard   身份证号
     * @return OK-成功，否则返回错误信息
     */
    public String idAuthen(Long userid, String realName, String idCard);


    /**
     * 查询短信余额
     *
     * @return 余额对象
     */
    public Overage queryOverage();

    /**
     * 使用第三方后台接口查询短信余量
     */
    public Integer postQueryamtf();


    /**
     * 添加站内信信息（对外接口）
     *
     * @param flag       模板标识
     * @param userid     用户ID
     * @param title      标题
     * @param replaceStr 要替换的变量字符串
     * @param type       0系统1通知
     * @return OK-成功，否则返回错误信息
     */
    public String addSiteContent(String flag, Long userid, String title, String replaceStr, Integer type);


    public String querySmsTemplateByFlag(String flag);

}
