package com.ydzb.core.message;
/**
 * 
 * B-JUI 异步请求返回结果
 * 
 * @author kejinyou
 *
 */
public class BJUIMessage {

	/**
	 * 必选。状态码(ok = 200, error = 300, timeout = 301)，可以在BJUI.init时配置三个参数的默认值。
	 */
	private int statusCode;
	/**
	 * 可选。信息内容。
	 */
	private String message;
	/**
	 * 可选。待刷新navtab id，多个id以英文逗号分隔开，当前的navtab id不需要填写，填写后可能会导致当前navtab重复刷新。
	 */
	private String tabid;
	/**
	 * 可选。是否关闭当前窗口(navtab或dialog)。
	 */
	private Boolean closeCurrent;
	/**
	 * 可选。跳转到某个url。
	 */
	private String forward;
	/**
	 * 可选。跳转url前的确认提示信息。
	 */
	private String forwardConfirm;

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTabid() {
		return tabid;
	}

	public void setTabid(String tabid) {
		this.tabid = tabid;
	}


	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	public String getForwardConfirm() {
		return forwardConfirm;
	}

	public void setForwardConfirm(String forwardConfirm) {
		this.forwardConfirm = forwardConfirm;
	}

	public Boolean getCloseCurrent() {
		return closeCurrent;
	}

	public void setCloseCurrent(Boolean closeCurrent) {
		this.closeCurrent = closeCurrent;
	}

}
