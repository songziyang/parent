package com.ydzb.web.platform.controller;

import com.alibaba.druid.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.product.entity.PlatformPay;
import com.ydzb.product.entity.PlatformStatistics;
import com.ydzb.product.service.IPlatformPayService;
import com.ydzb.product.service.IPlatformStatisticsService;
import com.ydzb.web.platform.condition.PlatformPayCondition;
import com.ydzb.web.platform.condition.PlatformStatisticsCondition;
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

/**
 * 平台支出统计控制层
 * @author sy
 */
@Controller
@RequestMapping("platform/pay")
public class PlatformPayController extends BaseController {

    @Autowired
    private IPlatformPayService platformPayService;

    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("platformpay_list")
    public String pageQuery(
            @RequestParam(defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute("condition") String condition,
            @ModelAttribute("platformPayCondition") PlatformPayCondition platformPayCondition, Model model) {
        Gson gson = new GsonBuilder().
                excludeFieldsWithoutExposeAnnotation().create();
        if (!StringUtils.isEmpty(condition)) {
            platformPayCondition = gson.fromJson(condition, PlatformPayCondition.class);
        }

        //创建查询
        Searchable searchable = Searchable.newSearchable();
        //添加查询条件
        searchable.addSearchFilters(platformPayCondition.getAndFilters());
        //设置排序条件
        searchable.addSort(new Sort(Sort.Direction.DESC, "created"));
        //设置分页参数
        searchable.setPage(pageCurrent, pageSize);

        model.addAttribute("condition", gson.toJson(platformPayCondition));
        model.addAttribute("page", platformPayService.findAll(searchable));

        return "product/platformpay/list";
    }

    @RequestMapping(value = "exportExcel/{condition}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("platformpay_list")
    public String exportExcel(
            @PathVariable(value = "condition") String condition,
            PlatformPayCondition platformPayCondition,
            HttpSession session, RedirectAttributes redirectAttributes) {
        String logoRealPathDir = session.getServletContext().getRealPath("/static/download");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (condition != null && condition.length() > 0) {
            platformPayCondition = gson.fromJson(condition, PlatformPayCondition.class);
        }
        Map<String, Object> filters = platformPayCondition.getSqlFilters();
        List<Object[]> list = platformPayService.findExportData(filters);
        String fileName = platformPayService.exportExcel(list, logoRealPathDir);
        redirectAttributes.addFlashAttribute("fileName", fileName);
        //session.setAttribute("message", "导出成功");
        return "redirect:/platform/pay/list";
    }
}
