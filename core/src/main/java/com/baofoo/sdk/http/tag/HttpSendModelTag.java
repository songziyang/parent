package com.baofoo.sdk.http.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.baofoo.sdk.http.HttpSendModel;


/**
 * 项目名称：baofoo-fopay-sdk-java
 * 类名称：表单参数
 * 类描述：
 * 创建人：陈少杰
 * 创建时间：2014-10-22 下午2:58:22
 * 修改人：陈少杰
 * 修改时间：2014-10-22 下午2:58:22
 * @version
 */
public class HttpSendModelTag extends SimpleTagSupport {
	private HttpSendModel htmlSendModel;
	private String formName;

	@Override
	public void doTag() throws JspException, IOException {
		PageContext pc = (PageContext) getJspContext();
		JspWriter out = pc.getOut();
		out.println(htmlSendModel.buildPostRequestForm(formName));
	}

	/**
	 * @return the htmlSendModel
	 */
	public HttpSendModel getHtmlSendModel() {
		return htmlSendModel;
	}

	/**
	 * @param htmlSendModel
	 *            the htmlSendModel to set
	 */
	public void setHtmlSendModel(HttpSendModel htmlSendModel) {
		this.htmlSendModel = htmlSendModel;
	}

	/**
	 * @return the formName
	 */
	public String getFormName() {
		return formName;
	}

	/**
	 * @param formName
	 *            the formName to set
	 */
	public void setFormName(String formName) {
		this.formName = formName;
	}

}