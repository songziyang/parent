package com.ydzb.web.traderecord.controller;

import com.baofoo.sdk.lang.string.StringUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.user.service.IUserIncomeRecordService;
import com.ydzb.web.traderecord.condition.UserIncomeRecordCondition;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户每日收益
 */
@Controller
@RequestMapping("traderecord/incomeDaily")
public class UserIncomeDailyRecordController extends BaseController {

    @Autowired
    private IUserIncomeRecordService userIncomeRecordService;

    /**
     * 分页列表
     * @param pageSize 每页显示条数
     * @param pageCurrent 当前页数(从0开始)
     * @param condition 查询条件json
     * @param userIncomeRecordCondition 查询条件
     * @param model
     * @return
     */
    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("incomedaily_list")
    public String pageQuery(
            @RequestParam(defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition, Model model,
            @ModelAttribute(value = "userIncomeRecordCondition") UserIncomeRecordCondition userIncomeRecordCondition) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (condition != null && condition.length() > 0) {
            userIncomeRecordCondition = gson.fromJson(condition, UserIncomeRecordCondition.class);
        }
        model.addAttribute("condition", gson.toJson(userIncomeRecordCondition));
        Page page;
        BigDecimal sumIncome;
        if (StringUtils.isEmpty(userIncomeRecordCondition.getStartDate()) && StringUtils.isEmpty(userIncomeRecordCondition.getEndDate())) {
            page = new PageImpl(new ArrayList());
            sumIncome = BigDecimal.ZERO;
        } else {
            page = userIncomeRecordService.findDailyIncome(userIncomeRecordCondition.getSqlFilters(),
                    pageCurrent, pageSize);
            //总收益金额
            sumIncome = userIncomeRecordService.findSumIncome(userIncomeRecordCondition.getStartDate(),
                    userIncomeRecordCondition.getEndDate());
        }
        model.addAttribute("page", page);
        //总收益金额
        model.addAttribute("sumIncome", sumIncome);
        return "traderecord/incomedaily/list";
    }

    /**
     * 导出Excel
     * @param condition 查询条件json
     * @param userIncomeRecordCondition 查询条件
     * @param request
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "export/{condition}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("incomedaily_list")
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
        List<Object[]> list = userIncomeRecordService.findDailyIncomeExportData(filter);
        String fileName = userIncomeRecordService.dailyIncomeExportExcel(list, path);
        redirectAttributes.addFlashAttribute("fileName", fileName);
        return "redirect:/traderecord/incomeDaily/list";
    }
}