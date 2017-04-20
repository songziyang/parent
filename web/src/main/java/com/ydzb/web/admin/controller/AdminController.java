package com.ydzb.web.admin.controller;

import javax.servlet.http.HttpSession;

import com.ydzb.admin.entity.Admin;
import com.ydzb.admin.service.IAdminService;
import com.ydzb.admin.service.IRoleService;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.web.admin.condition.AdminCondition;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@Controller
@RequestMapping(value = "admin/admin")
public class AdminController extends BaseController {

	@Autowired
	private IAdminService adminService;

	@Autowired
	private IRoleService roleService;

	/**
	 * 分页查询 @RequestParam 设置pageSize pageCurrent 默认值
	 * 
	 * @param pageSize
	 *            每页显示数量
	 * @param pageCurrent
	 *            当前页码
	 * @param roleCondition
	 *            查询条件
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "listAdmin", method = { RequestMethod.GET,
			RequestMethod.POST })
	@RequiresPermissions("admin_list")
	public String pageQuery(
			@RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
			@ModelAttribute(value = "condition") String condition,
			@ModelAttribute AdminCondition adminCondition, Model model) {
		
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		if (condition != null && condition.length() > 0) {
			adminCondition = gson.fromJson(condition, AdminCondition.class);
		}
		// 创建查询
		Searchable searchable = Searchable.newSearchable();
		// 添加查询条件
		searchable.addSearchFilters(adminCondition.getAndFilters());
		// 设置分页参数
		searchable.setPage(pageCurrent, pageSize);
		// 设置排序条件
		searchable.addSort(new Sort(Direction.DESC, "created"));
		model.addAttribute("condition", gson.toJson(adminCondition));
		model.addAttribute("page", adminService.findAll(searchable));
		model.addAttribute("roleLst", roleService.findAll());
		return "admin/admin/list";
	}

	// 添加
	@RequestMapping(value = "createAdmin", method = { RequestMethod.GET,
			RequestMethod.POST })
	@RequiresPermissions("admin_create")
	public String createAdmin(Model model) {
		model.addAttribute("roleLst", roleService.findAll());
		return "admin/admin/edit";
	}

	// 保存
	@RequestMapping(value = "saveAdmin", method = RequestMethod.POST)
	@RequiresPermissions(value = { "admin_create", "admin_edit" }, logical = Logical.OR)
	public String saveAdmin(Admin admin, HttpSession session, Model model)
			throws Exception {
		String result = adminService.saveAdmin(admin);
		if (StringUtils.isEmpty(result)) {
			session.setAttribute("message", SAVE_SUCCESS);
			return "redirect:/admin/admin/listAdmin";
		} else {
			model.addAttribute("admin", admin);
			model.addAttribute("roleLst", roleService.findAll());
			session.setAttribute("error", result);
			return "admin/admin/edit";
		}

	}

	// 删除
	@RequestMapping(value = "deleteAdmin/{id}", method = { RequestMethod.GET,
			RequestMethod.POST })
	@RequiresPermissions("admin_del")
	public String deleteAdmin(@PathVariable("id") Long id, HttpSession session)
			throws Exception {
		adminService.deleteAdmin(id);
		session.setAttribute("message", DELETE_SUCCESS);
		return "redirect:/admin/admin/listAdmin";
	}

	// 批量删除
	@RequestMapping(value = "deleteAdmins", method = { RequestMethod.GET,
			RequestMethod.POST })
	@RequiresPermissions("admin_del")
	public String deleteAdmins(Long[] ids, HttpSession session)
			throws Exception {
		adminService.deleteAdmin(ids);
		session.setAttribute("message", DELETE_SUCCESS);
		return "redirect:/admin/admin/listAdmin";
	}

	// 编辑
	@RequestMapping(value = "editAdmin/{id}", method = { RequestMethod.GET,
			RequestMethod.POST })
	@RequiresPermissions("admin_edit")
	public String editAdmin(@PathVariable("id") Long id, Model model) {
		model.addAttribute("admin", adminService.findOne(id));
		model.addAttribute("roleLst", roleService.findAll());
		return "admin/admin/edit";
	}

	public IAdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(IAdminService adminService) {
		this.adminService = adminService;
	}

	public IRoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}

}
