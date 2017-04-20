package com.ydzb.sms.service.impl;


import com.google.gson.Gson;
import com.ydzb.core.context.B;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.HttpXmlClient;
import com.ydzb.sms.entity.AuthCode;
import com.ydzb.sms.entity.Message;
import com.ydzb.sms.entity.Overage;
import com.ydzb.sms.entity.SmsLog;
import com.ydzb.sms.repository.SmsHandleRepository;
import com.ydzb.sms.service.ISmsHandleService;
import com.ydzb.sms.utils.IdCardAuth;
import com.ydzb.user.entity.User;
import com.ydzb.user.service.IUserService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class SmsHandleServiceImpl implements ISmsHandleService {


    public Logger logger = Logger.getLogger(SmsHandleServiceImpl.class);


    @Autowired
    private IUserService userService;

    @Autowired
    private SmsHandleRepository smsHandleRepository;

    //发送短信（对外接口）
    @Override
    public String sendContent(String mobile, String content) {
        return sendSms(mobile, content, "");
    }

    @Override
    public String querySmsTemplateByFlag(String flag) {
        return smsHandleRepository.querySmsTemplateByFlag(flag);
    }

    //定时发送用户短信
    @Override
    public String snedUserSmsTimer(String flag, String mobile, String replaceStr,
                                   String timer) {
        String template = smsHandleRepository.querySmsTemplateByFlag(flag);
        if (null == template || "".equals(template)) {
            logger.error("短信发送出错：没有找到模板或者模板内容为空。模板标识为" + flag);
            return "error";
        }
        String sendContent = replaceTemplateContent(flag, template, replaceStr);
        if (sendContent.equals("error")) {
            logger.error("短信发送模板替换出现错误, 手机号:" + mobile + ",模板标识:" + flag + ",替换字符串:" + replaceStr);
            return "error";
        }
        return sendSms(mobile, sendContent, timer);
    }


    // 发送用户短信（对外接口）
    @Override
    public String sendUserSms(String flag, String mobile, String replaceStr) {
        String template = smsHandleRepository.querySmsTemplateByFlag(flag);
        if (null == template || "".equals(template)) {
            logger.error("短信发送出错：没有找到模板或者模板内容为空。模板标识为" + flag);
            return "error";
        }
        String sendContent = replaceTemplateContent(flag, template, replaceStr);
        if (sendContent.equals("error")) {
            logger.error("短信发送模板替换出现错误, 手机号:" + mobile + ",模板标识:" + flag + ",替换字符串:" + replaceStr);
            return "error";
        }
        return sendSms(mobile, sendContent, "");
    }

    // 发送用户验证码短信（对外接口）
    @Override
    public String sendUserCodeSms(String flag, String mobile, String replaceStr,
                                  Byte codeType, String code, String ips) {
        String template = smsHandleRepository.querySmsTemplateByFlag(flag);
        if (null == template || "".equals(template)) {
            logger.error("短信发送出错：没有找到模板或者模板内容为空。模板标识为" + flag);
            return "error";
        }
        String sendContent = replaceTemplateContent(flag, template, replaceStr);
        if (sendContent.equals("error")) {
            logger.error("短信发送模板替换出现错误, 手机号:" + mobile + ",模板标识:" + flag + ",替换字符串:" + replaceStr);
            return "error";
        }
        saveAuthCode(mobile, codeType, code, ips);
        return sendSms(mobile, sendContent, "");
    }

    //群发用户短信（对外接口）
    @Override
    public String sendUserMassSms(String flag, String[] mobile, String replaceStr) {
        String template = smsHandleRepository.querySmsTemplateByFlag(flag);
        if (null == template || "".equals(template)) {
            logger.error("短信发送出错：没有找到模板或者模板内容为空。模板标识为" + flag);
            return "error";
        }
        String sendContent = replaceTemplateContent(flag, template, replaceStr);
        if (sendContent.equals("error")) {
            logger.error("短信发送模板替换出现错误,模板标识:" + flag + ",替换字符串:" + replaceStr);
            return "error";
        }
        return sendMassSms(flag, mobile, sendContent, "");
    }

    public String sendUserMassSms(String[] mobile, String content) {
        return sendMassSms(null, mobile, content, null);
    }

    //定时群发用户短信（对外接口）
    @Override
    public String snedUserMassSmsTimer(String flag, String[] mobile, String replaceStr, String timer) {
        String template = smsHandleRepository.querySmsTemplateByFlag(flag);
        if (null == template || "".equals(template)) {
            logger.error("短信发送出错：没有找到模板或者模板内容为空。模板标识为" + flag);
            return "error";
        }
        String sendContent = replaceTemplateContent(flag, template, replaceStr);
        if (sendContent.equals("error")) {
            logger.error("短信发送模板替换出现错误,模板标识:" + flag + ",替换字符串:" + replaceStr);
            return "error";
        }
        return sendMassSms(flag, mobile, sendContent, timer);
    }


    public void saveAuthCode(String mobile, Byte codeType, String code, String ips) {
        AuthCode ac = new AuthCode();
        ac.setContent(code);
        ac.setName(mobile);
        ac.setType(codeType);
        ac.setStatus((byte) 0);
        ac.setCreated(DateUtil.getSystemTimeSeconds());
        ac.setExpire(DateUtil.getSystemTimeSeconds() + 10 * 60);
        ac.setIps(ips);
        smsHandleRepository.saveAuthCode(ac);
    }


    public String replaceTemplateContent(String flag, String template, String replaceStr) {
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


    // 发送站里信（对外接口）
    @Override
    public String addSiteContent(String flag, Long userid, String title,
                                 String replaceStr, Integer type) {
        // TODO Auto-generated method stub
        try {
            String template = smsHandleRepository.querySiteTemplateByFlag(flag);
            if (null == template || "".equals(template)) {
                logger.error("站里信发送出错：没有找到模板或者模板内容为空。模板标识为" + flag);
                return "error";
            }
            String sendContent = replaceTemplateContent(flag, template, replaceStr);
            if (sendContent.equals("error")) {
                logger.error("站内信发送模板替换出现错误, 用户:" + userid + ",模板标识:" + flag + ",标题:" + title + ",替换字符串:" + replaceStr);
                return "error";
            }
            // 查询用户
            User user = null;
            if (null != userid) {
                user = userService.findOne(userid);
            }
            Message m = new Message();
            m.setUser(user);
            m.setContent(sendContent);
            m.setTitle(title);
            m.setCreated(DateUtil.getSystemTimeSeconds());
            m.setDeleted(0);
            m.setStatus(0);
            m.setType(type);
            smsHandleRepository.saveSiteMessage(m);
            return "ok";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }


    @Override
    public Overage queryOverage() {

        try {
            String result = HttpXmlClient.get(B.sms_url_overage);
            if (!StringUtils.isEmpty(result)) {
                //解析返回的xml数据 dom4j解析
                Document document = DocumentHelper.parseText(result);
                Element root = document.getRootElement();
                String returnstatus = root.elementText("returnstatus");
                if (!StringUtils.isEmpty(returnstatus)) {
                    //获取的数据正确
                    if (returnstatus.equals("Sucess") || returnstatus.equals("Success")) {
                        //余额信息对象
                        Overage oa = new Overage();
                        oa.setOverage(Integer.parseInt(root.elementText("overage")));
                        oa.setSendTotal(Integer.parseInt(root.elementText("sendTotal")));
                        return oa;
                    } else {
                        String errMsg = root.elementText("message");
                        logger.error("短信余额查询出现错误:" + errMsg);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Overage();
    }

    /**
     * 调用第三方后台网管查询余额
     * @return
     */
    @Override
    public Integer postQueryamtf() {
        PrintWriter printWriter = null;
        BufferedReader bufferedReader = null;
        String responseResult = new String();
        HttpURLConnection httpURLConnection = null;
        try {
            String time = new SimpleDateFormat( "yyyyMMddHHmmss" ).format( new Date( ) );			//时间戳yyyyMMddHHmmss
            String token = Hex.encodeHexString( new Md5( ).encrypt( B.USERID + time + B.PASSWORD ) );	//拼接Token
            URL realUrl = new URL(B.BASEURL + "/queryamtf/" +B.USERID+"/"+token);						//拼接URL
            // 打开和URL之间的连接
            httpURLConnection = (HttpURLConnection) realUrl.openConnection();
            String str = B.USERID + ":" + time;
            byte[] datas = str.getBytes("GBK");
            String authorization = new String(Base64.encodeBase64(datas));
            // 设置通用的请求属性
            httpURLConnection.setRequestProperty("accept", "application/json");
            httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            httpURLConnection.setRequestProperty("Authorization", authorization);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            // flush输出流的缓冲
            printWriter.flush();
            // 根据ResponseCode判断连接是否成功
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode != 200) {
                return 0;
            }
            // 定义BufferedReader输入流来读取URL的ResponseData
            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                responseResult += line;
            }
            Gson gson = new Gson();
            SmsOverage overage = gson.fromJson(responseResult, SmsOverage.class);
            if (overage == null || overage.getRspcode() != 0) {
                return 0;
            }
            return overage.getCount();
        } catch (Exception e) {
            return 0;
        } finally {
            httpURLConnection.disconnect();
            try {
                if (printWriter != null) {
                    printWriter.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    public static class SmsOverage {
        int Rspcode;
        int Count;

        public int getRspcode() {
            return Rspcode;
        }

        public void setRspcode(int rspcode) {
            Rspcode = rspcode;
        }

        public int getCount() {
            return Count;
        }

        public void setCount(int count) {
            Count = count;
        }
    }

    /*
	 *  MD5加密
	 */
    public static class Md5 {
        MessageDigest alg;

        public Md5() {
            try {
                alg = MessageDigest.getInstance("MD5");
            } catch( Exception ex ) {
                ex.printStackTrace( );
            }
        }

        public byte[] encrypt(String key) {
            return computeDigest(key.getBytes());
        }

        public byte[] computeDigest(byte[] b) {
            try {
                alg.reset();
                alg.update(b);
                byte[] hash = alg.digest(); // 得到摘要
                return hash;
            } catch(Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }

    public String sendMassSms(String flag, String[] mobiles, String content, String timer) {
        try {

            if (null == mobiles || mobiles.length < 1) {
                return "error";
            }
            //数组转字符串
            String tels = org.apache.shiro.util.StringUtils.toString(mobiles);

            List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
            nvps.add(new BasicNameValuePair("mobile", tels));                        //手机号字符串
            nvps.add(new BasicNameValuePair("content", content));                    //内容
            nvps.add(new BasicNameValuePair("mobilenumber", tels.length() + ""));        //手机号码数量
            nvps.add(new BasicNameValuePair("telephonenumber", tels.length() + ""));    //号码总数量
            //是否有定时发送
            if (null != timer && !"".equals(timer)) {
                nvps.add(new BasicNameValuePair("sendTime", timer));
            }
            //发送返回结果
            String result = HttpXmlClient.post(B.sms_url_send, nvps, "UTF-8");
            if (!StringUtils.isEmpty(result)) {
                //解析返回的xml数据 dom4j解析
                Document document = DocumentHelper.parseText(result);
                Element root = document.getRootElement();
                String message = root.elementText("message");
                String returnstatus = root.elementText("returnstatus");
                //获取的数据正确
                if (message.equals("ok")) {
                    for (String mobile : mobiles) {
                        SmsLog sl = new SmsLog();
                        sl.setPhone(mobile);
                        sl.setContent(content);
                        sl.setDescription(message);
                        sl.setCreated(DateUtil.getSystemTimeSeconds());
                        smsHandleRepository.saveSmsLog(sl);
                    }
                    return "ok";
                } else {
                    logger.error("短信发送出现错误:" + message + ",手机号码:" + tels + ",发送时间:" + System.currentTimeMillis());
                    return "error";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "error";
    }


    public String sendSms(String mobile, String content, String timer) {
        try {

            String url = B.sms_url_send + "&mobile=" + mobile + "&content=" + content + "&mobilenumber=1&telephonenumber=1";

            List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
            nvps.add(new BasicNameValuePair("mobile", mobile));
            nvps.add(new BasicNameValuePair("content", content));
            nvps.add(new BasicNameValuePair("mobilenumber", "1"));
            nvps.add(new BasicNameValuePair("telephonenumber", "1"));
            if (null != timer && !"".equals(timer)) {
                nvps.add(new BasicNameValuePair("sendTime", timer));
            }

            String result = HttpXmlClient.post(B.sms_url_send, nvps, "UTF-8");
            if (!StringUtils.isEmpty(result)) {
                //解析返回的xml数据 dom4j解析
                Document document = DocumentHelper.parseText(result);
                Element root = document.getRootElement();
                String message = root.elementText("message");
                String returnstatus = root.elementText("returnstatus");
                SmsLog sl = new SmsLog();
                sl.setPhone(mobile);
                sl.setContent(content);
                sl.setCreated(DateUtil.getSystemTimeSeconds());
                //获取的数据正确
                if (message.equals("ok")) {
                    sl.setDescription(message);
                    smsHandleRepository.saveSmsLog(sl);
                    return "ok";
                } else {
                    sl.setDescription(message);
                    smsHandleRepository.saveSmsLog(sl);
                    logger.error("短信发送出现错误:" + message + ",手机号码:" + mobile + ",发送时间:" + System.currentTimeMillis());
                    return "error";
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "error";
    }


    //发送身份验证（对外接口）
    @Override
    public String idAuthen(Long userid, String realName, String idCard) {
        try {
            List<BasicNameValuePair> listParams = craeteIdCardUrl(realName, idCard);
            String result = HttpXmlClient.post(B.idCard_url, listParams, "GBK");
            if (!StringUtils.isEmpty(result)) {
                //解析返回的xml数据 dom4j解析
                Document document = DocumentHelper.parseText(result);
                Element root = document.getRootElement();
                //验证结果状态
                String auResultCode = root.elementText("auResultCode");
                //合作商原订单号
                String coopOrderNo = root.elementText("coopOrderNo");
                //第三方订单号
                String auOrderNo = root.elementText("auOrderNo");
                //请求成功时间
                String auSuccessTime = root.elementText("auSuccessTime");
                //返回结果信息
                String auResultInfo = root.elementText("auResultInfo");

                System.out.println("auResultCode=" + auResultCode + "=auResultInfo" + auResultInfo);

                //查询用户信息
                User user = userService.findOne(userid);

                if (user == null) {
                    return "error";
                }

                if (user.getCardVerifyNum() == null || "".equals(user.getCardVerifyNum())) {
                    user.setCardVerifyNum(0);
                }

                //验证成功，只有姓名和身份证号一致的情况下，才是SUCCESS
                if (auResultCode.equals("SUCCESS")) {
                    //更新用户信息中的真实姓名，身份证号，和身份证验证次数
                    user.setRealName(realName);
                    user.setIdCard(idCard);
                    user.setCardVerifyNum(user.getCardVerifyNum() + 1);
                    userService.update(user);
                    logger.info("用户ID:" + userid + "身份验证成功,信息一致, 验证时间:" + auSuccessTime + ",验证结果信息:" + auResultInfo + ", 合作商原订单号:" + coopOrderNo + ", 第三方订单号:" + auOrderNo);
                    return "ok";
                } else if (auResultCode.equals("FAILED")) {
                    //请求成功，但身份验证结果不一致
                    if (auResultInfo.equals("不一致")) {
                        user.setCardVerifyNum(user.getCardVerifyNum() + 1);
                        userService.update(user);
                        logger.info("用户ID:" + userid + "身份验证成功,但信息不一致, 验证时间:" + auSuccessTime + ",验证结果信息:" + auResultInfo + ", 合作商原订单号:" + coopOrderNo + ", 第三方订单号:" + auOrderNo);
                        return "error";
                    } else {
                        user.setCardVerifyNum(user.getCardVerifyNum() + 1);
                        userService.update(user);
                        logger.error("用户ID:" + userid + ",身份验证出现错误:" + auResultInfo + ", 验证时间:" + auSuccessTime + ",验证结果信息:" + auResultInfo + ", 合作商原订单号:" + coopOrderNo + ", 第三方订单号:" + auOrderNo);
                        return "error";
                    }
                }
                return "error";
            } else {
                logger.error("第三方身份验证返回数据为空！用户ID:" + userid + ",用户姓名:" + realName + ",用户身份证号:" + idCard + "返回时间:" + new Date());
                return "error";
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("第三方身份验证服务请求出错！" + e.getMessage());
            return "error";
        }

    }


    //生成身份认证url地址
    public List<BasicNameValuePair> craeteIdCardUrl(String realName, String idCard) {
        String urlStr = "";
        // 网站用户ID
        String idCardUserId = B.idCard_userId;
        // 时间截
        String ts = System.currentTimeMillis() + "";
        // 订单号
        String orderNo = ts;
        // md5KEY
        String mKey = B.idCard_md5key;
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 发送时间
        String dateString = formatter.format(currentTime);
        // 时间和真实姓名转义
        try {
            realName = URLEncoder.encode(realName, "UTF-8");
            dateString = URLEncoder.encode(dateString, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 真实姓名DES加密
        String nameDes = IdCardAuth.Encode(realName);
        // 身份证号DES加密
        String idCodeDes = IdCardAuth.Encode(idCard);
        // 网站用户名+订单号+真实姓名+身份证号+签名
        String md5Str = "userId" + idCardUserId + "coopOrderNo" + orderNo
                + "auName" + realName + "auId" + idCard + "ts" + ts + mKey;
        // 再加密
        md5Str = IdCardAuth.md5Sign(md5Str);

        // 请求参数列表
        List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
        nvps.add(new BasicNameValuePair("userId", idCardUserId));
        nvps.add(new BasicNameValuePair("coopOrderNo", orderNo));
        nvps.add(new BasicNameValuePair("auName", nameDes));
        nvps.add(new BasicNameValuePair("auId", idCodeDes));
        nvps.add(new BasicNameValuePair("reqDate", dateString));
        nvps.add(new BasicNameValuePair("ts", ts));
        nvps.add(new BasicNameValuePair("sign", md5Str));

        // 请求参数，只给下边日志用。
        urlStr = B.idCard_url + "userId=" + idCardUserId + "&coopOrderNo="
                + orderNo + "&auName=" + nameDes + "&auId=" + idCodeDes
                + "&reqDate=" + dateString + "&ts=" + ts + "&sign=" + md5Str;

        logger.info("发送身份验证请求，请求参数:" + urlStr);

        return nvps;
    }


    public IUserService getUserService() {
        return userService;
    }

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }


    public SmsHandleRepository getSmsHandleRepository() {
        return smsHandleRepository;
    }

    public void setSmsHandleRepository(SmsHandleRepository smsHandleRepository) {
        this.smsHandleRepository = smsHandleRepository;
    }
}
