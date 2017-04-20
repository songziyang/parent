package com.ydzb.web.withdraw.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.web.withdraw.condition.ManualPayCondition;
import com.ydzb.withdraw.service.IPayManualRequestService;
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


@Controller
@RequestMapping(value = "userwithdraw/manualpay")
public class ManualPayController extends BaseController {

    @Autowired
    private IPayManualRequestService payManualRequestService;


    @RequestMapping(value = "listManualPay", method = {RequestMethod.GET,RequestMethod.POST})
    @RequiresPermissions(value = {"withdraws_list", "paymanualrecord_list"}, logical = Logical.OR)
    public String pageQuery(
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute ManualPayCondition manualPayCondition, Model model) throws Exception {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        if (condition != null && condition.length() > 0) {
            manualPayCondition = gson.fromJson(condition, ManualPayCondition.class);
        }

        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        searchable.addSearchFilters(manualPayCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        // 设置排序条件
        searchable.addSort(new Sort(Sort.Direction.DESC, "id"));
        model.addAttribute("condition", gson.toJson(manualPayCondition));
        model.addAttribute("page", payManualRequestService.findAll(searchable));

        return "userwithdraw/manualpay/list";
    }

    public IPayManualRequestService getPayManualRequestService() {
        return payManualRequestService;
    }

    public void setPayManualRequestService(IPayManualRequestService payManualRequestService) {
        this.payManualRequestService = payManualRequestService;
    }
}
