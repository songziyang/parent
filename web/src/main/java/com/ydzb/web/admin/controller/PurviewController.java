package com.ydzb.web.admin.controller;

import javax.servlet.http.HttpSession;

import com.ydzb.admin.entity.Purview;
import com.ydzb.admin.service.IPurviewService;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.core.message.BJUIMessage;
import com.ydzb.web.admin.condition.PurviewCondition;
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
@RequestMapping(value = "/admin/purview")
public class PurviewController extends BaseController {
	@Autowired
	private IPurviewService purviewService;

	// 查询
	@RequestMapping(value = "listPurview", method = { RequestMethod.GET,
			RequestMethod.POST })
	@RequiresPermissions("purview_list")
	public String pageQuery(
			@RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
			@ModelAttribute(value = "condition") String condition,
			@ModelAttribute PurviewCondition purviewCondition, Model model) {

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		if (condition != null && condition.length() > 0) {
			purviewCondition = gson.fromJson(condition, PurviewCondition.class);
		}

		Searchable searchable = Searchable.newSearchable();
		searchable.addSearchFilters(purviewCondition.getAndFilters());
		searchable.setPage(pageCurrent, pageSize);
		// 设置排序条件
		searchable.addSort(new Sort(Direction.DESC, "created"));
		model.addAttribute("condition", gson.toJson(purviewCondition));
		model.addAttribute("page", purviewService.findAll(searchable));
		return "admin/purview/list";
	}

	// 添加
	@RequestMapping(value = "createPurview", method = { RequestMethod.GET,
			RequestMethod.POST })
	@RequiresPermissions("purview_create")
	public String create(Model model) {
		return "admin/purview/edit";
	}

	@RequestMapping(value = "savePurview", method = RequestMethod.POST)
	@RequiresPermissions(value = { "purview_create", "purview_edit" }, logical = Logical.OR)
	public String save(Purview purview, HttpSession session) throws Exception {
		if (purview == null || purview.getId() == null) {
			purview.setCreated(System.currentTimeMillis());
		}
		purviewService.save(purview);
		session.setAttribute("message", SAVE_SUCCESS);
		return "redirect:/admin/purview/listPurview";
	}

	// 删除
	@RequestMapping(value = "deletePurview/{id}", method = { RequestMethod.GET,
			RequestMethod.POST })
	@RequiresPermissions("purview_del")
	public String delete(@PathVariable("id") Long id, HttpSession session)
			throws Exception {
		purviewService.delete(id);
		session.setAttribute("message", DELETE_SUCCESS);
		return "redirect:/admin/purview/listPurview";
	}

	// 批量删除
	@RequestMapping(value = "deletePurviews", method = { RequestMethod.GET,
			RequestMethod.POST })
	@RequiresPermissions("purview_del")
	public String deletes(Long[] ids, HttpSession session) throws Exception {
		purviewService.delete(ids);
		session.setAttribute("message", DELETE_SUCCESS);
		return "redirect:/admin/purview/listPurview";
	}

	// 编辑
	@RequestMapping(value = "editPurview/{id}", method = { RequestMethod.GET,
			RequestMethod.POST })
	@RequiresPermissions("purview_edit")
	public String editAdmin(@PathVariable("id") Long id, Model model) {
		model.addAttribute("purview", purviewService.findOne(id));
		return "admin/purview/edit";
	}

	@RequestMapping(value = "withoutPurview", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String withoutPurview(BJUIMessage bjuiMessage) {

		return "error/404";
	}

	public IPurviewService getPurviewService() {
		return purviewService;
	}

	public void setPurviewService(IPurviewService purviewService) {
		this.purviewService = purviewService;
	}

}
