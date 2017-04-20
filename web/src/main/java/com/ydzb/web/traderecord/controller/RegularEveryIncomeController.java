package com.ydzb.web.traderecord.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.packet.entity.ActivityYuanXiao;
import com.ydzb.product.entity.RegularEveryIncome;
import com.ydzb.product.service.IRegularEveryIncomeService;
import com.ydzb.web.traderecord.condition.RegularEveryIncomeCondition;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "traderecord/regularevery")
public class RegularEveryIncomeController extends BaseController {

    @Autowired
    private IRegularEveryIncomeService regularEveryIncomeService;

    @RequestMapping(value = "listRegularevery", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("regularevery_list")
    public String pageQueryMatchPlans(
            @RequestParam(value = "pageSize", defaultValue = "15") int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute RegularEveryIncomeCondition regularEveryIncomeCondition, Model model) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        if (condition != null && condition.length() > 0) {
            regularEveryIncomeCondition = gson.fromJson(condition, RegularEveryIncomeCondition.class);
        }

        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        // 添加查询条件

        searchable.addSearchFilters(regularEveryIncomeCondition.getAndFilters());
        searchable.setPage(pageCurrent, pageSize);
        // 设置排序条件
        searchable.addSort(new Sort(Direction.DESC, "sdate"));

        initData(regularEveryIncomeCondition);

        model.addAttribute("condition", gson.toJson(regularEveryIncomeCondition));
        model.addAttribute("page", regularEveryIncomeService.findAll(searchable));

        return "traderecord/regularevery/list";
    }

    public void initData(RegularEveryIncomeCondition regularEveryIncomeCondition) {
        regularEveryIncomeService.findRevenuBetweenDate(regularEveryIncomeCondition.getStartDate(), regularEveryIncomeCondition.getEndDate());
    }


    @RequestMapping(value = "exportExcel", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("regularevery_list")
    public String export(@ModelAttribute RegularEveryIncomeCondition regularEveryIncomeCondition,
                         HttpSession session, RedirectAttributes redirectAttributes) {
        String logoRealPathDir = session.getServletContext().getRealPath("/static/download");
        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        // 添加查询条件
        searchable.addSearchFilters(regularEveryIncomeCondition.getAndFilters());
        // 设置排序条件
        searchable.addSort(new Sort(Sort.Direction.DESC, "sdate"));
        List<RegularEveryIncome> data = regularEveryIncomeService.findAllWithSort(searchable);
        String fileName = regularEveryIncomeService.exportExcel(data, logoRealPathDir);
        redirectAttributes.addFlashAttribute("fileName", fileName);

        return "redirect:/traderecord/regularevery/listRegularevery";
    }


    public IRegularEveryIncomeService getRegularEveryIncomeService() {
        return regularEveryIncomeService;
    }

    public void setRegularEveryIncomeService(IRegularEveryIncomeService regularEveryIncomeService) {
        this.regularEveryIncomeService = regularEveryIncomeService;
    }
}
