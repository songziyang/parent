package com.ydzb.web.traderecord.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.packet.service.IExpMoneyRecordService;
import com.ydzb.web.traderecord.condition.ExpMoneyRecordCondition;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
 * 体验金记录controller
 * @author sy
 */
@Controller
@RequestMapping("traderecord/expmoney")
public class ExpMoneyRecordController extends BaseController {

    @Autowired
    private IExpMoneyRecordService expMoneyRecordService;

    /**
     * 分页查询
     * @param pageSize
     * @param pageCurrent
     * @param condition
     * @param expMoneyRecordCondition
     * @param model
     * @return
     */
    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("expmoneyrecord_list")
    public String pageQuery(
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute ExpMoneyRecordCondition expMoneyRecordCondition,
            Model model) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (condition != null && condition.length() > 0) {
            expMoneyRecordCondition = gson.fromJson(condition, ExpMoneyRecordCondition.class);
        }

        Searchable searchable = Searchable.newSearchable();
        searchable.addSearchFilters(expMoneyRecordCondition.getAndFilters());
        searchable.setPage(pageCurrent, pageSize);
        searchable.addSort(new Sort(Sort.Direction.DESC, "recordTime"));
        model.addAttribute("condition", gson.toJson(expMoneyRecordCondition));
        model.addAttribute("page", expMoneyRecordService.findAll(searchable));
        return "traderecord/expmoney/list";
    }

    /**
     * 导出excel
     * @param condition
     * @param expMoneyRecordCondition
     * @param request
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "exportExcel/{condition}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("expmoneyrecord_list")
    public String exportExcelFund(
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute ExpMoneyRecordCondition expMoneyRecordCondition,
            HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String path = request.getSession().getServletContext().getRealPath("/static/download");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        if (condition != null && condition.length() > 0) {
            expMoneyRecordCondition = gson.fromJson(condition, ExpMoneyRecordCondition.class);
        }
        Map<String, Object> filter = expMoneyRecordCondition.getSqlFilters();
        List<Object[]> list = expMoneyRecordService.findExportData(filter);
        String fileName = expMoneyRecordService.exportExcel(list, path);
        redirectAttributes.addFlashAttribute("fileName", fileName);
        return "redirect:/traderecord/expmoney/list";
    }
}