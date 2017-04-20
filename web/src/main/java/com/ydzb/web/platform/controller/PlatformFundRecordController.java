package com.ydzb.web.platform.controller;

import com.alibaba.druid.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.product.service.IPlatformFundRecordService;
import com.ydzb.web.platform.condition.PlatformFundRecordCondition;
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
 * 平台资金记录controller
 */
@Controller
@RequestMapping("platform/fundrecord")
public class PlatformFundRecordController extends BaseController {

    @Autowired
    private IPlatformFundRecordService platformFundRecordService;

    /**
     * 列表
     * @param model
     * @return
     */
    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("platformfrecord_list")
    public String pageQuery(@RequestParam(defaultValue = PAGE_SIZE) int pageSize,
        @RequestParam(defaultValue = PAGE_CURRENT) int pageCurrent,
        @ModelAttribute("condition") String condition, Model model,
        @ModelAttribute("platformFundRecordCondition") PlatformFundRecordCondition platformFundRecordCondition) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (!StringUtils.isEmpty(condition)) {
            platformFundRecordCondition = gson.fromJson(condition, PlatformFundRecordCondition.class);
        }
        //创建查询
        Searchable searchable = Searchable.newSearchable();
        //添加查询条件
        searchable.addSearchFilters(platformFundRecordCondition.getAndFilters());
        //设置排序条件
        searchable.addSort(new Sort(Sort.Direction.DESC, "optime"));
        //设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        model.addAttribute("condition", gson.toJson(platformFundRecordCondition));
        model.addAttribute("page", platformFundRecordService.findAll(searchable));
        return "product/platformfundrecord/list";
    }
}