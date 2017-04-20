package com.ydzb.web.product.controller;

import com.alibaba.druid.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.product.service.IProductSalesRecordService;
import com.ydzb.web.product.condition.ProductSalesRecordCondition;
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

@Controller
@RequestMapping("product/productsalesrecord")
public class ProductSalesRecordController extends BaseController {

    @Autowired
    private IProductSalesRecordService productSalesRecordService;


    @RequestMapping(value = "listSalesRecord", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("productsalesrecord_list")
    public String pageQuery(@RequestParam(defaultValue = PAGE_SIZE) int pageSize,
                            @RequestParam(defaultValue = PAGE_CURRENT) int pageCurrent,
                            @ModelAttribute("condition") String condition,
                            @ModelAttribute ProductSalesRecordCondition productSalesRecordCondition, Model model) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (!StringUtils.isEmpty(condition)) {
            productSalesRecordCondition = gson.fromJson(condition, ProductSalesRecordCondition.class);
        }
        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        // 添加查询条件
        searchable.addSearchFilters(productSalesRecordCondition.getAndFilters());
        // 设置排序条件
        searchable.addSort(new Sort(Direction.DESC, "created"));
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);

        model.addAttribute("condition", gson.toJson(productSalesRecordCondition));
        model.addAttribute("page", productSalesRecordService.findAll(searchable));

        return "product/productsalesrecord/list";
    }


    @RequestMapping(value = "exportExcel/{condition}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("productsalesrecord_list")
    public String exportExcel(@ModelAttribute(value = "condition") String condition,
                              @ModelAttribute ProductSalesRecordCondition productSalesRecordCondition,
                              HttpServletRequest request, RedirectAttributes redirectAttributes) {

        String path = request.getSession().getServletContext().getRealPath("/static/download");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (condition != null && condition.length() > 0) {
            productSalesRecordCondition = gson.fromJson(condition, ProductSalesRecordCondition.class);
        }
        Map<String, Object> filter = productSalesRecordCondition.getSqlFilters();
        List<Object[]> list = productSalesRecordService.findExportData(filter);
        String fileName = productSalesRecordService.exportExcel(list, path);
        redirectAttributes.addFlashAttribute("fileName", fileName);

        return "redirect:/product/productsalesrecord/listSalesRecord";
    }


    public IProductSalesRecordService getProductSalesRecordService() {
        return productSalesRecordService;
    }

    public void setProductSalesRecordService(IProductSalesRecordService productSalesRecordService) {
        this.productSalesRecordService = productSalesRecordService;
    }


}