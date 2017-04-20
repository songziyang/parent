package com.ydzb.web.redpacket.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.packet.service.IUserIntegralRecordService;
import com.ydzb.web.redpacket.condition.SilverExchangeCondition;
import com.ydzb.web.redpacket.condition.UserIntegralRecordCondition;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("redpacket/integralrecord")
public class UserIntegralRecordController extends BaseController {


    @Autowired
    private IUserIntegralRecordService userIntegralRecordService;


    @RequestMapping(value = "listIntegralRecord", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("integralrecord_list")
    public String pageQuery(
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute UserIntegralRecordCondition userIntegralRecordCondition, Model model) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        if (condition != null && condition.length() > 0) {
            userIntegralRecordCondition = gson.fromJson(condition, UserIntegralRecordCondition.class);
        }

        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        // 添加查询条件
        searchable.addSearchFilters(userIntegralRecordCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        // 设置排序条件
        searchable.addSort(new Sort(Sort.Direction.DESC, "created"));
        model.addAttribute("condition", gson.toJson(userIntegralRecordCondition));
        model.addAttribute("page", userIntegralRecordService.findAll(searchable));
        return "redpacket/integralrecord/list";
    }


    @RequestMapping(value = "exportExcel/{condition}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("integralrecord_list")
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
        List<Object[]> list = userIntegralRecordService.findExportData(filter);
        String fileName = userIntegralRecordService.exportExcel(list, logoRealPathDir);
        redirectAttributes.addFlashAttribute("fileName", fileName);
        return "redirect:/redpacket/integralrecord/listIntegralRecord";
    }

    public IUserIntegralRecordService getUserIntegralRecordService() {
        return userIntegralRecordService;
    }

    public void setUserIntegralRecordService(IUserIntegralRecordService userIntegralRecordService) {
        this.userIntegralRecordService = userIntegralRecordService;
    }
}