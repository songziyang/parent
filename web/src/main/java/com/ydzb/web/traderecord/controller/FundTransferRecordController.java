package com.ydzb.web.traderecord.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.user.service.IFundTransferRecordService;
import com.ydzb.web.traderecord.condition.FundTransferRecordCondition;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 资金转账记录controller
 */
@Controller
@RequestMapping("traderecord/fundtransfer")
public class FundTransferRecordController extends BaseController {

    @Autowired
    private IFundTransferRecordService fundTransferRecordService;

    /**
     * 资金转账记录列表
     * @param pageSize
     * @param pageCurrent
     * @param condition
     * @param fundTransferRecordCondition
     * @param model
     * @return
     */
    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("fundtransfer_list")
    public String pageQuery(
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute FundTransferRecordCondition fundTransferRecordCondition,
            Model model) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (condition != null && condition.length() > 0) {
            fundTransferRecordCondition = gson.fromJson(condition, FundTransferRecordCondition.class);
        }

        Searchable searchable = Searchable.newSearchable();
        searchable.addSearchFilters(fundTransferRecordCondition.getAndFilters());
        searchable.setPage(pageCurrent, pageSize);
        searchable.addSort(new Sort(Sort.Direction.DESC, "optime"));
        model.addAttribute("condition", gson.toJson(fundTransferRecordCondition));
        model.addAttribute("page", fundTransferRecordService.findAll(searchable));
        return "traderecord/fundtransfer/list";
    }

    /**
     * 导出excel
     * @param condition
     * @param fundTransferRecordCondition
     * @param session
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "exportExcel/{condition}", method = { RequestMethod.GET, RequestMethod.POST })
    public String exportExcel(@PathVariable("condition") String condition,
            FundTransferRecordCondition fundTransferRecordCondition,
            HttpSession session, RedirectAttributes redirectAttributes) {
        String path = session.getServletContext().getRealPath("/static/download");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (StringUtils.isNotEmpty(condition)) {
            fundTransferRecordCondition = gson.fromJson(condition, FundTransferRecordCondition.class);
        }
        Map<String, Object> filter = fundTransferRecordCondition.getSqlFilters();
        List<Object[]> list = fundTransferRecordService.findExportData(filter);
        String fileName = fundTransferRecordService.exportExcel(list, path);
        redirectAttributes.addFlashAttribute("fileName", fileName);
        return "redirect:/traderecord/fundtransfer/list";
    }
}