package com.ydzb.web.user.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.user.entity.UserManualRecharge;
import com.ydzb.user.service.IUserManualRechargeService;
import com.ydzb.web.user.condition.UserManualRechargeCondition;
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
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "userrecharge/recharge")
public class UserManualRechargeController extends BaseController {


    @Autowired
    private IUserManualRechargeService userManualRechargeService;

    @RequestMapping(value = "listRecharge", method = {RequestMethod.GET,
            RequestMethod.POST})
    @RequiresPermissions("userrecharge_list")
    public String pageQuery(
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute UserManualRechargeCondition userManualRechargeCondition, Model model) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        if (condition != null && condition.length() > 0) {
            userManualRechargeCondition = gson.fromJson(condition, UserManualRechargeCondition.class);
        }
        Searchable searchable = Searchable.newSearchable();
        searchable.addSearchFilters(userManualRechargeCondition.getAndFilters());
        searchable.setPage(pageCurrent, pageSize);
        searchable.addSort(new Sort(Direction.DESC, "operateTime"));
        model.addAttribute("condition", gson.toJson(userManualRechargeCondition));
        model.addAttribute("page", userManualRechargeService.findAll(searchable));
        return "userinfo/recharge/list";
    }


	@RequestMapping(value = "exportExcel/{condition}", method = {
			RequestMethod.GET, RequestMethod.POST })
	@RequiresPermissions("userrecharge_list")
	public String exportExcel(
			@ModelAttribute(value = "condition") String condition,
			@ModelAttribute UserManualRechargeCondition userManualRechargeCondition,
			Model model,HttpSession session,HttpServletRequest request,RedirectAttributes redirectAttributes ) {
		 String path = request.getSession().getServletContext().getRealPath("/static/download");

		 Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

		if (condition != null && condition.length() > 0) {
            userManualRechargeCondition = gson.fromJson(condition,UserManualRechargeCondition.class);
		}
        Map<String, Object> filter = userManualRechargeCondition.getSqlFilters();
		List<Object[]> list = userManualRechargeService.findExportData(filter);
		String fileName = userManualRechargeService.exportExcele(list,path);
		redirectAttributes.addFlashAttribute("fileName", fileName);
		return "redirect:/userrecharge/recharge/listRecharge";
	}



    public IUserManualRechargeService getUserManualRechargeService() {
        return userManualRechargeService;
    }

    public void setUserManualRechargeService(IUserManualRechargeService userManualRechargeService) {
        this.userManualRechargeService = userManualRechargeService;
    }
}
