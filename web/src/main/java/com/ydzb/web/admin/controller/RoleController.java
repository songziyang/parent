package com.ydzb.web.admin.controller;

import javax.servlet.http.HttpSession;

import com.ydzb.admin.entity.Role;
import com.ydzb.admin.service.IMenuService;
import com.ydzb.admin.service.IPurviewService;
import com.ydzb.admin.service.IRoleService;
import com.ydzb.admin.shiro.Contains;
import com.ydzb.admin.shiro.ShiroUser;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import com.ydzb.web.admin.condition.RoleCondition;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
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
@RequestMapping(value = "/admin/role")
public class RoleController extends BaseController {

	@Autowired
	private IRoleService roleService;

	@Autowired
	private IPurviewService purviewService;

	@Autowired
	private IMenuService menuService;

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
	@RequestMapping(value = "listRole", method = { RequestMethod.GET,
			RequestMethod.POST })
	@RequiresPermissions("role_list")
	public String pageQuery(
			@RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
			@ModelAttribute(value = "condition") String condition,
			@ModelAttribute RoleCondition roleCondition, Model model) {

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		if (condition != null && condition.length() > 0) {
			roleCondition = gson.fromJson(condition, RoleCondition.class);
		}

		// 创建查询
		Searchable searchable = Searchable.newSearchable();
		// 添加查询条件
		searchable.addSearchFilters(roleCondition.getAndFilters());
		// 设置分页参数
		searchable.setPage(pageCurrent, pageSize);
		// 设置排序条件
		searchable.addSort(new Sort(Direction.DESC, "created"));
		model.addAttribute("condition", gson.toJson(roleCondition));
		model.addAttribute("page", roleService.findAll(searchable));
		return "admin/role/list";
	}

	// 保存
	@RequestMapping(value = "saveRole", method = RequestMethod.POST)
	@RequiresPermissions(value = { "role_create", "role_edit" }, logical = Logical.OR)
	public String saveRole(Role role, HttpSession session, Model model)
			throws Exception {
		String result = roleService.saveRole(role);
		if (StringUtils.isEmpty(result)) {
			session.setAttribute("message", SAVE_SUCCESS);
			return "redirect:/admin/role/listRole";
		} else {
			model.addAttribute("role", role);
			session.setAttribute("error", result);
			return "admin/role/edit";
		}
	}

	// 添加
	@RequestMapping(value = "createRole", method = { RequestMethod.GET,
			RequestMethod.POST })
	@RequiresPermissions("role_create")
	public String createRole() {

		return "admin/role/edit";
	}

	// 删除
	@RequestMapping(value = "deleteRole/{id}", method = { RequestMethod.GET,
			RequestMethod.POST })
	@RequiresPermissions("role_del")
	public String deleteRole(@PathVariable("id") Long id, HttpSession session)
			throws Exception {
		String result = roleService.deleteRole(id);
		if (StringUtils.isEmpty(result)) {
			session.setAttribute("message", DELETE_SUCCESS);
		} else {
			session.setAttribute("error", result);
		}
		return "redirect:/admin/role/listRole";
	}

	// 批量删除
	@RequestMapping(value = "deleteRoles", method = { RequestMethod.GET,
			RequestMethod.POST })
	@RequiresPermissions("role_del")
	public String deleteRoles(Long[] ids, HttpSession session) throws Exception {
		String result = roleService.deleteRole(ids);
		if (StringUtils.isEmpty(result)) {
			session.setAttribute("message", DELETE_SUCCESS);
		} else {
			session.setAttribute("error", result);
		}
		return "redirect:/admin/role/listRole";
	}

	// 编辑

	@RequestMapping(value = "editRole/{id}", method = { RequestMethod.GET,
			RequestMethod.POST })
	@RequiresPermissions("role_edit")
	public String editRole(@PathVariable("id") Long id, Model model) {
		model.addAttribute("role", roleService.findOne(id));
		return "admin/role/edit";
	}

	// 授权
	@RequestMapping(value = "authorization/{id}", method = { RequestMethod.GET,
			RequestMethod.POST })
	@RequiresPermissions("role_authorization")
	public String authorization(@PathVariable("id") Long id, Model model) {
		model.addAttribute("role", roleService.findOne(id));
		Subject currentUser = SecurityUtils.getSubject();
		ShiroUser shiroUser = (ShiroUser) currentUser.getPrincipal();
		if (shiroUser != null) {
			if (Contains.USERNAME.equals(shiroUser.getUsername())) {
				model.addAttribute("menuLst", menuService.findAll());
				model.addAttribute("purviewLst", purviewService.findAll());
			} else {
				Searchable searchable = Searchable.newSearchable();
				searchable.and(SearchFilterHelper.newCondition("flag",
								SearchOperator.prefixNotLike, "menu_"),
						SearchFilterHelper.newCondition("flag",
								SearchOperator.prefixNotLike, "purview_"));
				Searchable search = Searchable.newSearchable();
				search.and(SearchFilterHelper.newCondition("purflag",
						SearchOperator.prefixNotLike, "menu_"),
						SearchFilterHelper.newCondition("purflag",
								SearchOperator.prefixNotLike, "purview_"));
				model.addAttribute("menuLst",
						menuService.findAllWithNoPageNoSort(search));
				model.addAttribute("purviewLst",
						purviewService.findAllWithNoPageNoSort(searchable));
			}
		}
		return "admin/role/author";
	}

	@RequestMapping(value = "saveAuthorization", method = RequestMethod.POST)
	@RequiresPermissions("role_authorization")
	public String saveAuthorization(Role role, Long[] purviewids,
			HttpSession session) throws Exception {
		roleService.saveAuthorization(role, purviewids);
		session.setAttribute("message", "授权成功");
		return "redirect:/admin/role/listRole";
	}

	public IRoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}

	public IPurviewService getPurviewService() {
		return purviewService;
	}

	public void setPurviewService(IPurviewService purviewService) {
		this.purviewService = purviewService;
	}

	public IMenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(IMenuService menuService) {
		this.menuService = menuService;
	}

}
