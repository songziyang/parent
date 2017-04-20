package com.ydzb.withdraw.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "message")
@XmlAccessorType(XmlAccessType.FIELD)
public class AccountBalanceRequest {

	@XmlElement(name = "head")
	private HeaderRequest headerRequest;

	@XmlElement(name = "body")
	private AccountBalanceBodyRequest bodyRequest;

	public HeaderRequest getHeaderRequest() {
		return headerRequest;
	}

	public void setHeaderRequest(HeaderRequest headerRequest) {
		this.headerRequest = headerRequest;
	}

	public AccountBalanceBodyRequest getBodyRequest() {
		return bodyRequest;
	}

	public void setBodyRequest(AccountBalanceBodyRequest bodyRequest) {
		this.bodyRequest = bodyRequest;
	}

}
