package com.ydzb.web.product.controller;

import com.alibaba.druid.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.product.entity.Stable;
import com.ydzb.product.service.IStableService;
import com.ydzb.web.product.condition.StableCondition;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


/**
 * 稳进宝控制层
 */
@Controller
@RequestMapping(value = "product/stable")
public class StableController extends BaseController {

    @Autowired
    private IStableService stableService;

    /**
     * 分页查询
     *
     * @param pageSize        每页显示几条
     * @param pageCurrent     当前页数-从0开始
     * @param condition
     * @param stableCondition
     * @param model
     * @return
     */
    @RequestMapping(value = "listStable", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("stable_list")
    public String pageQuery(@RequestParam(defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(defaultValue = PAGE_CURRENT) int pageCurrent, final Model model,
            final String condition, @ModelAttribute StableCondition stableCondition) {

        Gson gson = new GsonBuilder().
                excludeFieldsWithoutExposeAnnotation().create();
        if (!StringUtils.isEmpty(condition)) {
            stableCondition = gson.fromJson(condition, StableCondition.class);
        }

        //创建查询
        Searchable searchable = Searchable.newSearchable();
        //添加查询条件
        searchable.addSearchFilters(stableCondition.getAndFilters());
        //设置排序条件
        searchable.addSort(new Sort(Direction.DESC, "created"));
        //设置分页参数
        searchable.setPage(pageCurrent, pageSize);

        model.addAttribute("condition", gson.toJson(stableCondition));
        model.addAttribute("page", stableService.findAll(searchable));

        return "product/stable/list";
    }

    /**
     * 创建稳健宝
     *
     * @return
     */

    @RequestMapping(value = "createStable", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("stable_create")
    public String create() {
        return "product/stable/edit";
    }

    /**
     * 编辑稳进宝
     *
     * @param id    稳定宝id
     * @param model
     * @return
     */
    @RequestMapping(value = "editStable/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("stable_edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("stable", stableService.findOne(id));
        return "product/stable/edit";
    }

    /**
     * 保存稳进宝
     *
     * @param session
     * @param model
     * @param stable     稳进宝实体
     * @param sStartDate 申购开始日期
     * @param sEndDate   申购结束日期
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "saveStable", method = RequestMethod.POST)
    @RequiresPermissions(value = {"stable_create", "stable_edit"}, logical = Logical.OR)
    public String save(HttpSession session, Model model, Stable stable,
                       String sStartDate, String sEndDate) throws Exception {
        String result = stableService.saveStable(stable, sStartDate, sEndDate);
        if (StringUtils.isEmpty(result)) {
            session.setAttribute("message", SAVE_SUCCESS);
            return "redirect:/product/stable/listStable";
        }
        session.setAttribute("error", result);
        model.addAttribute("stable", stable);
        return "product/stable/edit";
    }

    /**
     * 删除单个稳进宝
     *
     * @param id      稳进宝id
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "deleteStable/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("stable_del")
    public String delete(@PathVariable Long id, HttpSession session) throws Exception {
        stableService.delete(id);
        session.setAttribute("message", DELETE_SUCCESS);
        return "redirect:/product/stable/listStable";
    }

    /**
     * 删除单个/多个稳进宝
     *
     * @param ids     稳进宝id
     * @param session
     * @return
     */
    @RequestMapping(value = "deleteStables", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("stable_del")
    public String delete(Long[] ids, HttpSession session) {
        stableService.delete(ids);
        session.setAttribute("message", DELETE_SUCCESS);
        return "redirect:/product/stable/listStable";
    }

    public IStableService getStableService() {
        return stableService;
    }

    public void setStableService(IStableService stableService) {
        this.stableService = stableService;
    }
}