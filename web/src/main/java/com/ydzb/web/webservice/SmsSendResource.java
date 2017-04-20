package com.ydzb.web.webservice;

import com.ydzb.sms.service.ISmsHandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.net.URLDecoder;


@Path("sms")
@Scope("prototype")
@Produces(value = MediaType.APPLICATION_JSON)
public class SmsSendResource {

    @Autowired
    private ISmsHandleService smsHandleService;

    /**
     * 发送短信信息
     *
     * @param flag    模板标识
     * @param mobile  手机号
     * @param content 发送占位字符串 例子：
     *                "user_realname:刘驰,sex:男,date:2014年12月22日17时13分,money:1000"
     * @return 成功返回 "ok",失败返回 "error"
     */
    @GET
    @Path("sendSms/{flag}/{mobile}/{content}")
    public String sendSms(@PathParam("flag") String flag,
                          @PathParam("mobile") String mobile,
                          @PathParam("content") String content) {
        return smsHandleService.sendUserSms(flag, mobile, URLDecoder.decode(content));
    }

    /**
     * 发送短信验证码信息
     *
     * @param flag     模板标识
     * @param mobile   手机号
     * @param content  发送占位字符串 例子：
     *                 "user_realname:刘驰,sex:男,date:2014年12月22日17时13分,money:1000"
     * @param codeType 验证码类型
     * @param code     验证码类型
     * @return 成功返回 "ok",失败返回 "error"
     */
    @GET
    @Path("sendCodeSms/{flag}/{mobile}/{content}/{codeType}/{code}/{ips}")
    public String sendCodeSms(@PathParam("flag") String flag,
                              @PathParam("mobile") String mobile,
                              @PathParam("content") String content,
                              @PathParam("codeType") int codeType,
                              @PathParam("code") String code, @PathParam("ips") String ips) {
        return smsHandleService.sendUserCodeSms(flag, mobile, URLDecoder.decode(content), (byte) codeType, code, ips);
    }

    /**
     * 发送站内信信息
     *
     * @param flag    模板标识
     * @param userid  前台接收用户ID
     * @param title   标题
     * @param content 发送占位字符串 例子：
     *                "user_realname:刘驰,sex:男,date:2014年12月22日17时13分,money:1000"
     * @return 成功返回 "OK",失败返回错误信息字符串
     */
    @GET
    @Path("sendSiteMessage/{flag}/{userid}/{title}/{content}/{type}")
    public String sendSiteMessage(@PathParam("flag") String flag, @PathParam("userid") Long userid, @PathParam("title") String title, @PathParam("content") String content, @PathParam("type") Integer type) {
        return smsHandleService.addSiteContent(flag, userid, URLDecoder.decode(title), URLDecoder.decode(content), type);
    }

    public ISmsHandleService getSmsHandleService() {
        return smsHandleService;
    }

    public void setSmsHandleService(ISmsHandleService smsHandleService) {
        this.smsHandleService = smsHandleService;
    }

}
