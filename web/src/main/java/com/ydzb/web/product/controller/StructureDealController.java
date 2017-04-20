package com.ydzb.web.product.controller;

import com.alibaba.druid.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.product.service.IStructureDealService;
import com.ydzb.web.product.condition.StructureDealCondition;
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
import java.util.List;
import java.util.Map;


/**
 * 转转赚交易记录控制层
 */
@Controller
@RequestMapping("product/structuredeal")
public class StructureDealController extends BaseController {

    @Autowired
    private IStructureDealService structureDealService;

    /**
     * 查询某一转转赚产品下的交易记录
     *
     * @param pageSize
     * @param pageCurrent
     * @param condition
     * @param structureDealCondition
     * @param model
     * @return
     */
    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("structure_list")
    public String pageQuery(
            @RequestParam(defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute("condition") String condition, @ModelAttribute StructureDealCondition structureDealCondition, Model model) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (!StringUtils.isEmpty(condition)) {
            structureDealCondition = gson.fromJson(condition, StructureDealCondition.class);
        }
        //创建查询
        Searchable searchable = Searchable.newSearchable();
        //添加查询条件
        searchable.addSearchFilters(structureDealCondition.getAndFilters());
        //设置排序条件
        searchable.addSort(new Sort(Direction.DESC, "btime"));
        //设置分页参数
        searchable.setPage(pageCurrent, pageSize);

        model.addAttribute("condition", gson.toJson(structureDealCondition));
        model.addAttribute("page", structureDealService.findAll(searchable));
        model.addAttribute("structureId", structureDealCondition.getStructureId());
        return "product/structuredeal/list";
    }

    /**
     *
     * @param condition
     * @param structureDealCondition
     * @param request
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "exportExcel/{condition}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("structure_list")
    public String exportExcelFund(@ModelAttribute(value = "condition") String condition,
            @ModelAttribute StructureDealCondition structureDealCondition,
            HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String path = request.getSession().getServletContext().getRealPath("/static/download");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (condition != null && condition.length() > 0) {
            structureDealCondition = gson.fromJson(condition, StructureDealCondition.class);
        }
        Map<String, Object> filters = structureDealCondition.getSqlFilters();
        List<Object[]> list = structureDealService.findExportData(filters);
        String fileName = structureDealService.exportExcel(list, path);
        redirectAttributes.addFlashAttribute("fileName", fileName);
        redirectAttributes.addFlashAttribute("condition", condition);
        return "redirect:/product/structuredeal/list";
    }
}
