package com.ydzb.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class ContentTypeFilter implements Filter {

	private String charset = "UTF-8";

	private FilterConfig config;

	public void destroy() {
		charset = null;
		config = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		request.setCharacterEncoding(charset);
		response.setCharacterEncoding(charset);
		HttpServletRequest req = (HttpServletRequest) request;

		if (req.getMethod().equalsIgnoreCase("get")) {
			req = new GetHttpServletRequestWrapper(req, charset);
		}

		chain.doFilter(req, response);

	}

	public void init(FilterConfig config) throws ServletException {
		this.config = config;
		String charset = config.getServletContext().getInitParameter("charset");
		if (charset != null && charset.trim().length() != 0) {
			this.charset = charset;
		}
	}

}