package com.ydzb.web.user.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.admin.entity.Admin;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.user.entity.MinusAmount;
import com.ydzb.user.service.IMinusAmountService;
import com.ydzb.web.user.condition.MinusAmountCondition;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping(value = "userinfo/minusamount")
public class MinusAmountController extends BaseController {

    @Autowired
    private IMinusAmountService minusAmountService;

    //分页查询
    @RequestMapping(value = "listMinusamount", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("minusamount_list")
    public String pageQuery(
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute MinusAmountCondition minusAmountCondition, Model model) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (condition != null && condition.length() > 0) {
            minusAmountCondition = gson.fromJson(condition, MinusAmountCondition.class);
        }
        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        // 添加查询条件
        searchable.addSearchFilters(minusAmountCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        // 设置排序条件
        searchable.addSort(new Sort(Direction.DESC, "created"));
        model.addAttribute("condition", gson.toJson(minusAmountCondition));
        model.addAttribute("page", minusAmountService.findAll(searchable));
        return "userinfo/minusamount/list";
    }

    // 添加
    @RequestMapping(value = "createMinusamount", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("minusamount_create")
    public String createMinusamount(Model model) {
        return "userinfo/minusamount/edit";
    }

    // 保存
    @RequestMapping(value = "saveMinusamount", method = RequestMethod.POST)
    @RequiresPermissions(value = {"minusamount_create", "minusamount_edit"}, logical = Logical.OR)
    public String saveMinusamount(MinusAmount minusAmount, HttpSession session, Model model) throws Exception {
        minusAmountService.saveMinusamount(minusAmount);
        session.setAttribute("message", SAVE_SUCCESS);
        return "redirect:/userinfo/minusamount/listMinusamount";


    }

    // 删除
    @RequestMapping(value = "deleteMinusamount/{id}", method = {RequestMethod.GET,
            RequestMethod.POST})
    @RequiresPermissions("minusamount_del")
    public String deleteMinusamount(@PathVariable("id") Long id, HttpSession session)
            throws Exception {
        minusAmountService.delete(id);
        session.setAttribute("message", DELETE_SUCCESS);
        return "redirect:/userinfo/minusamount/listMinusamount";
    }

    // 批量删除
    @RequestMapping(value = "deleteMinusamounts", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("minusamount_del")
    public String deleteMinusamounts(Long[] ids, HttpSession session)
            throws Exception {
        minusAmountService.delete(ids);
        session.setAttribute("message", DELETE_SUCCESS);
        return "redirect:/userinfo/minusamount/listMinusamount";
    }

    // 编辑
    @RequestMapping(value = "editMinusamount/{id}", method = {RequestMethod.GET,
            RequestMethod.POST})
    @RequiresPermissions("minusamount_edit")
    public String editMinusamount(@PathVariable("id") Long id, Model model) {
        model.addAttribute("minusAmount", minusAmountService.findOne(id));
        return "userinfo/minusamount/edit";
    }


    public IMinusAmountService getMinusAmountService() {
        return minusAmountService;
    }

    public void setMinusAmountService(IMinusAmountService minusAmountService) {
        this.minusAmountService = minusAmountService;
    }
}
