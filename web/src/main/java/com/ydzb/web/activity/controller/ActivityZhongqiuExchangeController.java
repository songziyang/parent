package com.ydzb.web.activity.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.packet.service.IActivityZhongqiuExchangeService;
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


@Controller
@RequestMapping("activity/zhongqiuExchange")
public class ActivityZhongqiuExchangeController extends BaseController {

    @Autowired
    private IActivityZhongqiuExchangeService activityZhongqiuExchangeService;


    /**
     * @param pageSize
     * @param pageCurrent
     * @param condition
     * @param model
     * @param activityZhongqiuExchangeCondition
     * @return
     */
    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("zhongqiuExchange_list")
    public String pageQuery(@RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
                            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
                            @ModelAttribute(value = "condition") String condition, Model model,
                            @ModelAttribute ActivityZhongqiuExchangeCondition activityZhongqiuExchangeCondition) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (StringUtils.isNotEmpty(condition)) {
            activityZhongqiuExchangeCondition = gson.fromJson(condition, ActivityZhongqiuExchangeCondition.class);
        }
        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        // 添加查询条件
        searchable.addSearchFilters(activityZhongqiuExchangeCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        // 设置排序条件
        searchable.addSort(new Sort(Sort.Direction.DESC, "created"));
        model.addAttribute("condition", gson.toJson(activityZhongqiuExchangeCondition));
        model.addAttribute("page", activityZhongqiuExchangeService.findAll(searchable));
        return "activity/zhongqiu/list";
    }


    /**
     * 确认发货 和 确认收货
     *
     * @param id      ID
     * @param status  状态
     * @param session
     * @return
     */
    @RequestMapping(value = "auditSuccess/{id}/{status}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("zhongqiuExchange_list")
    public String auditSuccess(@PathVariable Long id, @PathVariable Integer status, HttpSession session) {
        try {
            activityZhongqiuExchangeService.updateActivityZhongqiuExchange(id, status);
            session.setAttribute("message", "操作成功!");
        } catch (Exception e) {
            session.setAttribute("error", "操作失败！");
            e.printStackTrace();
        }
        return "redirect:/activity/zhongqiuExchange/list";
    }


    /**
     * 取消订单
     *
     * @param id      ID
     * @param session
     * @return
     */
    @RequestMapping(value = "auditFailure/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("zhongqiuExchange_list")
    public String auditFailure(@PathVariable Long id, HttpSession session) {
        try {
            activityZhongqiuExchangeService.auditFailure(id);
            session.setAttribute("message", "操作成功!");
        } catch (Exception e) {
            session.setAttribute("error", "操作失败！");
            e.printStackTrace();
        }
        return "redirect:/activity/zhongqiuExchange/list";
    }


    @RequestMapping(value = "exportExcel/{condition}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("zhongqiuExchange_list")
    public String exportExcel(@PathVariable(value = "condition") String condition,
                              ActivityZhongqiuExchangeCondition activityZhongqiuExchangeCondition,
                              HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String logoRealPathDir = request.getSession().getServletContext().getRealPath("/static/download");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (condition != null && condition.length() > 0) {
            activityZhongqiuExchangeCondition = gson.fromJson(condition, ActivityZhongqiuExchangeCondition.class);
        }
        Map<String, Object> filter = activityZhongqiuExchangeCondition.getSqlFilters();
        List<Object[]> list = activityZhongqiuExchangeService.findExportData(filter);
        String fileName = activityZhongqiuExchangeService.exportExcel(list, logoRealPathDir);
        redirectAttributes.addFlashAttribute("fileName", fileName);
        return "redirect:/activity/zhongqiuExchange/list";
    }


    public IActivityZhongqiuExchangeService getActivityZhongqiuExchangeService() {
        return activityZhongqiuExchangeService;
    }

    public void setActivityZhongqiuExchangeService(IActivityZhongqiuExchangeService activityZhongqiuExchangeService) {
        this.activityZhongqiuExchangeService = activityZhongqiuExchangeService;
    }
}