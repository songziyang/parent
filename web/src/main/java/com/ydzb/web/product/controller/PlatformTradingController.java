package com.ydzb.web.product.controller;

import com.alibaba.druid.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.product.service.IPlatformTradingDealService;
import com.ydzb.product.service.IPlatformTradingService;
import com.ydzb.web.product.condition.PlatformTradingCondition;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("product/platformTrading")
public class PlatformTradingController extends BaseController {

	@Autowired
	private IPlatformTradingService platformTradingService;
	@Autowired
	private IPlatformTradingDealService platformTradingDealService;

	@RequestMapping(value = "list", method = { RequestMethod.GET,
			RequestMethod.POST })
	@RequiresPermissions("platformtrading_list")
	public String pageQuery(
			@RequestParam(defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(defaultValue = PAGE_CURRENT) int pageCurrent,
			@ModelAttribute("condition") String condition,
			@ModelAttribute("platformTradingCondition") PlatformTradingCondition platformTradingCondition,
			Model model) {

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		if (!StringUtils.isEmpty(condition)) {
			platformTradingCondition = gson.fromJson(condition,
					PlatformTradingCondition.class);
		}
		// 创建查询
		Searchable searchable = Searchable.newSearchable();
		// 添加查询条件
		searchable.addSearchFilters(platformTradingCondition.getAndFilters());
		// 设置排序条件
		searchable.addSort(new Sort(Direction.DESC, "operationTime"));
		// 设置分页参数
		searchable.setPage(pageCurrent, pageSize);

		model.addAttribute("condition", gson.toJson(platformTradingCondition));
		model.addAttribute("page", platformTradingService.findAll(searchable));
		model.addAttribute("total", platformTradingService.findSumFund(platformTradingCondition.getType(),
				platformTradingCondition.getStartDate(), platformTradingCondition.getEndDate()));
		packingGraphData(model);
		return "product/platformtrading/list";
	}

	/**
	 * 封装图标所需数据
	 * @param model
	 */
	public void packingGraphData(Model model) {
		Gson gson = new Gson();
		Map<String, String[]> map = platformTradingService.queryDates();
		model.addAttribute("dates", gson.toJson(map.get("dates")));
		model.addAttribute("recharges", gson.toJson(map.get("recharges")));
		model.addAttribute("withdraws", gson.toJson(map.get("withdraws")));
		model.addAttribute("currents", gson.toJson(map.get("currents")));
		model.addAttribute("regulars", gson.toJson(map.get("regulars")));
		model.addAttribute("institutions", gson.toJson(map.get("institutions")));
		model.addAttribute("privateOrderings", gson.toJson(map.get("privateOrderings")));
		model.addAttribute("frees", gson.toJson(map.get("frees")));
	}

	/**
	 * 导出excel
	 * @param condition
	 * @param platformTradingCondition
	 * @param session
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "exportExcel/{condition}", method = { RequestMethod.GET, RequestMethod.POST })
	public String exportExcel(@PathVariable("condition") String condition,
			PlatformTradingCondition platformTradingCondition,
			HttpSession session, RedirectAttributes redirectAttributes ) {
		
		String logoRealPathDir = session.getServletContext().getRealPath("/static/download");
		Gson gson = new GsonBuilder().
				excludeFieldsWithoutExposeAnnotation().create();
		if (!StringUtils.isEmpty(condition)) {
			platformTradingCondition = gson.fromJson(condition, PlatformTradingCondition.class);
		}
		Map<String, Object> filter = platformTradingCondition.getSqlFilters();
		List<Object[]> list = platformTradingService.findExportData(filter);
		String fileName = platformTradingService.exportExcel(list, logoRealPathDir);
		redirectAttributes.addFlashAttribute("fileName", fileName);
		redirectAttributes.addFlashAttribute("condition", condition);
		//session.setAttribute("message", "导出成功");
		return "redirect:/product/platformTrading/list";
	}
}