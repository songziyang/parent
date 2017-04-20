package com.ydzb.web.platform.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.product.service.IDailySignStatisticsService;
import com.ydzb.web.platform.condition.DailySignStatisticsCondition;
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

/**
 * 每日签到统计controller
 */
@Controller
@RequestMapping("platform/dailysign")
public class DailySignStatisticsController extends BaseController {

    @Autowired
    private IDailySignStatisticsService dailySignStatisticsService;

    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("platformdailysign_list")
    public String pageQuery(@RequestParam(defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute("condition") String condition,
            @ModelAttribute("platformPayCondition") DailySignStatisticsCondition dilySignStatisticsCondition, Model model) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (StringUtils.isNotEmpty(condition)) {
            dilySignStatisticsCondition = gson.fromJson(condition, DailySignStatisticsCondition.class);
        }
        //创建查询
        Searchable searchable = Searchable.newSearchable();
        //添加查询条件
        searchable.addSearchFilters(dilySignStatisticsCondition.getAndFilters());
        //设置排序条件
        searchable.addSort(new Sort(Sort.Direction.DESC, "statisticsDate"));
        //设置分页参数
        searchable.setPage(pageCurrent, pageSize);

        model.addAttribute("condition", gson.toJson(dilySignStatisticsCondition));
        model.addAttribute("page", dailySignStatisticsService.findAll(searchable));
        return "product/platformdailysign/list";
    }
}