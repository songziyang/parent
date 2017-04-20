package com.ydzb.web.message.controller;

import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.message.service.IAuthCodeService;
import com.ydzb.web.message.condition.AuthCodeCondition;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@RequestMapping(value = "infoMessage/authCode")
public class AuthCodeController extends BaseController {

	@Autowired
	private IAuthCodeService authCodeService;

	@RequestMapping(value = "listAuthCode", method = { RequestMethod.GET,
			RequestMethod.POST })
	@RequiresPermissions("authCode_list")
	public String pageQuery(
			@RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
			@ModelAttribute(value = "condition") String condition,
			@ModelAttribute AuthCodeCondition authCodeCondition, Model model) {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		if (condition != null && condition.length() > 0) {
			authCodeCondition = gson.fromJson(condition,
					AuthCodeCondition.class);
		}
		// 创建查询
		Searchable searchable = Searchable.newSearchable();
		// 添加查询条件
		searchable.addSearchFilters(authCodeCondition.getAndFilters());
		// 设置分页参数
		searchable.setPage(pageCurrent, pageSize);
		// 设置排序条件
		searchable.addSort(new Sort(Direction.DESC, "created"));
		model.addAttribute("condition", gson.toJson(authCodeCondition));
		model.addAttribute("page", authCodeService.findAll(searchable));

		return "infoMessage/authCode/list";
	}

}
