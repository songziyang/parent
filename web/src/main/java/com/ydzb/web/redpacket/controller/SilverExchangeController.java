package com.ydzb.web.redpacket.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.packet.service.ISilverExchangeService;
import com.ydzb.web.platform.condition.PlatformStatisticsCondition;
import com.ydzb.web.redpacket.condition.SilverExchangeCondition;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("redpacket/silverproduct")
public class SilverExchangeController extends BaseController {


    @Autowired
    private ISilverExchangeService silverExchangeService;


    /**
     * @param pageSize
     * @param pageCurrent
     * @param condition
     * @param silverExchangeCondition
     * @param model
     * @return
     */
    @RequestMapping(value = "listSilverproduct", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("silverproduct_list")
    public String pageQuery(
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute SilverExchangeCondition silverExchangeCondition, Model model) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        if (condition != null && condition.length() > 0) {
            silverExchangeCondition = gson.fromJson(condition, SilverExchangeCondition.class);
        }

        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        // 添加查询条件
        searchable.addSearchFilters(silverExchangeCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        // 设置排序条件
        searchable.addSort(new Sort(Sort.Direction.DESC, "created"));
        model.addAttribute("condition", gson.toJson(silverExchangeCondition));
        model.addAttribute("page", silverExchangeService.findAll(searchable));
        return "redpacket/silverproduct/list";
    }


    @RequestMapping(value = "auditSuccess/{id}/{status}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("silverproduct_list")
    public String auditSuccess(@PathVariable Long id, @PathVariable Integer status, HttpSession session) {
        try {
            silverExchangeService.updateSilverExchange(id, status);
            session.setAttribute("message", "操作成功!");
        } catch (Exception e) {
            session.setAttribute("error", "操作失败！");
            e.printStackTrace();
        }
        return "redirect:/redpacket/silverproduct/listSilverproduct";
    }


    @RequestMapping(value = "auditFailure/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("silverproduct_list")
    public String auditFailure(@PathVariable Long id, HttpSession session) {
        try {
            silverExchangeService.auditFailure(id);
            session.setAttribute("message", "操作成功!");
        } catch (Exception e) {
            session.setAttribute("error", "操作失败！");
            e.printStackTrace();
        }
        return "redirect:/redpacket/silverproduct/listSilverproduct";
    }

    @RequestMapping(value = "exportExcel/{condition}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("silverproduct_list")
    public String exportExcel(
            @PathVariable(value = "condition") String condition,
            SilverExchangeCondition silverExchangeCondition, Model model,
            HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String logoRealPathDir = request.getSession().getServletContext().getRealPath("/static/download");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (condition != null && condition.length() > 0) {
            silverExchangeCondition = gson.fromJson(condition, SilverExchangeCondition.class);
        }
        Map<String, Object> filter = silverExchangeCondition.getSqlFilters();
        List<Object[]> list = silverExchangeService.findExportData(filter);
        String fileName = silverExchangeService.exportExcel(list, logoRealPathDir);
        redirectAttributes.addFlashAttribute("fileName", fileName);
        //session.setAttribute("message", "导出成功");
        return "redirect:/redpacket/silverproduct/listSilverproduct";
    }


    public ISilverExchangeService getSilverExchangeService() {
        return silverExchangeService;
    }

    public void setSilverExchangeService(ISilverExchangeService silverExchangeService) {
        this.silverExchangeService = silverExchangeService;
    }
}