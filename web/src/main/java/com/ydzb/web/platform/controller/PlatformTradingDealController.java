package com.ydzb.web.platform.controller;

import com.alibaba.druid.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.product.entity.PlatformTradingDeal;
import com.ydzb.product.service.IPlatformStatisticsService;
import com.ydzb.product.service.IPlatformTradingDealService;
import com.ydzb.web.platform.condition.PlatformStatisticsCondition;
import com.ydzb.web.product.condition.PlatformTradingCondition;
import com.ydzb.web.product.condition.PlatformTradingDealCondition;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


/**
 *
 */
@Controller
@RequestMapping("platform/tradingDeal")
public class PlatformTradingDealController extends BaseController {


    @Autowired
    private IPlatformTradingDealService platformService;

    @RequestMapping(value = "list/{tradingId}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("platformtrading_list")
    public String pageQuery(
            @PathVariable("tradingId") Long tradingId,
            @RequestParam(defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute("condition") String condition,
            @ModelAttribute("platformTradingDealCondition") PlatformTradingDealCondition platformTradingDealCondition,
            Model model) {

        platformTradingDealCondition.setTradingId(tradingId);
        Gson gson = new GsonBuilder().
                excludeFieldsWithoutExposeAnnotation().create();
        if (!StringUtils.isEmpty(condition)) {
            platformTradingDealCondition = gson.fromJson(condition, PlatformTradingDealCondition.class);
        }

        //创建查询
        Searchable searchable = Searchable.newSearchable();
        //添加查询条件
        searchable.addSearchFilters(platformTradingDealCondition.getAndFilters());
        //设置排序条件
        searchable.addSort(new Sort(Sort.Direction.DESC, "operationTime"));
        //设置分页参数
        searchable.setPage(pageCurrent, pageSize);

        model.addAttribute("condition", gson.toJson(platformTradingDealCondition));
        model.addAttribute("page", platformService.findAll(searchable));

        return "product/platformtrading/deallist";
    }

}
