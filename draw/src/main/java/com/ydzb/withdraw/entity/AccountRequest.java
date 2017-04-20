package com.ydzb.withdraw.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "message")
@XmlAccessorType(XmlAccessType.FIELD)
public class AccountRequest {

	@XmlElement(name = "head")
	private HeaderRequest headerRequest;

	@XmlElement(name = "body")
	private BodyRequest bodyRequest;

	public HeaderRequest getHeaderRequest() {
		return headerRequest;
	}

	public void setHeaderRequest(HeaderRequest headerRequest) {
		this.headerRequest = headerRequest;
	}

	public BodyRequest getBodyRequest() {
		return bodyRequest;
	}

	public void setBodyRequest(BodyRequest bodyRequest) {
		this.bodyRequest = bodyRequest;
	}

}
