package com.ydzb.web.traderecord.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.user.service.IUserIncomeRecordService;
import com.ydzb.web.traderecord.condition.UserIncomeRecordCondition;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 用户收益记录控制层
 */
@Controller
@RequestMapping("traderecord/income")
public class UserIncomeRecordController extends BaseController {

    @Autowired
    private IUserIncomeRecordService userIncomeRecordService;

    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("incomerecord_list")
    public String pageQuery(
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute UserIncomeRecordCondition userIncomeRecordCondition,
            Model model) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (condition != null && condition.length() > 0) {
            userIncomeRecordCondition = gson.fromJson(condition, UserIncomeRecordCondition.class);
        }
        Searchable searchable = Searchable.newSearchable();
        searchable.addSearchFilters(userIncomeRecordCondition.getAndFilters());
        searchable.setPage(pageCurrent, pageSize);
        model.addAttribute("condition", gson.toJson(userIncomeRecordCondition));
        model.addAttribute("page", userIncomeRecordService.findAll(searchable));
        model.addAttribute("sumIncome", userIncomeRecordService.findSumIncome(userIncomeRecordCondition.getStartDate(),
                userIncomeRecordCondition.getEndDate()));
        return "traderecord/income/list";
    }

    /**
     * 导出Excel
     * @param condition
     * @param userIncomeRecordCondition
     * @param request
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "export/{condition}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("incomerecord_list")
    public String exportExcel(
            @ModelAttribute("condition") String condition,
            @ModelAttribute("userIncomeRecordCondition") UserIncomeRecordCondition userIncomeRecordCondition,
            HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String path = request.getSession().getServletContext().getRealPath("/static/download");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        if (condition != null && condition.length() > 0) {
            userIncomeRecordCondition = gson.fromJson(condition, UserIncomeRecordCondition.class);
        }
        Map<String, Object> filter = userIncomeRecordCondition.getSqlFilters();
        List<Object[]> list = userIncomeRecordService.findExportData(filter);
        String fileName = userIncomeRecordService.exportExcel(list, path);
        redirectAttributes.addFlashAttribute("fileName", fileName);

        return "redirect:/traderecord/income/list";
    }


    public IUserIncomeRecordService getUserIncomeRecordService() {
        return userIncomeRecordService;
    }

    public void setUserIncomeRecordService(IUserIncomeRecordService userIncomeRecordService) {
        this.userIncomeRecordService = userIncomeRecordService;
    }
}