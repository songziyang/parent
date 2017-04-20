package com.ydzb.core.context;

import java.math.BigDecimal;

public class B {
	//提现扣费
	public static  BigDecimal WITHDRAWAL_FEE = BigDecimal.valueOf(2.0);



	//------------- 短信发送第三方访问接口------------------//

	//http://sms.pro-group.com.cn/sms.aspx?action=overage&userid=338&account=1069012500112&password=1069012500112
	static String sms_userid = "338";
	static String sms_account = "100179";
	static String sms_password = "reVvMaNu";
	static String sms_url_head = "http://118.178.117.163/myuan/sms?";
	static String sms_url_base = sms_url_head + "?" + "userid=" + sms_userid + "&account=" + sms_account + "&password=" + sms_password;

	//余额查询url
	public static String sms_url_overage = sms_url_base + "&action=overage";
	//发送url
	public static String sms_url_send = sms_url_base + "&action=send";

	//------------- 身份验证发送第三方访问接口------------------//
	public static String idCard_userId = "100030";
	public static String idCard_url = "http://121.40.136.150:8080/IdInDataAu/api/authenInfoApi.htm";
	public static String idCard_md5key = "l3L1ZNU5e5hi93BnSd9I3w3H1I531zrg";
	public static String idCard_desKey = "f77Q33ZQtg75A39COA9ei3G579H7ki7b";

	/**
	 * 短信余额查询接口
	 */
	public static final String BASEURL = "http://121.41.85.249:28080/chif10";
	// 用户ID
	public static final String USERID = "ab0043";
	// 帐号密码
	public static final String PASSWORD = "125789";
}
