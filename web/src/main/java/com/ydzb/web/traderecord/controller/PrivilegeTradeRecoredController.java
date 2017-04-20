package com.ydzb.web.traderecord.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.product.service.ICurrentTradeRecoredService;
import com.ydzb.product.service.IPrivilegeTradeRecoredService;
import com.ydzb.web.traderecord.condition.CurrentTradeRecordCondition;
import com.ydzb.web.traderecord.condition.PrivilegeTradeRecordCondition;
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

@Controller
@RequestMapping(value = "traderecord/privilege")
public class PrivilegeTradeRecoredController extends BaseController {

    @Autowired
    private IPrivilegeTradeRecoredService privilegeTradeRecoredService;

    @RequestMapping(value = "listPrivilegeRecored", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("privilegerecored_list")
    public String pageQuery(
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute PrivilegeTradeRecordCondition privilegeTradeRecordCondition,
            Model model) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        if (condition != null && condition.length() > 0) {
            privilegeTradeRecordCondition = gson.fromJson(condition, PrivilegeTradeRecordCondition.class);
        }

        Searchable searchable = Searchable.newSearchable();
        searchable.addSearchFilters(privilegeTradeRecordCondition.getAndFilters());
        searchable.setPage(pageCurrent, pageSize);
        searchable.addSort(new Sort(Sort.Direction.DESC, "buyTime"));
        model.addAttribute("condition", gson.toJson(privilegeTradeRecordCondition));
        model.addAttribute("page", privilegeTradeRecoredService.findAll(searchable));
        return "traderecord/privilege/list";
    }

    /**
     * 导出excel
     * @param condition
     * @param privilegeTradeRecordCondition
     * @param request
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "exportExcel/{condition}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("privilegerecored_list")
    public String exportExcelFund(
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute PrivilegeTradeRecordCondition privilegeTradeRecordCondition,
            HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String path = request.getSession().getServletContext().getRealPath("/static/download");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        if (condition != null && condition.length() > 0) {
            privilegeTradeRecordCondition = gson.fromJson(condition, PrivilegeTradeRecordCondition.class);
        }
        List<Object[]> list = privilegeTradeRecoredService.findExportData(privilegeTradeRecordCondition.getSqlFilter());
        String fileName = privilegeTradeRecoredService.exportExcel(list, path);
        redirectAttributes.addFlashAttribute("fileName", fileName);

        return "redirect:/traderecord/privilege/listPrivilegeRecored";
    }

    public IPrivilegeTradeRecoredService getPrivilegeTradeRecoredService() {
        return privilegeTradeRecoredService;
    }

    public void setPrivilegeTradeRecoredService(IPrivilegeTradeRecoredService privilegeTradeRecoredService) {
        this.privilegeTradeRecoredService = privilegeTradeRecoredService;
    }
}
