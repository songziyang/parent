package com.ydzb.web.message.controller;

import javax.servlet.http.HttpSession;

import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.message.service.IInfoTemplateService;
import com.ydzb.sms.entity.InfoTemplate;
import com.ydzb.sms.service.ISmsHandleService;
import com.ydzb.web.message.condition.InfoTemplateCondition;
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
@RequestMapping(value = "infoMessage/infoTemplate")
public class InfoTemplateController extends BaseController {

	@Autowired
	private IInfoTemplateService infoTemplateService;

	@Autowired
	private ISmsHandleService smsHandleService;

	@RequestMapping(value = "listInfoTemplate", method = { RequestMethod.GET,
			RequestMethod.POST })
	@RequiresPermissions("infoTemplate_list")
	public String pageQuery(
			@RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
			@ModelAttribute(value = "condition") String condition,
			@ModelAttribute InfoTemplateCondition infoTemplateCondition,
			Model model) {

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		if (condition != null && condition.length() > 0) {
			infoTemplateCondition = gson.fromJson(condition,
					InfoTemplateCondition.class);
		}

		// 创建查询
		Searchable searchable = Searchable.newSearchable();
		// 添加查询条件
		searchable.addSearchFilters(infoTemplateCondition.getAndFilters());
		// 设置分页参数
		searchable.setPage(pageCurrent, pageSize);
		// 设置排序条件
		searchable.addSort(new Sort(Direction.DESC, "created"));

		model.addAttribute("condition", gson.toJson(infoTemplateCondition));
		model.addAttribute("page", infoTemplateService.findAll(searchable));

		return "infoMessage/infoTemplate/list";
	}

	// 添加
	@RequestMapping(value = "createInfoTemplate", method = { RequestMethod.GET,
			RequestMethod.POST })
	@RequiresPermissions("infoTemplate_create")
	public String createSmsTemplate(Model model) {
		return "infoMessage/infoTemplate/edit";
	}

	// 编辑
	@RequestMapping(value = "editInfoTemplate/{id}", method = {
			RequestMethod.GET, RequestMethod.POST })
	@RequiresPermissions("infoTemplate_edit")
	public String editAdmin(@PathVariable("id") Long id, Model model) {
		model.addAttribute("infoTemplate", infoTemplateService.findOne(id));
		return "infoMessage/infoTemplate/edit";
	}

	// 保存
	@RequestMapping(value = "saveInfoTemplate", method = RequestMethod.POST)
	@RequiresPermissions(value = { "infoTemplate_create", "infoTemplate_edit" }, logical = Logical.OR)
	public String save(InfoTemplate infoTemplate, HttpSession session,
			Model model) throws Exception {
		String result = infoTemplateService.saveInfoTemplate(infoTemplate);
		if (StringUtils.isEmpty(result)) {
			session.setAttribute("message", SAVE_SUCCESS);
			return "redirect:/infoMessage/infoTemplate/listInfoTemplate";
		} else {
			session.setAttribute("error", result);
			model.addAttribute("infoTemplate", infoTemplate);
			return "infoMessage/infoTemplate/edit";
		}
	}

	// 删除
	@RequestMapping(value = "deleteInfoTemplate/{id}", method = {
			RequestMethod.GET, RequestMethod.POST })
	@RequiresPermissions("infoTemplate_del")
	public String delete(@PathVariable("id") Long id, HttpSession session)
			throws Exception {
		infoTemplateService.deleteInfoTemplate(id);
		session.setAttribute("message", "删除成功");
		return "redirect:/infoMessage/infoTemplate/listInfoTemplate";
	}

	// 批量删除
	@RequestMapping(value = "deleteInfoTemplate", method = { RequestMethod.GET,
			RequestMethod.POST })
	@RequiresPermissions("infoTemplate_del")
	public String deleteSmsTemplate(Long[] ids, HttpSession session)
			throws Exception {
		infoTemplateService.delete(ids);
		session.setAttribute("message", "删除成功");
		return "redirect:/infoMessage/infoTemplate/listInfoTemplate";
	}

	public IInfoTemplateService getInfoTemplateService() {
		return infoTemplateService;
	}

	public void setInfoTemplateService(IInfoTemplateService infoTemplateService) {
		this.infoTemplateService = infoTemplateService;
	}

	public ISmsHandleService getSmsHandleService() {
		return smsHandleService;
	}

	public void setSmsHandleService(ISmsHandleService smsHandleService) {
		this.smsHandleService = smsHandleService;
	}

}
