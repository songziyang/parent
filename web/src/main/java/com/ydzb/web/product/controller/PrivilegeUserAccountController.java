package com.ydzb.web.product.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.product.service.ICurrentUserAccountService;
import com.ydzb.product.service.IPrivilegeUserAccountService;
import com.ydzb.web.product.condition.CurrentUserAccountCondition;
import com.ydzb.web.product.condition.PrivilegeUserAccountCondition;
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

/**
 *新手宝已购管理
 */
@Controller
@RequestMapping(value = "product/privilegeaccount")

public class PrivilegeUserAccountController extends BaseController {


	@Autowired
	private IPrivilegeUserAccountService privilegeUserAccountService;

	@RequestMapping(value = "listPrivilegeAccount", method = { RequestMethod.GET,
			RequestMethod.POST })
	@RequiresPermissions("privilegeaccount_list")
	public String pageQuery(
			@RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
			@ModelAttribute(value = "condition") String condition,
			@ModelAttribute PrivilegeUserAccountCondition privilegeUserAccountCondition,
			Model model) {

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		if (condition != null && condition.length() > 0) {
			privilegeUserAccountCondition = gson.fromJson(condition, PrivilegeUserAccountCondition.class);
		}
		// 创建查询
		Searchable searchable = Searchable.newSearchable();
		searchable.addSort(new Sort(Direction.DESC, "allFund"));
		// 添加查询条件
		searchable.addSearchFilters(privilegeUserAccountCondition.getAndFilters());
		// 设置分页参数
		searchable.setPage(pageCurrent, pageSize);
		model.addAttribute("condition", gson.toJson(privilegeUserAccountCondition));
		model.addAttribute("page", privilegeUserAccountService.findAll(searchable));
		return "product/privilegeaccount/list";
	}

	/**
	 *
	 * @param condition
	 * @param privilegeUserAccountCondition
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "exportExcel/{condition}", method = {RequestMethod.GET, RequestMethod.POST})
	@RequiresPermissions("privilegeaccount_list")
	public String exportExcelFund(
			@ModelAttribute(value = "condition") String condition,
			@ModelAttribute PrivilegeUserAccountCondition privilegeUserAccountCondition,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String path = request.getSession().getServletContext().getRealPath("/static/download");
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		if (condition != null && condition.length() > 0) {
			privilegeUserAccountCondition = gson.fromJson(condition, PrivilegeUserAccountCondition.class);
		}
		Map<String, Object> filters = privilegeUserAccountCondition.getSqlFilters();
		List<Object[]> list = privilegeUserAccountService.findExportData(filters);
		String fileName = privilegeUserAccountService.exportExcel(list, path);
		redirectAttributes.addFlashAttribute("fileName", fileName);
		return "redirect:/product/privilegeaccount/listPrivilegeAccount";
	}


}
