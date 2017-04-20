package com.ydzb.web.withdraw.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.web.withdraw.condition.PayManualRecordCondition;
import com.ydzb.withdraw.entity.PayManualRecord;
import com.ydzb.withdraw.service.IPayManualRecordService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * 后台手动打款控制层
 */
@Controller
@RequestMapping("userwithdraw/payManualRecord")
public class PayManualRecordController extends BaseController {

    @Autowired
    private IPayManualRecordService payManualRecordService;

    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("paymanualrecord_list")
    public String pageQuery(
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute PayManualRecordCondition payManualRecordCondition, Model model) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (condition != null && condition.length() > 0) {
            payManualRecordCondition = gson.fromJson(condition, PayManualRecordCondition.class);
        }
        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        searchable.addSearchFilters(payManualRecordCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        // 设置排序条件
        searchable.addSort(new Sort(Sort.Direction.DESC, "created"));
        model.addAttribute("condition", gson.toJson(payManualRecordCondition));
        model.addAttribute("page", payManualRecordService.findAll(searchable));
        return "userwithdraw/paymanualrecord/list";
    }

    /**
     * 创建
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "create", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("paymanualrecord_create")
    public String create(Model model) {
        return "userwithdraw/paymanualrecord/edit";
    }

    /**
     * 保存
     *
     * @param payManualRecord
     * @param session
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @RequiresPermissions(value = {"paymanualrecord_create", "paymanualrecord_edit"}, logical = Logical.OR)
    public String save(PayManualRecord payManualRecord, HttpSession session, Model model) throws Exception {
        String result = payManualRecordService.savePayManualRecord(payManualRecord);
        if (StringUtils.isEmpty(result)) {
            session.setAttribute("message", SAVE_SUCCESS);
            return "redirect:/userwithdraw/payManualRecord/list";
        } else {
            session.setAttribute("error", result);
            model.addAttribute("payManualRecord", payManualRecord);
            return "userwithdraw/paymanualrecord/edit";
        }
    }

    /**
     * 民生请求列表
     *
     * @param pageSize
     * @param pageCurrent
     * @param condition
     * @param payManualRecordCondition
     * @param model
     * @return
     */
    @RequestMapping(value = "requestList", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("paymanualrecord_list")
    public String requestPageQuery(
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute PayManualRecordCondition payManualRecordCondition, Model model) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (condition != null && condition.length() > 0) {
            payManualRecordCondition = gson.fromJson(condition, PayManualRecordCondition.class);
        }
        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        searchable.addSearchFilters(payManualRecordCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        // 设置排序条件
        searchable.addSort(new Sort(Sort.Direction.DESC, "created"));
        model.addAttribute("condition", gson.toJson(payManualRecordCondition));
        model.addAttribute("page", payManualRecordService.findAll(searchable));
        return "userwithdraw/paymanualrecord/request";
    }


    public IPayManualRecordService getPayManualRecordService() {
        return payManualRecordService;
    }

    public void setPayManualRecordService(IPayManualRecordService payManualRecordService) {
        this.payManualRecordService = payManualRecordService;
    }
}