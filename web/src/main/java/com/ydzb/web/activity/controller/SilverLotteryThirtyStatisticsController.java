package com.ydzb.web.activity.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.packet.service.IThirtyLotteryStatisticsService;
import com.ydzb.web.activity.condition.SilverLotteryThirtyStatisticsCondition;
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


@Controller
@RequestMapping("platform/silverLotteryThirtyStatistics")
public class SilverLotteryThirtyStatisticsController extends BaseController {

    @Autowired
    private IThirtyLotteryStatisticsService thirtyLotteryStatisticsService;

    /**
     * @param pageSize
     * @param pageCurrent
     * @param condition
     * @param model
     * @return
     */
    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("silverlotterysta30_list")
    public String pageQuery(@RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition, Model model,
            @ModelAttribute SilverLotteryThirtyStatisticsCondition silverLotteryThirtyStatisticsCondition) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (StringUtils.isNotEmpty(condition)) {
            silverLotteryThirtyStatisticsCondition = gson.fromJson(condition, SilverLotteryThirtyStatisticsCondition.class);
        }
        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        // 添加查询条件
        searchable.addSearchFilters(silverLotteryThirtyStatisticsCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        // 设置排序条件
        searchable.addSort(new Sort(Sort.Direction.DESC, "id"));
        model.addAttribute("condition", gson.toJson(silverLotteryThirtyStatisticsCondition));
        model.addAttribute("page", thirtyLotteryStatisticsService.findAll(searchable));
        return "product/platformthirtylotterysta/list";
    }



    @RequestMapping(value = "exportExcel/{condition}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("silverlotterysta30_list")
    public String exportExcel(@PathVariable(value = "condition") String condition,
            SilverLotteryThirtyStatisticsCondition silverLotteryThirtyStatisticsCondition,
            HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String logoRealPathDir = request.getSession().getServletContext().getRealPath("/static/download");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (condition != null && condition.length() > 0) {
            silverLotteryThirtyStatisticsCondition = gson.fromJson(condition, SilverLotteryThirtyStatisticsCondition.class);
        }
        Map<String, Object> filter = silverLotteryThirtyStatisticsCondition.getSqlFilters();
        List<Object[]> list = thirtyLotteryStatisticsService.findExportData(filter);
        String fileName = thirtyLotteryStatisticsService.exportExcel(list, logoRealPathDir);
        redirectAttributes.addFlashAttribute("fileName", fileName);
        //session.setAttribute("message", "导出成功");
        return "redirect:/platform/silverLotteryThirtyStatistics/list";
    }
}