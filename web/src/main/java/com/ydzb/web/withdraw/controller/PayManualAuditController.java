package com.ydzb.web.withdraw.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.web.withdraw.condition.PayManualAuditCondition;
import com.ydzb.withdraw.service.IPayManualRecordService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 后台手动打款控制层
 */
@Controller
@RequestMapping("userwithdraw/payManualAudit")
public class PayManualAuditController extends BaseController {

    @Autowired
    private IPayManualRecordService payManualRecordService;

    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("paymanualaudit_list")
    public String pageQuery(
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute PayManualAuditCondition payManualAuditCondition, Model model) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (condition != null && condition.length() > 0) {
            payManualAuditCondition = gson.fromJson(condition, PayManualAuditCondition.class);
        }
        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        searchable.addSearchFilters(payManualAuditCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        // 设置排序条件
        searchable.addSort(new Sort(Sort.Direction.DESC, "created"));
        model.addAttribute("condition", gson.toJson(payManualAuditCondition));
        model.addAttribute("page", payManualRecordService.findAll(searchable));
        return "userwithdraw/paymanualaudit/list";
    }

    /**
     * 审核成功
     *
     * @param id
     * @param session
     * @return
     */
    @RequestMapping(value = "auditSuccess/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("paymanualaudit_operate")
    public String auditSuccess(@PathVariable Long id, HttpSession session) {
        try {
            String result = payManualRecordService.auditSuccess(id);
            if (StringUtils.isEmpty(result)) {
                payManualRecordService.querysendRequest(id);
                session.setAttribute("message", "操作成功");
            } else {
                session.setAttribute("error", result);
            }
        } catch (Exception e) {
            session.setAttribute("error", "操作失败！");
            e.printStackTrace();
        }
        return "redirect:/userwithdraw/payManualAudit/list";
    }

    /**
     * 审核失败
     *
     * @param id
     * @param session
     * @return
     */
    @RequestMapping(value = "auditFailure/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("paymanualaudit_operate")
    public String auditFailure(@PathVariable Long id, HttpSession session) {
        try {
            payManualRecordService.auditFailure(id);
            session.setAttribute("message", "操作成功！");
        } catch (Exception e) {
            session.setAttribute("error", "操作失败！");
            e.printStackTrace();
        }
        return "redirect:/userwithdraw/payManualAudit/list";
    }

    public IPayManualRecordService getPayManualRecordService() {
        return payManualRecordService;
    }

    public void setPayManualRecordService(IPayManualRecordService payManualRecordService) {
        this.payManualRecordService = payManualRecordService;
    }
}