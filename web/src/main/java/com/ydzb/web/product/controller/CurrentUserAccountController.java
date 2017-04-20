package com.ydzb.web.product.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.product.entity.CurrentTradeRecored;
import com.ydzb.product.entity.CurrentUserAccount;
import com.ydzb.product.service.ICurrentUserAccountService;
import com.ydzb.web.product.condition.CurrentUserAccountCondition;
import com.ydzb.web.traderecord.condition.CurrentTradeRecordCondition;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "product/currentaccount")
public class CurrentUserAccountController extends BaseController {


	@Autowired
	private ICurrentUserAccountService currentUserAccountService;

	@RequestMapping(value = "listCurrentAccount", method = { RequestMethod.GET,
			RequestMethod.POST })
	@RequiresPermissions("currentaccount_list")
	public String pageQuery(
			@RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
			@ModelAttribute(value = "condition") String condition,
			@ModelAttribute CurrentUserAccountCondition currentUserAccountCondition,
			Model model) {

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		if (condition != null && condition.length() > 0) {
			currentUserAccountCondition = gson.fromJson(condition, CurrentUserAccountCondition.class);
		}
		// 创建查询
		Searchable searchable = Searchable.newSearchable();
		searchable.addSort(new Sort(Direction.DESC, "allFund","expFund"));
		// 添加查询条件
		searchable.addSearchFilters(currentUserAccountCondition.getAndFilters());
		// 设置分页参数
		searchable.setPage(pageCurrent, pageSize);
		model.addAttribute("condition", gson.toJson(currentUserAccountCondition));
		model.addAttribute("page", currentUserAccountService.findAll(searchable));
		return "product/currentaccount/list";
	}

	/**
	 *
	 * @param condition
	 * @param currentUserAccountCondition
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "exportExcel/{condition}", method = {RequestMethod.GET, RequestMethod.POST})
	@RequiresPermissions("currentaccount_list")
	public String exportExcelFund(
			@ModelAttribute(value = "condition") String condition,
			@ModelAttribute CurrentUserAccountCondition currentUserAccountCondition,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String path = request.getSession().getServletContext().getRealPath("/static/download");
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		if (condition != null && condition.length() > 0) {
			currentUserAccountCondition = gson.fromJson(condition, CurrentUserAccountCondition.class);
		}
		Map<String, Object> filters = currentUserAccountCondition.getSqlFilters();
		List<Object[]> list = currentUserAccountService.findExportData(filters);
		String fileName = currentUserAccountService.exportExcel(list, path);
		redirectAttributes.addFlashAttribute("fileName", fileName);
		return "redirect:/product/currentaccount/listCurrentAccount";
	}

	public ICurrentUserAccountService getCurrentUserAccountService() {
		return currentUserAccountService;
	}

	public void setCurrentUserAccountService(ICurrentUserAccountService currentUserAccountService) {
		this.currentUserAccountService = currentUserAccountService;
	}
}
