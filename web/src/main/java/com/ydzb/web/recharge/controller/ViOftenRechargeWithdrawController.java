package com.ydzb.web.recharge.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.user.service.IViOftenRechargeWithdrawService;
import com.ydzb.user.service.IViRechargePennyService;
import com.ydzb.web.recharge.condition.ViOftenRechargeWithdrawCondition;
import com.ydzb.web.recharge.condition.ViRechargePennyCondition;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
 * 频繁充值提现controller
 */
@Controller
@RequestMapping("recharge/often")
public class ViOftenRechargeWithdrawController extends BaseController {

    @Autowired
    private IViOftenRechargeWithdrawService viOftenRechargeWithdrawService;

    /**
     * 根据条件分页查询
     *
     * @param pageSize
     * @param pageCurrent
     * @param condition
     * @param model
     * @return
     */
    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("ofrecharge_list")
    public String pageQuery(
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute ViOftenRechargeWithdrawCondition viOftenRechargeWithdrawCondition,
            Model model) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (condition != null && condition.length() > 0) {
            viOftenRechargeWithdrawCondition = gson.fromJson(condition, ViOftenRechargeWithdrawCondition.class);
        }

        Searchable searchable = Searchable.newSearchable();
        searchable.addSearchFilters(viOftenRechargeWithdrawCondition.getAndFilters());
        searchable.setPage(pageCurrent, pageSize);
        model.addAttribute("condition", gson.toJson(viOftenRechargeWithdrawCondition));
        model.addAttribute("page", viOftenRechargeWithdrawService.findAll(searchable));
        return "recharge/often/list";
    }

    @RequestMapping(value = "exportExcel/{condition}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("ofrecharge_list")
    public String exportExcel(
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute ViOftenRechargeWithdrawCondition viOftenRechargeWithdrawCondition, HttpServletRequest request,
            RedirectAttributes redirectAttributes) {
        String path = request.getSession().getServletContext().getRealPath("/static/download");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        if (StringUtils.isNotEmpty(condition)) {
            viOftenRechargeWithdrawCondition = gson.fromJson(condition, ViOftenRechargeWithdrawCondition.class);
        }
        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        searchable.addSearchFilters(viOftenRechargeWithdrawCondition.getAndFilters());
        Map<String, Object> filters = viOftenRechargeWithdrawCondition.getSqlFilters();
        List<Object[]> list = viOftenRechargeWithdrawService.findExportData(filters);
        String fileName = viOftenRechargeWithdrawService.exportExcel(list, path);
        redirectAttributes.addFlashAttribute("fileName", fileName);
        return "redirect:/recharge/often/list";
    }
}
