package com.ydzb.web.platform.controller;

import com.alibaba.druid.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.product.entity.PlatformStatistics;
import com.ydzb.product.service.IPlatformStatisticsService;
import com.ydzb.web.platform.condition.PlatformStatisticsCondition;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;


/**
 * 平台统计controller
 */
@Controller
@RequestMapping("platform/platformStatistics")
public class PlatformStatisticsController extends BaseController {

    @Autowired
    private IPlatformStatisticsService platformService;

    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("platformstatistics_list")
    public String pageQuery(
            @RequestParam(defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(defaultValue = PAGE_CURRENT) int pageCurrent,
            String condition, PlatformStatisticsCondition platformCondition, Model model) {

        Gson gson = new GsonBuilder().
                excludeFieldsWithoutExposeAnnotation().create();
        if (!StringUtils.isEmpty(condition)) {
            platformCondition = gson.fromJson(condition, PlatformStatisticsCondition.class);
        }

        //创建查询
        Searchable searchable = Searchable.newSearchable();
        //添加查询条件
        searchable.addSearchFilters(platformCondition.getAndFilters());
        //设置排序条件
        searchable.addSort(new Sort(Direction.DESC, "created"));
        //设置分页参数
        searchable.setPage(pageCurrent, pageSize);

        model.addAttribute("condition", gson.toJson(platformCondition));
        model.addAttribute("page", platformService.findAll(searchable));

        return "product/platformstatistics/list";
    }

    @RequestMapping(value = "exportExcel/{condition}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("platformstatistics_list")
    public String exportExcel(
            @PathVariable(value = "condition") String condition,
            PlatformStatisticsCondition platformCondition, Model model,
            HttpSession session, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String logoRealPathDir = request.getSession().getServletContext().getRealPath("/static/download");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (condition != null && condition.length() > 0) {
            platformCondition = gson.fromJson(condition, PlatformStatisticsCondition.class);
        }
        Map<String, Object> filter = platformCondition.getSqlFilters();
        List<Object[]> list = platformService.findExportData(filter);
        String fileName = platformService.exportExcel(list, logoRealPathDir);
        redirectAttributes.addFlashAttribute("fileName", fileName);
        //session.setAttribute("message", "导出成功");
        return "redirect:/platform/platformStatistics/list";
    }

    public IPlatformStatisticsService getPlatformService() {
        return platformService;
    }

    public void setPlatformService(IPlatformStatisticsService platformService) {
        this.platformService = platformService;
    }
}