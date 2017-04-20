package com.ydzb.web.admin.controller;

import javax.servlet.http.HttpServletRequest;

import com.ydzb.admin.entity.Admin;
import com.ydzb.admin.service.IAdminService;
import com.ydzb.admin.service.IMenuService;
import com.ydzb.admin.shiro.IncorrectCaptchaException;
import com.ydzb.admin.shiro.ShiroUser;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.shiro.CaptchaUsernamePasswordToken;
import com.ydzb.core.utils.HttpRequestDeviceUtils;
import com.ydzb.web.admin.model.Message;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;


@Controller
public class LoginController extends BaseController {

	@Autowired
	private IMenuService menuService;
	
	@Autowired
	private IAdminService adminService;

	// 跳转到登陆页面
	@RequestMapping(value = "index", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String index() {
		return "login";
	}

	// 拦截登陆方法的get请求，并跳转到登陆页面
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login() {

		return "login";
	}

	// 拦截登陆方法的get请求，并跳转到登陆页面
	@RequestMapping(value = "reLogin", method = {RequestMethod.GET,RequestMethod.POST})
	public String reLogin() {

		return "relogin";
	}

	// 处理登陆POS请求
	@RequestMapping(value = "login", method = RequestMethod.POST)
	@ResponseBody
	public Message fail(@RequestBody String userinfo, Message message) {
		try {
			Gson gson = new Gson();
			Admin admin = gson.fromJson(userinfo, Admin.class);
			// 使用Shiro验证登陆用户的用户名和密码
			SecurityUtils.getSubject().login(
					new CaptchaUsernamePasswordToken(admin.getUsername(), admin
							.getPassword(), admin.getCaptcha()));
			message.setRet(LOGIN_SUCCESS);
		} catch (IncorrectCaptchaException e) {
			// 验证码错误异常处理
			message.setRet(LOGIN_ERROR);
			message.setMsg(e.getMessage());
		} catch (IncorrectCredentialsException e) {
			// 用户名或者密码错误异常处理
			message.setRet(LOGIN_ERROR);
			message.setMsg("用户名或密码错误！");
		}
		return message;
	}

	// 登陆成功跳转到系统首页
	@RequestMapping(value = "main", method = { RequestMethod.POST,
			RequestMethod.GET })
	@RequiresAuthentication
	public String main(Model model,HttpServletRequest request) {
		// model.addAttribute("roleMenuLst",menuService.listRoleMenu());
		Subject currentUser = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) currentUser.getPrincipal();
		if (shiroUser != null) {
			Admin admin = adminService.findOne(shiroUser.getId());
			model.addAttribute("realname", admin.getRealname());
		}
		if (HttpRequestDeviceUtils.isMobileDevice(request)) {
			return "mobile/index";
		}
		return "main";
	}

	@RequestMapping(value = "logout", method = { RequestMethod.POST,
			RequestMethod.GET })
	@RequiresAuthentication
	public String logout() {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			subject.logout();
		}
		return "relogin";
	}

	public IMenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(IMenuService menuService) {
		this.menuService = menuService;
	}

	public IAdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(IAdminService adminService) {
		this.adminService = adminService;
	}

}
