package com.ydzb.web.platform.controller;

import com.alibaba.druid.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.product.service.IPlatformFundRecordService;
import com.ydzb.product.service.IPlatformInvestRecordService;
import com.ydzb.web.platform.condition.PlatformFundRecordCondition;
import com.ydzb.web.platform.condition.PlatformInvestRecordCondition;
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
 * 平台债权记录controller
 */
@Controller
@RequestMapping("platform/investrecord")
public class PlatformInvestRecordController extends BaseController {

    @Autowired
    private IPlatformInvestRecordService platformInvestRecordService;

    /**
     * 列表
     * @param model
     * @return
     */
    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("platformirecord_list")
    public String pageQuery(@RequestParam(defaultValue = PAGE_SIZE) int pageSize,
        @RequestParam(defaultValue = PAGE_CURRENT) int pageCurrent,
        @ModelAttribute("condition") String condition, Model model,
        @ModelAttribute("platformInvestRecordCondition") PlatformInvestRecordCondition platformInvestRecordCondition) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (!StringUtils.isEmpty(condition)) {
            platformInvestRecordCondition = gson.fromJson(condition, PlatformInvestRecordCondition.class);
        }
        //创建查询
        Searchable searchable = Searchable.newSearchable();
        //添加查询条件
        searchable.addSearchFilters(platformInvestRecordCondition.getAndFilters());
        //设置排序条件
        searchable.addSort(new Sort(Sort.Direction.DESC, "optime"));
        //设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        model.addAttribute("condition", gson.toJson(platformInvestRecordCondition));
        model.addAttribute("page", platformInvestRecordService.findAll(searchable));
        return "product/platforminvestrecord/list";
    }
}