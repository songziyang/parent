package com.ydzb.web.traderecord.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.product.entity.CurrentTradeRecored;
import com.ydzb.product.entity.RagularTradeRecored;
import com.ydzb.product.service.IRagularTradeRecoredService;
import com.ydzb.web.traderecord.condition.CurrentTradeRecordCondition;
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

@Controller
@RequestMapping(value = "traderecord/ragular")
public class RagularTradeRecoredController extends BaseController {


    @Autowired
    private IRagularTradeRecoredService ragularTradeRecoredService;

    @RequestMapping(value = "listRagularRecored", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("ragularrecored_list")
    public String pageQuery(
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute RagularTradeRecordCondition ragularTradeRecordCondition,
            Model model) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        if (condition != null && condition.length() > 0) {
            ragularTradeRecordCondition = gson.fromJson(condition, RagularTradeRecordCondition.class);
        }

        Searchable searchable = Searchable.newSearchable();
        searchable.addSearchFilters(ragularTradeRecordCondition.getAndFilters());
        searchable.setPage(pageCurrent, pageSize);
        searchable.addSort(new Sort(Sort.Direction.DESC, "id"));
        model.addAttribute("condition", gson.toJson(ragularTradeRecordCondition));
        model.addAttribute("page", ragularTradeRecoredService.findAll(searchable));
        return "traderecord/ragularrecored/list";
    }

    /*
     * 导出excel
     * @param condition
     * @param ragularTradeRecordCondition
     * @param request
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "exportRagularRecord/{condition}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("ragularrecored_list")
    public String exportExcel(
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute RagularTradeRecordCondition ragularTradeRecordCondition,
            HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String path = request.getSession().getServletContext().getRealPath("/static/download");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        if (condition != null && condition.length() > 0) {
            ragularTradeRecordCondition = gson.fromJson(condition, RagularTradeRecordCondition.class);
        }
        Map<String, Object> filter = ragularTradeRecordCondition.getSqlFilters();
        List<Object[]> list = ragularTradeRecoredService.findExportData(filter);
        String fileName = ragularTradeRecoredService.exportExcel(list, path);
        redirectAttributes.addFlashAttribute("fileName", fileName);

        return "redirect:/traderecord/ragular/listRagularRecored";
    }

    public IRagularTradeRecoredService getRagularTradeRecoredService() {
        return ragularTradeRecoredService;
    }

    public void setRagularTradeRecoredService(IRagularTradeRecoredService ragularTradeRecoredService) {
        this.ragularTradeRecoredService = ragularTradeRecoredService;
    }
}
