package com.ydzb.web.recharge.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.user.service.IViRechargePennyService;
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
 * 小额重置controller
 */
@Controller
@RequestMapping("recharge/penny")
public class ViRechargePennyController extends BaseController {

    @Autowired
    private IViRechargePennyService viRechargePennyService;

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
    @RequiresPermissions("rechargepenny_list")
    public String pageQuery(
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute ViRechargePennyCondition viRechargePennyCondition,
            Model model) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (condition != null && condition.length() > 0) {
            viRechargePennyCondition = gson.fromJson(condition, ViRechargePennyCondition.class);
        }

        Searchable searchable = Searchable.newSearchable();
        searchable.addSearchFilters(viRechargePennyCondition.getAndFilters());
        searchable.setPage(pageCurrent, pageSize);
        searchable.addSort(new Sort(Sort.Direction.DESC, "rechargeTime"));
        model.addAttribute("condition", gson.toJson(viRechargePennyCondition));
        model.addAttribute("page", viRechargePennyService.findAll(searchable));
        model.addAttribute("userCount", viRechargePennyService.queryUsersCount(viRechargePennyCondition.getStartTime(), viRechargePennyCondition.getEndTime()));

        return "recharge/penny/list";
    }

    @RequestMapping(value = "exportExcel/{condition}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("rechargepenny_list")
    public String exportExcel(
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute ViRechargePennyCondition viRechargePennyCondition, HttpServletRequest request,
            RedirectAttributes redirectAttributes) {
        String path = request.getSession().getServletContext().getRealPath("/static/download");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        if (StringUtils.isNotEmpty(condition)) {
            viRechargePennyCondition = gson.fromJson(condition, ViRechargePennyCondition.class);
        }
        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        searchable.addSearchFilters(viRechargePennyCondition.getAndFilters());
        searchable.addSort(new Sort(Sort.Direction.DESC, "rechargeTime"));
        Map<String, Object> filters = viRechargePennyCondition.getSqlFilters();
        List<Object[]> list = viRechargePennyService.findExportData(filters);
        String fileName = viRechargePennyService.exportExcel(list, path);
        redirectAttributes.addFlashAttribute("fileName", fileName);
        return "redirect:/recharge/penny/list";
    }
}
