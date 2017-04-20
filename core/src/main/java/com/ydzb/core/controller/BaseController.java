package com.ydzb.core.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
public abstract class BaseController {

	public static final String LOGIN_SUCCESS = "0";

	public static final String LOGIN_ERROR = "1";

	public static final String PAGE_SIZE = "10";
	
	public static final String PAGE_CURRENT = "0";

	public static final int OK = 200;
	
	public static final int ERROR = 300;
	
	public static final int TIMEOUT = 301;
	
	public static final String SAVE_SUCCESS = "保存成功！" ;
	
	public static final String DELETE_SUCCESS ="删除成功！";
	

}
