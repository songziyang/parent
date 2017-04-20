package com.ydzb.web.product.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.product.entity.SelfRevenue;
import com.ydzb.product.service.ISelfRevenueService;
import com.ydzb.web.product.condition.SelfRevenueCondition;
import com.ydzb.web.product.condition.SelfTradeLogCondition;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 15-9-25.
 */
@Controller
@RequestMapping("product/selfRevenue")
public class SelfRevenueController extends BaseController {

    @Autowired
    private ISelfRevenueService selfRevenueService;

    /**
     * 审核列表
     * @param pageSize 每页显示几条
     * @param pageCurrent 当前页数-从0开始
     * @param condition
     * @param selfRevenueCondition
     * @param model
     * @return
     */
    @RequestMapping(value = "list", method = { RequestMethod.GET, RequestMethod.POST })
    @RequiresPermissions("selfrevenue_check")
    public String pageQuery(@RequestParam(defaultValue = PAGE_SIZE) int pageSize,
                            @RequestParam(defaultValue = PAGE_CURRENT) int pageCurrent,
                            @ModelAttribute("condition") String condition,
                            @ModelAttribute("selfRevenueCondition") SelfRevenueCondition selfRevenueCondition,
                            Model model) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        if (StringUtils.isNotEmpty(condition)) {
            selfRevenueCondition = gson.fromJson(condition, SelfRevenueCondition.class);
        }
        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        // 添加查询条件
        searchable.addSearchFilters(selfRevenueCondition.getAndFilters());
        searchable.addSort(new Sort(Sort.Direction.DESC, "id"));
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        model.addAttribute("condition", gson.toJson(selfRevenueCondition));
        model.addAttribute("page", selfRevenueService.findAll(searchable));
        return "product/selftradelog/check";
    }

    /**
     * 审核成功
     * @param id
     * @return
     */
    @RequestMapping(value = "check/{id}/success", method = { RequestMethod.GET, RequestMethod.POST })
    @RequiresPermissions("selfrevenue_check")
    public String checkSuccess(@PathVariable Long id) {
        selfRevenueService.approve(id, SelfRevenue.CHECKING_SUCCESS);
        return "redirect:/product/selfRevenue/list";
    }

    /**
     * 审核失败
     * @param id
     * @return
     */
    @RequestMapping(value = "check/{id}/fail", method = { RequestMethod.GET, RequestMethod.POST })
    @RequiresPermissions("selfrevenue_check")
    public String checkFail(@PathVariable Long id) {
        selfRevenueService.approve(id, SelfRevenue.CHECKING_FAILURE);
        return "redirect:/product/selfRevenue/list";
    }
}