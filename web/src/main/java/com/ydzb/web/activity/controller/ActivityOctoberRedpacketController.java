package com.ydzb.web.activity.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.packet.service.IActivityOctoberRedpacketService;
import com.ydzb.web.activity.condition.ActivityOctoberRedpacketCondition;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 金秋十月活动controller
 */
@Controller
@RequestMapping("activity/octoberRedpacket")
public class ActivityOctoberRedpacketController extends BaseController {

    @Autowired
    private IActivityOctoberRedpacketService activityOctoberRedpacketService;


    /**
     * 分页列表
     * @param pageSize
     * @param pageCurrent
     * @param condition
     * @param model
     * @param activityOctoberRedpacketCondition
     * @return
     */
    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("octoberredpacket_list")
    public String pageQuery(@RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition, Model model,
            @ModelAttribute ActivityOctoberRedpacketCondition activityOctoberRedpacketCondition) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (StringUtils.isNotEmpty(condition)) {
            activityOctoberRedpacketCondition = gson.fromJson(condition, ActivityOctoberRedpacketCondition.class);
        }
        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        // 添加查询条件
        searchable.addSearchFilters(activityOctoberRedpacketCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        // 设置排序条件
        searchable.addSort(new Sort(Sort.Direction.DESC, "created"));
        model.addAttribute("condition", gson.toJson(activityOctoberRedpacketCondition));
        model.addAttribute("page", activityOctoberRedpacketService.findAll(searchable));
        return "activity/octoberredpacket/list";
    }

    /**
     * 导出excel
     * @param condition
     * @param activityOctoberRedpacketCondition
     * @param request
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "exportExcel/{condition}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("octoberredpacket_list")
    public String exportExcel(@PathVariable String condition,
        ActivityOctoberRedpacketCondition activityOctoberRedpacketCondition,
        HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String logoRealPathDir = request.getSession().getServletContext().getRealPath("/static/download");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (condition != null && condition.length() > 0) {
            activityOctoberRedpacketCondition = gson.fromJson(condition, ActivityOctoberRedpacketCondition.class);
        }
        Map<String, Object> filter = activityOctoberRedpacketCondition.getSqlFilters();
        List<Object[]> list = activityOctoberRedpacketService.findExportData(filter);
        String fileName = activityOctoberRedpacketService.exportExcel(list, logoRealPathDir);
        redirectAttributes.addFlashAttribute("fileName", fileName);
        return "redirect:/activity/octoberRedpacket/list";
    }
}