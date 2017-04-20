package com.ydzb.web.traderecord.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.product.service.IFreeTradeRecordService;
import com.ydzb.web.traderecord.condition.FreeTradeRecordCondition;
import com.ydzb.web.traderecord.condition.RagularTradeRecordCondition;
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
 * 随心存交易记录controller
 */
@Controller
@RequestMapping(value = "traderecord/free")
public class FreeTradeRecoredController extends BaseController {

    @Autowired
    private IFreeTradeRecordService freeTradeRecordService;

    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("freerecord_list")
    public String pageQuery(
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute FreeTradeRecordCondition freeTradeRecordCondition,
            Model model) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        if (condition != null && condition.length() > 0) {
            freeTradeRecordCondition = gson.fromJson(condition, FreeTradeRecordCondition.class);
        }

        Searchable searchable = Searchable.newSearchable();
        searchable.addSearchFilters(freeTradeRecordCondition.getAndFilters());
        searchable.setPage(pageCurrent, pageSize);
        searchable.addSort(new Sort(Sort.Direction.DESC, "id"));
        model.addAttribute("condition", gson.toJson(freeTradeRecordCondition));
        model.addAttribute("page", freeTradeRecordService.findAll(searchable));
        return "traderecord/free/list";
    }

    /**
     * 导出excel
     * @param condition
     * @param freeTradeRecordCondition
     * @param request
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "export/{condition}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("freerecord_list")
    public String exportExcel(
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute FreeTradeRecordCondition freeTradeRecordCondition,
            HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String path = request.getSession().getServletContext().getRealPath("/static/download");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        if (condition != null && condition.length() > 0) {
            freeTradeRecordCondition = gson.fromJson(condition, FreeTradeRecordCondition.class);
        }
        Map<String, Object> filter = freeTradeRecordCondition.getSqlFilters();
        List<Object[]> list = freeTradeRecordService.findExportData(filter);
        String fileName = freeTradeRecordService.exportExcel(list, path);
        redirectAttributes.addFlashAttribute("fileName", fileName);
        return "redirect:/traderecord/free/list";
    }
}
