package com.baofoo.sdk.demo;


import com.baofoo.sdk.http.HttpUtil;
import com.baofoo.sdk.util.SecurityUtil;
import com.baofoo.sdk.util.TransConstant;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 商户信息系统接口说明
 * 此接口没有测试环境只有正式环境。
 *
 * @author 宝付（大圣）
 */
public class MITestDemo {

    public static void main(String[] args) throws IOException {

        Map<String, String> PostParams = new HashMap<String, String>();

        PostParams.put("member_id", TransConstant.member_id);//	商户号
        PostParams.put("terminal_id", TransConstant.terminal_id);//	终端号
        PostParams.put("return_type", TransConstant.data_type_json);//	返回报文数据类型xml 或json
        PostParams.put("trans_code", "BF0001");//	交易码
        PostParams.put("version", "4.0.0");//版本号
        PostParams.put("account_type", "1");//帐户类型--0:全部、1:基本账户、2:未结算账户、3:冻结账户、4:保证金账户、5:资金托管账户；

        String MAK = "&";//分隔符
        String KeyString = "abcdefg";

        String Md5AddString = "member_id=" + PostParams.get("member_id") + MAK + "terminal_id=" + PostParams.get("terminal_id") + MAK + "return_type=" + PostParams.get("return_type") + MAK + "trans_code=" + PostParams.get("trans_code") + MAK + "version=" + PostParams.get("version") + MAK + "account_type=" + PostParams.get("account_type") + MAK + "key=" + KeyString;
        log("Md5拼接字串:" + Md5AddString);//商户在正式环境不要输出此项以免泄漏密钥，只在测试时输出以检查验签失败问题

        String Md5Sing = SecurityUtil.MD5(Md5AddString).toUpperCase();//必须为大写
        PostParams.put("sign", Md5Sing);

        String Re_Url = "https://public.baofoo.com/open-service/query/service.do";//正式请求地址

        String RetrunString = HttpUtil.RequestForm(Re_Url, PostParams);

        log("返回：" + RetrunString);
    }


    public static void log(String msg) {
        System.out.println(getTime() + "\t: " + msg);
    }

    private static String getTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}