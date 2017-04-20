package com.ydzb.web.traderecord.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.product.entity.CurrentOverLog;
import com.ydzb.product.service.ICurrentOverLogService;
import com.ydzb.web.traderecord.condition.CurrentOverLogCondition;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping(value = "traderecord/currentoverlog")
public class CurrentOverLogController extends BaseController {


    @Autowired
    private ICurrentOverLogService currentOverLogService;

    @RequestMapping(value = "dayloanloglist", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("dayloanover_list")
    public String dayLoanOverLogList(
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute CurrentOverLogCondition currentOverLogCondition,
            Model model) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();

        if (condition != null && condition.length() > 0) {
            currentOverLogCondition = gson.fromJson(condition, CurrentOverLogCondition.class);
        }

        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        // 添加查询条件
        searchable.addSearchFilters(currentOverLogCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        // 设置排序条件
        searchable.addSort(new Sort(Direction.DESC, "redemptionTime"));
        model.addAttribute("condition", gson.toJson(currentOverLogCondition));
        model.addAttribute("page", currentOverLogService.findAll(searchable));
        return "userinfo/dayloanover/list";
    }


    @RequestMapping(value = "exportExcelDay/{condition}", method = {
            RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("dayloanover_list")
    public String exportExcelDay(
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute CurrentOverLogCondition currentOverLogCondition,
            Model model, HttpSession session, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String path = request.getSession().getServletContext().getRealPath("/static/download");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();

        if (condition != null && condition.length() > 0) {
            currentOverLogCondition = gson.fromJson(condition, CurrentOverLogCondition.class);
        }

        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        // 添加查询条件
        searchable.addSearchFilters(currentOverLogCondition.getAndFilters());
        searchable.addSort(new Sort(Direction.DESC, "redemptionTime"));
        List<CurrentOverLog> list = currentOverLogService.findAllWithSort(searchable);
        String fileName = currentOverLogService.exportExcele(list, path);
        redirectAttributes.addFlashAttribute("fileName", fileName);
        return "redirect:/traderecord/currentoverlog/dayloanloglist";
    }


    public ICurrentOverLogService getCurrentOverLogService() {
        return currentOverLogService;
    }

    public void setCurrentOverLogService(ICurrentOverLogService currentOverLogService) {
        this.currentOverLogService = currentOverLogService;
    }
}
