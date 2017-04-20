package com.ydzb.web.product.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.product.entity.CurrentRate;
import com.ydzb.product.service.ICurrentQueueService;
import com.ydzb.product.service.ICurrentRateService;
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
 * 活期宝利率控制层
 * @author sy
 */
@Controller
@RequestMapping("product/currentRate")
public class CurrentRateController extends BaseController {

    @Autowired
    private ICurrentRateService rateService;
    @Autowired
    private ICurrentQueueService currentQueueService;

    /**
     * 列表
     * @param pageSize
     * @param pageCurrent
     * @param condition
     * @param rateCondition
     * @param model
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("currentrate_list")
    public String queryPage(@RequestParam(defaultValue = "0") int pageSize,
            @RequestParam(defaultValue = "0") int pageCurrent,
            @ModelAttribute("condition") String condition,
            @ModelAttribute("currentRateCondition") CurrentRateCondition rateCondition, Model model) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
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
        model.addAttribute("prepayCopies", currentQueueService.querySumCopies());
        model.addAttribute("todayPrepayCopies", currentQueueService.queryTodaySumCopies());
        return "product/currentrate/list";
    }

    /**
     * 创建
     * @param model
     * @return
     */
    @RequestMapping(value = "create", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("currentrate_create")
    public String create(Model model) {
        return "product/currentrate/edit";
    }

    /**
     * 保存
     * @param currentRate
     * @param session
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @RequiresPermissions(value = { "currentrate_create", "currentrate_edit" }, logical = Logical.OR)
    public String save(CurrentRate currentRate, String dCurrentDate, HttpSession session, Model model) throws Exception {
        String result = rateService.saveOne(currentRate, dCurrentDate);
        if (StringUtils.isEmpty(result)) {
            session.setAttribute("message", SAVE_SUCCESS);
            return "redirect:/product/currentRate/list";
        } else {
            session.setAttribute("error", result);
            model.addAttribute("currentRate", currentRate);
            return "product/currentrate/edit";
        }
    }

    /**
     * 编辑
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "edit/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("currentrate_edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("currentRate", rateService.findOne(id));
        return "product/currentrate/edit";
    }

    /**
     * 停用
     * @param id
     * @param session
     * @return
     */
    @RequestMapping(value = "disable/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("currentrate_del")
    public String disable(@PathVariable Long id, HttpSession session) {
        rateService.disable(id);
        session.setAttribute("message", "停用成功");
        return "redirect:/product/currentRate/list";
    }
}