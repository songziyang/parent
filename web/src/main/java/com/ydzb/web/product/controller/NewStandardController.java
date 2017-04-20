package com.ydzb.web.product.controller;

import com.alibaba.druid.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.product.service.INewStandardService;
import com.ydzb.web.product.condition.NewStandardCondition;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("product/newstandard")
public class NewStandardController extends BaseController {


    @Autowired
    private INewStandardService newStandardService;

    @RequestMapping(value = "listNewStandard", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("newstandard_list")
    public String pageQuery(
            @RequestParam(defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(defaultValue = PAGE_CURRENT) int pageCurrent,
            final String condition, @ModelAttribute NewStandardCondition newStandardCondition, Model model) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (!StringUtils.isEmpty(condition)) {
            newStandardCondition = gson.fromJson(condition, NewStandardCondition.class);
        }
        //创建查询
        Searchable searchable = Searchable.newSearchable();
        //添加查询条件
        searchable.addSearchFilters(newStandardCondition.getAndFilters());
        //设置排序条件
        searchable.addSort(new Sort(Direction.DESC, "created"));
        //设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        model.addAttribute("condition", gson.toJson(newStandardCondition));
        model.addAttribute("page", newStandardService.findAll(searchable));
        return "product/newstandard/list";
    }

    public INewStandardService getNewStandardService() {
        return newStandardService;
    }

    public void setNewStandardService(INewStandardService newStandardService) {
        this.newStandardService = newStandardService;
    }
}
