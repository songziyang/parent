package com.ydzb.web.redpacket.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.packet.service.IDepositTypeExchangeService;
import com.ydzb.web.redpacket.condition.DepositTypeExchangeCondition;
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
@RequestMapping("redpacket/depositexchange")
public class DepositTypeExchangeController extends BaseController {

    @Autowired
    private IDepositTypeExchangeService depositTypeExchangeService;

    @RequestMapping(value = "listDepositExchange", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("depositexchange_list")
    public String pageQuery(
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute DepositTypeExchangeCondition depositTypeExchangeCondition, Model model) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (condition != null && condition.length() > 0) {
            depositTypeExchangeCondition = gson.fromJson(condition, DepositTypeExchangeCondition.class);
        }

        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        // 添加查询条件
        searchable.addSearchFilters(depositTypeExchangeCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        // 设置排序条件
        searchable.addSort(new Sort(Sort.Direction.DESC, "created"));
        model.addAttribute("condition", gson.toJson(depositTypeExchangeCondition));
        model.addAttribute("page", depositTypeExchangeService.findAll(searchable));
        model.addAttribute("status", depositTypeExchangeCondition.getStatus() == null ? 1 : depositTypeExchangeCondition.getStatus());
        return "redpacket/depositexchange/list";
    }


    @RequestMapping(value = "auditSuccess/{id}/{status}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("depositexchange_deal")
    public String auditSuccess(@PathVariable Long id, @PathVariable Integer status, HttpSession session) {
        try {
            depositTypeExchangeService.updateExchange(id, status);
            session.setAttribute("message", "操作成功!");
        } catch (Exception e) {
            session.setAttribute("error", "操作失败！");
            e.printStackTrace();
        }
        return "redirect:/redpacket/depositexchange/listDepositExchange";
    }


    @RequestMapping(value = "auditSuccess/{status}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("depositexchange_deal")
    public String auditSuccess(@PathVariable Integer status, Long[] ids, HttpSession session) {
        try {
            depositTypeExchangeService.updateExchange(ids, status);
            session.setAttribute("message", "操作成功!");
        } catch (Exception e) {
            session.setAttribute("error", "操作失败！");
            e.printStackTrace();
        }
        return "redirect:/redpacket/depositexchange/listDepositExchange";
    }


    @RequestMapping(value = "exportExcel/{condition}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("depositexchange_list")
    public String exportExcel(@PathVariable(value = "condition") String condition,
                              DepositTypeExchangeCondition depositTypeExchangeCondition, Model model,
                              HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String logoRealPathDir = request.getSession().getServletContext().getRealPath("/static/download");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (condition != null && condition.length() > 0) {
            depositTypeExchangeCondition = gson.fromJson(condition, DepositTypeExchangeCondition.class);
        }
        Map<String, Object> filter = depositTypeExchangeCondition.getSqlFilters();
        List<Object[]> list = depositTypeExchangeService.findExportData(filter);
        String fileName = depositTypeExchangeService.exportExcel(list, logoRealPathDir);
        redirectAttributes.addFlashAttribute("fileName", fileName);
        return "redirect:/redpacket/depositexchange/listDepositExchange";
    }


    public IDepositTypeExchangeService getDepositTypeExchangeService() {
        return depositTypeExchangeService;
    }

    public void setDepositTypeExchangeService(IDepositTypeExchangeService depositTypeExchangeService) {
        this.depositTypeExchangeService = depositTypeExchangeService;
    }
}