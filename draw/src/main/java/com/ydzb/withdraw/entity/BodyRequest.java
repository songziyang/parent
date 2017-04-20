package com.ydzb.withdraw.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class BodyRequest {

	// 商户平台付款账户别名
	private String merPlatAcctAlias;

	// 协议号
	private String protocolNo;

	// 银行名称
	private String bankName;

	// 账号
	private String accountNo;

	// 账户名称
	private String accountName;

	// 账号类型
	private String accountType;

	// 开户行所在省
	private String openProvince;

	// 开户行所在市
	private String openCity;

	// 开户行名称
	private String openName;

	// 交易金额
	private Double tranAmt;

	// 币种
	private String curType;

	// 业务类型
	private String bsnType;

	// 开户证件类型
	private String certType;

	// 证件号
	private String certNo;

	// 手机号码
	private String mobileNo;

	// 商品信息
	private String prodInfo;

	// 附加信息
	private String msgExt;

	public String getMerPlatAcctAlias() {
		return merPlatAcctAlias;
	}

	public void setMerPlatAcctAlias(String merPlatAcctAlias) {
		this.merPlatAcctAlias = merPlatAcctAlias;
	}

	public String getProtocolNo() {
		return protocolNo;
	}

	public void setProtocolNo(String protocolNo) {
		this.protocolNo = protocolNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getOpenProvince() {
		return openProvince;
	}

	public void setOpenProvince(String openProvince) {
		this.openProvince = openProvince;
	}

	public String getOpenCity() {
		return openCity;
	}

	public void setOpenCity(String openCity) {
		this.openCity = openCity;
	}

	public String getOpenName() {
		return openName;
	}

	public void setOpenName(String openName) {
		this.openName = openName;
	}

	public Double getTranAmt() {
		return tranAmt;
	}

	public void setTranAmt(Double tranAmt) {
		this.tranAmt = tranAmt;
	}

	public String getCurType() {
		return curType;
	}

	public void setCurType(String curType) {
		this.curType = curType;
	}

	public String getBsnType() {
		return bsnType;
	}

	public void setBsnType(String bsnType) {
		this.bsnType = bsnType;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getProdInfo() {
		return prodInfo;
	}

	public void setProdInfo(String prodInfo) {
		this.prodInfo = prodInfo;
	}

	public String getMsgExt() {
		return msgExt;
	}

	public void setMsgExt(String msgExt) {
		this.msgExt = msgExt;
	}

}
