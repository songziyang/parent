package com.ydzb.web.platform.controller;

import com.alibaba.druid.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.product.service.IPlatformExceptionService;
import com.ydzb.web.platform.condition.PlatformExceptionCondition;
import com.ydzb.web.platform.condition.PlatformReconciliationCondition;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 平台统计controller
 */
@Controller
@RequestMapping("platform/pexception")
public class PlatformExceptionController extends BaseController {


    @Autowired
    private IPlatformExceptionService platformExceptionService;


    @RequestMapping(value = "listPException", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("pexception_list")
    public String pageQuery(
            @RequestParam(defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(defaultValue = PAGE_CURRENT) int pageCurrent,
            String condition, PlatformExceptionCondition platformCondition, Model model) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (!StringUtils.isEmpty(condition)) {
            platformCondition = gson.fromJson(condition, PlatformExceptionCondition.class);
        }
        //创建查询
        Searchable searchable = Searchable.newSearchable();
        //添加查询条件
        searchable.addSearchFilters(platformCondition.getAndFilters());
        //设置排序条件
        searchable.addSort(new Sort(Direction.DESC, "created"));
        //设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        model.addAttribute("condition", gson.toJson(platformCondition));
        model.addAttribute("page", platformExceptionService.findAll(searchable));
        return "product/pexception/list";
    }


    public IPlatformExceptionService getPlatformExceptionService() {
        return platformExceptionService;
    }

    public void setPlatformExceptionService(IPlatformExceptionService platformExceptionService) {
        this.platformExceptionService = platformExceptionService;
    }
}