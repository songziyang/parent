package com.ydzb.web.product.controller;

import com.alibaba.druid.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.product.service.IStableDealService;
import com.ydzb.product.service.IStableService;
import com.ydzb.web.product.condition.StableDealCondition;
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


/**
 * 稳进宝交易记录控制层
 */
@Controller
@RequestMapping("product/stabledeal")
public class StableDealController extends BaseController {

    @Autowired
    private IStableDealService iStableDealService;
    @Autowired
    private IStableService iStableService;

    /**
     * 查询某一稳进宝下的交易记录
     *
     * @param pageSize
     * @param pageCurrent
     * @param condition
     * @param stableDealCondition
     * @param model
     * @return
     */
    @RequestMapping(value = "listStableDeal", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("stable_list")
    public String pageQuery(
            @RequestParam(defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(defaultValue = PAGE_CURRENT) int pageCurrent,
            final String condition, @ModelAttribute StableDealCondition stableDealCondition, Model model) {

        Gson gson = new GsonBuilder().
                excludeFieldsWithoutExposeAnnotation().create();
        if (!StringUtils.isEmpty(condition)) {
            stableDealCondition = gson.fromJson(condition, StableDealCondition.class);
        }

        //创建查询
        Searchable searchable = Searchable.newSearchable();
        //添加查询条件
        searchable.addSearchFilters(stableDealCondition.getAndFilters());
        //设置排序条件
        searchable.addSort(new Sort(Direction.DESC, "btime"));
        //设置分页参数
        searchable.setPage(pageCurrent, pageSize);

        model.addAttribute("condition", gson.toJson(stableDealCondition));
        model.addAttribute("page", iStableDealService.findAll(searchable));
        return "product/stabledeal/list";
    }

    public IStableDealService getiStableDealService() {
        return iStableDealService;
    }

    public void setiStableDealService(IStableDealService iStableDealService) {
        this.iStableDealService = iStableDealService;
    }

    public IStableService getiStableService() {
        return iStableService;
    }

    public void setiStableService(IStableService iStableService) {
        this.iStableService = iStableService;
    }
}
