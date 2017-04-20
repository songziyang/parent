package com.ydzb.web.activity.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.packet.service.IActivityGuoqingExchangeService;
import com.ydzb.web.activity.condition.ActivityGuoqingExchangeCondition;
import com.ydzb.web.activity.condition.ActivityZhongqiuExchangeCondition;
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

/**
 * 国庆活动兑换controller
 */
@Controller
@RequestMapping("activity/guoqingExchange")
public class ActivityGuoqingExchangeController extends BaseController {

    @Autowired
    private IActivityGuoqingExchangeService activityGuoqingExchangeService;

    /**
     * 列表
     * @param pageSize
     * @param pageCurrent
     * @param condition
     * @param model
     * @param activityGuoqingExchangeCondition
     * @return
     */
    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("guoqingExchange_list")
    public String pageQuery(@RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition, Model model,
            @ModelAttribute ActivityGuoqingExchangeCondition activityGuoqingExchangeCondition) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (StringUtils.isNotEmpty(condition)) {
            activityGuoqingExchangeCondition = gson.fromJson(condition, ActivityGuoqingExchangeCondition.class);
        }
        Searchable searchable = Searchable.newSearchable();
        searchable.addSearchFilters(activityGuoqingExchangeCondition.getAndFilters());
        searchable.setPage(pageCurrent, pageSize);
        searchable.addSort(new Sort(Sort.Direction.DESC, "created"));
        model.addAttribute("condition", gson.toJson(activityGuoqingExchangeCondition));
        model.addAttribute("page", activityGuoqingExchangeService.findAll(searchable));
        return "activity/guoqing/list";
    }


    /**
     * 确认发货 和 确认收货
     *
     * @param id
     * @param status  状态
     * @param session
     * @return
     */
    @RequestMapping(value = "auditSuccess/{id}/{status}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("guoqingExchange_list")
    public String auditSuccess(@PathVariable Long id, @PathVariable Integer status, HttpSession session) {
        try {
            activityGuoqingExchangeService.updateActivityZhongqiuExchange(id, status);
            session.setAttribute("message", "操作成功!");
        } catch (Exception e) {
            session.setAttribute("error", "操作失败！");
            e.printStackTrace();
        }
        return "redirect:/activity/guoqingExchange/list";
    }

    /**
     * 取消订单
     *
     * @param id
     * @param session
     * @return
     */
    @RequestMapping(value = "auditFailure/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("guoqingExchange_list")
    public String auditFailure(@PathVariable Long id, HttpSession session) {
        try {
            activityGuoqingExchangeService.auditFailure(id);
            session.setAttribute("message", "操作成功!");
        } catch (Exception e) {
            session.setAttribute("error", "操作失败！");
            e.printStackTrace();
        }
        return "redirect:/activity/guoqingExchange/list";
    }

    /**
     * 导出excel
     * @param condition
     * @param activityGuoqingExchangeCondition
     * @param request
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "exportExcel/{condition}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("guoqingExchange_list")
    public String exportExcel(@PathVariable(value = "condition") String condition,
            ActivityGuoqingExchangeCondition activityGuoqingExchangeCondition,
            HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String logoRealPathDir = request.getSession().getServletContext().getRealPath("/static/download");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (condition != null && condition.length() > 0) {
            activityGuoqingExchangeCondition = gson.fromJson(condition, ActivityGuoqingExchangeCondition.class);
        }
        Map<String, Object> filter = activityGuoqingExchangeCondition.getSqlFilters();
        List<Object[]> list = activityGuoqingExchangeService.findExportData(filter);
        String fileName = activityGuoqingExchangeService.exportExcel(list, logoRealPathDir);
        redirectAttributes.addFlashAttribute("fileName", fileName);
        return "redirect:/activity/guoqingExchange/list";
    }
}