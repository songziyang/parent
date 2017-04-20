package com.ydzb.web.product.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.product.entity.VRagularUserAccount;
import com.ydzb.product.service.IVRagularUserAccountService;
import com.ydzb.web.product.condition.RagularUserAccountCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("product/ragularaccount")
public class VRagularUserAccountController extends BaseController {

    @Autowired
    private IVRagularUserAccountService vRagularUserAccountService;

    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    public String pageQuery(@RequestParam(defaultValue = PAGE_SIZE) int pageSize,
                            @RequestParam(defaultValue = PAGE_CURRENT) int pageCurrent,
                            @ModelAttribute("condition") String condition, Model model,
                            @ModelAttribute("ragularUserAccountCondition") RagularUserAccountCondition ragularUserAccountCondition) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        if (condition != null && condition.length() > 0) {
            ragularUserAccountCondition = gson.fromJson(condition, RagularUserAccountCondition.class);
        }
        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        searchable.addSort(new Sort(Sort.Direction.DESC, "three_month_fund", "six_month_fund", "twelve_month_fund"));
        // 添加查询条件
        searchable.addSearchFilters(ragularUserAccountCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);

        Page<VRagularUserAccount> userAccounts = vRagularUserAccountService.findAll(searchable);
        model.addAttribute("condition", gson.toJson(ragularUserAccountCondition));
        model.addAttribute("page", userAccounts);

        model.addAttribute("oneMonthFund", vRagularUserAccountService.queryOneMonthFund());
        model.addAttribute("threeMonthFund", vRagularUserAccountService.queryThreeMonthFund());
        model.addAttribute("sixMonthFund", vRagularUserAccountService.querySixMonthFund());
        model.addAttribute("twelveMonthFund", vRagularUserAccountService.queryTwelveMonthFund());
        return "product/ragularaccount/list";
    }

    public IVRagularUserAccountService getvRagularUserAccountService() {
        return vRagularUserAccountService;
    }

    public void setvRagularUserAccountService(IVRagularUserAccountService vRagularUserAccountService) {
        this.vRagularUserAccountService = vRagularUserAccountService;
    }
}