package com.ydzb.web.user.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.user.service.IUserVipChangeService;
import com.ydzb.web.user.condition.UserVipChangeCondition;
import org.apache.commons.lang3.StringUtils;
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
 * 用户vip变更controller
 */
@Controller
@RequestMapping("userinfo/vipchange")
public class UserVipChangeController extends BaseController {

    @Autowired
    private IUserVipChangeService vipChangeService;

    /**
     * 分页列表
     * @param pageSize 每页显示条数
     * @param pageCurrent 当前页数
     * @param condition 查询条件json
     * @param vipChangeCondition 查询条件
     * @param model
     * @return
     */
    @RequestMapping(value = "list", method = { RequestMethod.GET, RequestMethod.POST })
    @RequiresPermissions("vipchange_list")
    public String list(
            @RequestParam(defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute("condition") String condition,
            @ModelAttribute("vipChangeCondition") UserVipChangeCondition vipChangeCondition,
            Model model) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (StringUtils.isNotEmpty(condition)) {
            vipChangeCondition = gson.fromJson(condition, UserVipChangeCondition.class);
        }
        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        // 添加查询条件
        searchable.addSearchFilters(vipChangeCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        // 设置排序条件
        searchable.addSort(new Sort(Sort.Direction.DESC, "id"));
        model.addAttribute("condition", gson.toJson(vipChangeCondition));
        model.addAttribute("page", vipChangeService.findAll(searchable));
        return "userinfo/vipchange/list";
    }

    /**
     * 导出excel
     * @param condition 查询条件json
     * @param vipChangeCondition 查询条件
     * @param request
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "exportExcel/{condition}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("vipchange_list")
    public String exportExcelFund(
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute(value = "userCondition") UserVipChangeCondition vipChangeCondition,
            HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String path = request.getSession().getServletContext().getRealPath("/static/download");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation() .create();
        if (StringUtils.isNotEmpty(condition)) {
            vipChangeCondition = gson.fromJson(condition, UserVipChangeCondition.class);
        }
        Map<String, Object> filter = vipChangeCondition.getSqlFilters();
        List<Object[]> list = vipChangeService.findExportData(filter);
        String fileName = vipChangeService.exportExcel(list, path);
        redirectAttributes.addFlashAttribute("fileName", fileName);
        return "redirect:/userinfo/vipchange/list";
    }
}