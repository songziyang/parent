package com.ydzb.web.traderecord.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.product.entity.CurrentTradeRecored;
import com.ydzb.product.service.ICurrentTradeRecoredService;
import com.ydzb.user.entity.User;
import com.ydzb.web.traderecord.condition.CurrentTradeRecordCondition;
import com.ydzb.web.user.condition.UserReferralCondition;
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
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "traderecord/current")
public class CurrentTradeRecoredController extends BaseController {

    @Autowired
    private ICurrentTradeRecoredService currentTradeRecoredService;

    @RequestMapping(value = "listCurrentRecored", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("currentrecored_list")
    public String pageQuery(
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute CurrentTradeRecordCondition currentTradeRecordCondition,
            Model model) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        if (condition != null && condition.length() > 0) {
            currentTradeRecordCondition = gson.fromJson(condition, CurrentTradeRecordCondition.class);
        }

        Searchable searchable = Searchable.newSearchable();
        searchable.addSearchFilters(currentTradeRecordCondition.getAndFilters());
        searchable.setPage(pageCurrent, pageSize);
        searchable.addSort(new Sort(Sort.Direction.DESC, "buyTime"));
        model.addAttribute("condition", gson.toJson(currentTradeRecordCondition));
        model.addAttribute("page", currentTradeRecoredService.findAll(searchable));
        return "traderecord/current/list";
    }

    /**
     * 导出excel
     * @param condition
     * @param currentTradeRecordCondition
     * @param request
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "exportExcel/{condition}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("currentrecored_list")
    public String exportExcelFund(
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute CurrentTradeRecordCondition currentTradeRecordCondition,
            HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String path = request.getSession().getServletContext().getRealPath("/static/download");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        if (condition != null && condition.length() > 0) {
            currentTradeRecordCondition = gson.fromJson(condition, CurrentTradeRecordCondition.class);
        }
        List<Object[]> list = currentTradeRecoredService.findExportData(currentTradeRecordCondition.getSqlFilter());
        String fileName = currentTradeRecoredService.exportExcel(list, path);
        redirectAttributes.addFlashAttribute("fileName", fileName);

        return "redirect:/traderecord/current/listCurrentRecored";
    }

    public ICurrentTradeRecoredService getCurrentTradeRecoredService() {
        return currentTradeRecoredService;
    }

    public void setCurrentTradeRecoredService(ICurrentTradeRecoredService currentTradeRecoredService) {
        this.currentTradeRecoredService = currentTradeRecoredService;
    }
}
