package com.ydzb.web.message.controller;

import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.message.service.ISmsLogService;
import com.ydzb.sms.entity.Overage;
import com.ydzb.sms.service.ISmsHandleService;
import com.ydzb.web.message.condition.SmsLogCondition;
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
@RequestMapping(value = "infoMessage/smsLog")
public class SmsLogController extends BaseController {

	@Autowired
	private ISmsLogService smsLogService;

	@Autowired
	private ISmsHandleService smsHandleService;

	@RequestMapping(value = "listSmsLog", method = { RequestMethod.GET,
			RequestMethod.POST })
	@RequiresPermissions("smsLog_list")
	public String pageQuery(
			@RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
			@ModelAttribute(value = "condition") String condition,
			@ModelAttribute SmsLogCondition smsLogCondition, Model model) {

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		if (condition != null && condition.length() > 0) {
			smsLogCondition = gson.fromJson(condition, SmsLogCondition.class);
		}

		// 短信余额提示
		Overage smsInfo = smsHandleService.queryOverage();
		// 创建查询
		Searchable searchable = Searchable.newSearchable();
		// 添加查询条件
		searchable.addSearchFilters(smsLogCondition.getAndFilters());
		// 设置分页参数
		searchable.setPage(pageCurrent, pageSize);
		// 设置排序条件
		searchable.addSort(new Sort(Direction.DESC, "created"));
		model.addAttribute("overage", smsInfo.getOverage());
		model.addAttribute("condition", gson.toJson(smsLogCondition));
		model.addAttribute("page", smsLogService.findAll(searchable));
		return "infoMessage/smsLog/list";
	}

	public String test() {
		return "test";
	}

	public ISmsLogService getSmsLogService() {
		return smsLogService;
	}

	public void setSmsLogService(ISmsLogService smsLogService) {
		this.smsLogService = smsLogService;
	}

	public ISmsHandleService getSmsHandleService() {
		return smsHandleService;
	}

	public void setSmsHandleService(ISmsHandleService smsHandleService) {
		this.smsHandleService = smsHandleService;
	}

}
