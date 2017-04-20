package com.ydzb.core.utils;

import javax.servlet.http.HttpServletRequest;

public class HttpRequestDeviceUtils {

	/** 手机浏览器的User-Agent里的关键词 */
	private static String[] mobileUserAgents = new String[] { "Nokia",
			"SAMSUNG", "MIDP-2", "CLDC1.1", "SymbianOS", "MAUI",
			"UNTRUSTED/1.0", "Windows CE", "iPhone", "iPad", "Android",
			"BlackBerry", "UCWEB", "ucweb", "BREW", "J2ME", "YULONG", "YuLong",
			"COOLPAD", "TIANYU", "TY-", "K-Touch", "Haier", "DOPOD", "Lenovo",
			"LENOVO", "HUAQIN", "AIGO-", "CTC/1.0", "CTC/2.0", "CMCC",
			"DAXIAN", "MOT-", "SonyEricsson", "GIONEE", "HTC", "ZTE", "HUAWEI",
			"webOS", "GoBrowser", "IEMobile", "WAP2.0" };

	/**
	 * 根据当前请求的特征，判断该请求是否来自手机终端，主要检测特殊的头信息，以及user-Agent这个header
	 * 
	 * @param request
	 *            http请求
	 * @return 如果命中手机特征规则，则返回对应的特征字符串
	 */
	public static boolean isMobileDevice(HttpServletRequest request) {
		String userAgent = request.getHeader("user-agent");
		for (int i = 0; i < mobileUserAgents.length; i++) {
			if (userAgent.contains(mobileUserAgents[i])) {
				return true;
			}
		}
		return false;
	}

}