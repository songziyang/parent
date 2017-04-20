package com.ydzb.core.exception;

public class BaseException extends Exception {
	private static final long serialVersionUID = -8952518314659250274L;
	
	// 提示的出错信息
	private String message;

	// 构造方法
	public BaseException(String message) {
		super(message);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}