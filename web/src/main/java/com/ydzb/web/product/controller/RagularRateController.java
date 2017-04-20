package com.ydzb.web.product.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.product.entity.RagularRate;
import com.ydzb.product.service.IRagularRateService;
import com.ydzb.web.product.condition.CurrentRateCondition;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 定存宝利率控制层
 */
@Controller
@RequestMapping("product/ragularRate")
public class RagularRateController extends BaseController {

    @Autowired
    private IRagularRateService rateService;

    /**
     * 分页列表
     * @param pageSize 每页显示条数
     * @param pageCurrent 当前页数(从0开始)
     * @param condition 查询条件json
     * @param rateCondition 查询条件
     * @param model
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("ragularrate_list")
    public String queryPage(@RequestParam(defaultValue = "0") int pageSize,
            @RequestParam(defaultValue = "0") int pageCurrent,
            @ModelAttribute("condition") String condition, Model model,
            @ModelAttribute("currentRateCondition") CurrentRateCondition rateCondition) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (StringUtils.isNotEmpty(condition)) {
            rateCondition = gson.fromJson(condition, CurrentRateCondition.class);
        }
        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        searchable.addSort(new Sort(Sort.Direction.DESC, "created"));
        // 添加查询条件
        searchable.addSearchFilters(rateCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        model.addAttribute("condition", gson.toJson(rateCondition));
        model.addAttribute("page", rateService.findAll(searchable));
        return "product/ragularrate/list";
    }

    /**
     * 创建
     * @return
     */
    @RequestMapping(value = "create", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("ragularrate_create")
    public String create() {
        return "product/ragularrate/edit";
    }

    /**
     * 保存
     * @param rate 定存宝利率
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @RequiresPermissions(value = {"ragularrate_create", "ragularrate_edit"}, logical = Logical.OR)
    public String save(RagularRate rate, HttpSession session) throws Exception {
        String result = rateService.saveOne(rate);
        String message = SAVE_SUCCESS;
        String target = "redirect:/product/ragularRate/list";
        if ("failure".equals(result)) {
            message = "保存失败! ";
            target = rate.getId() == null? "redirect:/product/ragularrate/create":
                    "redirect:/product/ragularrate/edt/" + rate.getId();
        }
        session.setAttribute("message", message);
        return target;
}

    /**
     * 编辑
     * @param id 定存宝利率id
     * @param model
     * @return
     */
    @RequestMapping(value = "edit/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("ragularrate_edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("ragularRate", rateService.findOne(id));
        return "product/ragularrate/edit";
    }
}