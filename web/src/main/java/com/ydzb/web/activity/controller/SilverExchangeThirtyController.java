package com.ydzb.web.activity.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.packet.entity.SilverExchangeThirty;
import com.ydzb.packet.service.ISilverExchangeService;
import com.ydzb.packet.service.ISilverExchangeThirtyService;
import com.ydzb.web.activity.condition.SilverExchangeThirtyCondition;
import com.ydzb.web.redpacket.condition.SilverExchangeCondition;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("activity/silverExchangeThirty")
public class SilverExchangeThirtyController extends BaseController {

    @Autowired
    private ISilverExchangeThirtyService silverExchangeThirtyService;


    /**
     * @param pageSize
     * @param pageCurrent
     * @param condition
     * @param model
     * @return
     */
    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("silverexchange30_list")
    public String pageQuery(@RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition, Model model,
            @ModelAttribute SilverExchangeThirtyCondition silverExchangeThirtyCondition) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (StringUtils.isNotEmpty(condition)) {
            silverExchangeThirtyCondition = gson.fromJson(condition, SilverExchangeThirtyCondition.class);
        }
        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        // 添加查询条件
        searchable.addSearchFilters(silverExchangeThirtyCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        // 设置排序条件
        searchable.addSort(new Sort(Sort.Direction.DESC, "created"));
        model.addAttribute("condition", gson.toJson(silverExchangeThirtyCondition));
        model.addAttribute("page", silverExchangeThirtyService.findAll(searchable));
        model.addAttribute("status", silverExchangeThirtyCondition.getStatus());
        model.addAttribute("category", silverExchangeThirtyCondition.getCategory());
        return "activity/silverexchangethirty/list";
    }


    @RequestMapping(value = "auditSuccess/{id}/{status}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("silverexchange30_list")
    public String auditSuccess(@PathVariable Long id, @PathVariable Integer status, HttpSession session) {
        try {
            silverExchangeThirtyService.updateSilverExchange(id, status);
            session.setAttribute("message", "操作成功!");
        } catch (Exception e) {
            session.setAttribute("error", "操作失败！");
            e.printStackTrace();
        }
        return "redirect:/activity/silverExchangeThirty/list";
    }


    @RequestMapping(value = "auditFailure/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("silverexchange30_list")
    public String auditFailure(@PathVariable Long id, HttpSession session) {
        try {
            silverExchangeThirtyService.auditFailure(id);
            session.setAttribute("message", "操作成功!");
        } catch (Exception e) {
            session.setAttribute("error", "操作失败！");
            e.printStackTrace();
        }
        return "redirect:/activity/silverExchangeThirty/list";
    }

    @RequestMapping(value = "exportExcel/{condition}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("silverexchange30_list")
    public String exportExcel(
            @PathVariable(value = "condition") String condition,
            SilverExchangeThirtyCondition silverExchangeThirtyConditionCondition, Model model,
            HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String logoRealPathDir = request.getSession().getServletContext().getRealPath("/static/download");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (condition != null && condition.length() > 0) {
            silverExchangeThirtyConditionCondition = gson.fromJson(condition, SilverExchangeThirtyCondition.class);
        }
        Map<String, Object> filter = silverExchangeThirtyConditionCondition.getSqlFilters();
        List<Object[]> list = silverExchangeThirtyService.findExportData(filter);
        String fileName = silverExchangeThirtyService.exportExcel(list, logoRealPathDir);
        redirectAttributes.addFlashAttribute("fileName", fileName);
        //session.setAttribute("message", "导出成功");
        return "redirect:/activity/silverExchangeThirty/list";
    }
}