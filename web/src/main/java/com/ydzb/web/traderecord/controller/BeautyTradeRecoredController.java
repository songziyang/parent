package com.ydzb.web.traderecord.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.product.service.IBeautyTradeRecordService;
import com.ydzb.web.traderecord.condition.BeautyTradeRecordCondition;
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
 * 美利金融交易记录controller
 */
@Controller
@RequestMapping(value = "traderecord/beauty")
public class BeautyTradeRecoredController extends BaseController {

    @Autowired
    private IBeautyTradeRecordService beautyTradeRecordService;

    /**
     * 分页列表
     * @param pageSize
     * @param pageCurrent
     * @param condition
     * @param beautyTradeRecordCondition
     * @param model
     * @return
     */
    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("beautyrecord_list")
    public String pageQuery(
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute BeautyTradeRecordCondition beautyTradeRecordCondition,
            Model model) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        if (condition != null && condition.length() > 0) {
            beautyTradeRecordCondition = gson.fromJson(condition, BeautyTradeRecordCondition.class);
        }

        Searchable searchable = Searchable.newSearchable();
        searchable.addSearchFilters(beautyTradeRecordCondition.getAndFilters());
        searchable.setPage(pageCurrent, pageSize);
        searchable.addSort(new Sort(Sort.Direction.DESC, "id"));
        model.addAttribute("condition", gson.toJson(beautyTradeRecordCondition));
        model.addAttribute("page", beautyTradeRecordService.findAll(searchable));
        return "traderecord/beauty/list";
    }

    /**
     * 导出excel
     * @param condition
     * @param beautyTradeRecordCondition
     * @param request
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "export/{condition}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("beautyrecord_list")
    public String exportExcel(
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute BeautyTradeRecordCondition beautyTradeRecordCondition,
            HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String path = request.getSession().getServletContext().getRealPath("/static/download");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        if (condition != null && condition.length() > 0) {
            beautyTradeRecordCondition = gson.fromJson(condition, BeautyTradeRecordCondition.class);
        }
        Map<String, Object> filter = beautyTradeRecordCondition.getSqlFilters();
        List<Object[]> list = beautyTradeRecordService.findExportData(filter);
        String fileName = beautyTradeRecordService.exportExcel(list, path);
        redirectAttributes.addFlashAttribute("fileName", fileName);
        return "redirect:/traderecord/beauty/list";
    }
}
