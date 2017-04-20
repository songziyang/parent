package com.ydzb.web.admin.controller;

import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.ydzb.admin.entity.Menu;
import com.ydzb.admin.service.IMenuService;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.web.admin.condition.MenuCondition;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URLEncodedUtils;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@Controller
@RequestMapping(value = "admin/menu")
public class MenuController extends BaseController {

	@Autowired
	private IMenuService menuService;

	@RequestMapping(value = "listMenu", method = { RequestMethod.GET,
			RequestMethod.POST })
	@RequiresPermissions("menu_list")
	public String pageQuery(
			@RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
			@ModelAttribute(value = "condition") String condition,
			@ModelAttribute MenuCondition menuCondition, Model model) {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		if (condition != null && condition.length() > 0) {
			menuCondition = gson.fromJson(condition, MenuCondition.class);
		}
		// 创建查询
		Searchable searchable = Searchable.newSearchable();
		// 添加查询条件
		searchable.addSearchFilters(menuCondition.getAndFilters());
		// 设置分页参数
		searchable.setPage(pageCurrent, pageSize);
		// 设置排序条件
		searchable.addSort(new Sort(Direction.DESC, "created"));
		model.addAttribute("condition",  gson.toJson(menuCondition));
		model.addAttribute("page", menuService.findAll(searchable));
		model.addAttribute("menuLst", menuService.findParentMenus());
		return "admin/menu/list";
	}

	// 添加
	@RequestMapping(value = "createMenu", method = { RequestMethod.GET,
			RequestMethod.POST })
	@RequiresPermissions("menu_create")
	public String create(Model model) {
		model.addAttribute("menuLst", menuService.findParentMenus());
		return "admin/menu/edit";
	}

	// 保存
	@RequestMapping(value = "saveMenu", method = RequestMethod.POST)
	@RequiresPermissions(value = { "menu_create", "menu_edit" }, logical = Logical.OR)
	public String save(Menu menu, HttpSession session) throws Exception {
		if (menu.getParentMenu() == null
				|| menu.getParentMenu().getId() == null) {
			menu.setParentMenu(null);
		}
		if (menu == null || menu.getId() == null) {
			menu.setCreated(System.currentTimeMillis());
		}
		menuService.save(menu);
		session.setAttribute("message", SAVE_SUCCESS);
		return "redirect:/admin/menu/listMenu";
	}

	// 删除
	@RequestMapping(value = "deleteMenu/{id}", method = { RequestMethod.GET,
			RequestMethod.POST })
	@RequiresPermissions("menu_del")
	public String delete(@PathVariable("id") Long id, HttpSession session)
			throws Exception {
		String result = menuService.deleteMenu(id);
		if (StringUtils.isEmpty(result)) {
			session.setAttribute("message", DELETE_SUCCESS);
			return "redirect:/admin/menu/listMenu";
		} else {
			session.setAttribute("error", result);
			return "redirect:/admin/menu/listMenu";
		}

	}

	// 批量删除
	@RequestMapping(value = "deleteMenus", method = { RequestMethod.GET,
			RequestMethod.POST })
	@RequiresPermissions("menu_del")
	public String deletes(Long[] ids, HttpSession session) throws Exception {
		String result = menuService.deleteMenu(ids);
		if (StringUtils.isEmpty(result)) {
			session.setAttribute("message", DELETE_SUCCESS);
			return "redirect:/admin/menu/listMenu";
		} else {
			session.setAttribute("error", result);
			return "redirect:/admin/menu/listMenu";
		}
	}

	// 编辑
	@RequestMapping(value = "editMenu/{id}", method = { RequestMethod.GET,
			RequestMethod.POST })
	@RequiresPermissions("menu_edit")
	public String editAdmin(@PathVariable("id") Long id, Model model) {
		model.addAttribute("menu", menuService.findOne(id));
		model.addAttribute("menuLst", menuService.findParentMenus());
		return "admin/menu/edit";
	}

	// 菜单
	@RequestMapping(value = "listRoleMenuJSON", method = RequestMethod.POST)
	@ResponseBody
	public List<Menu> listMenu() {
		return menuService.listRoleMenu();
	}

	public IMenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(IMenuService menuService) {
		this.menuService = menuService;
	}

}
