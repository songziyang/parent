package com.ydzb.withdraw.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;


@XmlAccessorType(XmlAccessType.FIELD)
public class HeaderRequest {

	// 版本号
	private String version;

	// 报文类型，对于请求报文，type域值为0001
	private String msgtype;

	// 99
	private String channelno;

	// 商户号
	private String merchantno;

	// 交易日期
	private String trandate;

	// 交易时间
	private String trantime;

	// 交易流水号
	private String bussflowno;

	// 请求交易码，随交易不同而不同
	private String trancode;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public String getChannelno() {
		return channelno;
	}

	public void setChannelno(String channelno) {
		this.channelno = channelno;
	}

	public String getMerchantno() {
		return merchantno;
	}

	public void setMerchantno(String merchantno) {
		this.merchantno = merchantno;
	}

	public String getTrandate() {
		return trandate;
	}

	public void setTrandate(String trandate) {
		this.trandate = trandate;
	}

	public String getTrantime() {
		return trantime;
	}

	public void setTrantime(String trantime) {
		this.trantime = trantime;
	}

	public String getBussflowno() {
		return bussflowno;
	}

	public void setBussflowno(String bussflowno) {
		this.bussflowno = bussflowno;
	}

	public String getTrancode() {
		return trancode;
	}

	public void setTrancode(String trancode) {
		this.trancode = trancode;
	}

}
