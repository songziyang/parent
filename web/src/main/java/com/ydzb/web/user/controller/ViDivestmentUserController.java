package com.ydzb.web.user.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.user.service.IViDivestmentUserService;
import com.ydzb.web.user.condition.ViDivestmentUserCondition;
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
@RequestMapping(value = "userinfo/divestment")
public class ViDivestmentUserController extends BaseController {

    @Autowired
    private IViDivestmentUserService divestmentUserService;


    @RequestMapping(value = "listDivestment", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("divestment_list")
    public String pageQuery(@RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
                            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
                            @ModelAttribute(value = "condition") String condition,
                            @ModelAttribute ViDivestmentUserCondition divestmentUserCondition, Model model) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (condition != null && condition.length() > 0) {
            divestmentUserCondition = gson.fromJson(condition, ViDivestmentUserCondition.class);
        }
        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        // 添加查询条件
        searchable.addSearchFilters(divestmentUserCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        // 设置排序条件
        searchable.addSort(new Sort(Sort.Direction.DESC, "id"));
        model.addAttribute("condition", gson.toJson(divestmentUserCondition));
        model.addAttribute("page", divestmentUserService.findAll(searchable));

        return "userinfo/divestment/list";
    }


    @RequestMapping(value = "exportExcel/{condition}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("divestment_list")
    public String exportExcel(@ModelAttribute(value = "condition") String condition,
                              @ModelAttribute ViDivestmentUserCondition divestmentUserCondition,
                              HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String path = request.getSession().getServletContext().getRealPath("/static/download");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (condition != null && condition.length() > 0) {
            divestmentUserCondition = gson.fromJson(condition, ViDivestmentUserCondition.class);
        }
        Map<String, Object> filter = divestmentUserCondition.getSqlFilters();
        List<Object[]> list = divestmentUserService.findExportData(filter);
        String fileName = divestmentUserService.exportExcel(list, path);
        redirectAttributes.addFlashAttribute("fileName", fileName);
        return "redirect:/userinfo/divestment/listDivestment";
    }


    public IViDivestmentUserService getDivestmentUserService() {
        return divestmentUserService;
    }

    public void setDivestmentUserService(IViDivestmentUserService divestmentUserService) {
        this.divestmentUserService = divestmentUserService;
    }
}