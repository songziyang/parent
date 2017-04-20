package com.ydzb.web.redpacket.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.packet.entity.ActivityYuanXiao;
import com.ydzb.packet.service.IActivityYuanXiaoService;
import com.ydzb.web.redpacket.condition.ActivityYuanXiaoCondition;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping("redpacket/activityyuanxiao")
public class ActivityYuanXiaoController extends BaseController {

    @Autowired
    private IActivityYuanXiaoService activityYuanXiaoService;

    @RequestMapping(value = "listActivityyuanxiao", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("activityyuanxiao_list")
    public String pageQuery(
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute ActivityYuanXiaoCondition activityYuanXiaoCondition, Model model) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        if (condition != null && condition.length() > 0) {
            activityYuanXiaoCondition = gson.fromJson(condition, ActivityYuanXiaoCondition.class);
        }

        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        // 添加查询条件
        searchable.addSearchFilters(activityYuanXiaoCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        // 设置排序条件
        searchable.addSort(new Sort(Sort.Direction.DESC, "scores"));
        model.addAttribute("condition", gson.toJson(activityYuanXiaoCondition));
        model.addAttribute("page", activityYuanXiaoService.findAll(searchable));
        return "redpacket/activityyuanxiao/list";
    }

    /**
     * 导出excel
     * @param condition
     * @param activityYuanXiaoCondition
     * @return
     */
    @RequestMapping(value = "exportExcel/{condition}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("activityyuanxiao_list")
    public String export(@PathVariable  String condition,
            @ModelAttribute ActivityYuanXiaoCondition activityYuanXiaoCondition,
            HttpSession session, RedirectAttributes redirectAttributes) {
        String logoRealPathDir = session.getServletContext().getRealPath("/static/download");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (condition != null && condition.length() > 0) {
            activityYuanXiaoCondition = gson.fromJson(condition, ActivityYuanXiaoCondition.class);
        }
        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        // 添加查询条件
        searchable.addSearchFilters(activityYuanXiaoCondition.getAndFilters());
        // 设置排序条件
        searchable.addSort(new Sort(Sort.Direction.DESC, "scores"));
        final List<ActivityYuanXiao> data = activityYuanXiaoService.findAllWithSort(searchable);
        final String fileName = activityYuanXiaoService.exportExcel(data, logoRealPathDir);
        redirectAttributes.addFlashAttribute("fileName", fileName);
        //session.setAttribute("message", "导出成功");
        return "redirect:/redpacket/activityyuanxiao/listActivityyuanxiao";
    }

    public IActivityYuanXiaoService getActivityYuanXiaoService() {
        return activityYuanXiaoService;
    }

    public void setActivityYuanXiaoService(IActivityYuanXiaoService activityYuanXiaoService) {
        this.activityYuanXiaoService = activityYuanXiaoService;
    }
}