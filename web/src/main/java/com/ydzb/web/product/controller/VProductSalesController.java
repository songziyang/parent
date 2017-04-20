package com.ydzb.web.product.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.product.service.*;
import com.ydzb.web.product.condition.ProductSalesDealCondition;
import com.ydzb.web.product.condition.VProductSalesCondition;
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
 * 产品售出控制层
 */
@Controller
@RequestMapping("product/productSales")
public class VProductSalesController extends BaseController {

    @Autowired
    private IVProductSalesService vProductSalesService;
    @Autowired
    private IProductTypeService productTypeService;
    @Autowired
    private IProductInfoService productInfoService;
    @Autowired
    private ICurrentBuyLogService currentBuyLogService;
    @Autowired
    private IRegularBuyLogService regularBuyLogService;

    /**
     * 产品售出列表
     *
     * @param pageSize
     * @param pageCurrent
     * @param condition
     * @param model
     * @param productSalesCondition
     * @return
     */
    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("productsales_list")
    public String pageQuery(@RequestParam(defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute("condition") String condition, Model model,
            @ModelAttribute("productSalesCondition") VProductSalesCondition productSalesCondition) {
        try {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                    .create();
            if (condition != null && condition.length() > 0) {
                productSalesCondition = gson.fromJson(condition, VProductSalesCondition.class);
            }

            // 创建查询
            Searchable searchable = Searchable.newSearchable();
            // 添加查询条件
            searchable.addSearchFilters(productSalesCondition.getAndFilters());
            // 设置分页参数
            searchable.setPage(pageCurrent, pageSize);
            // 设置排序条件
            searchable.addSort(new Sort(Sort.Direction.DESC, "buyDate"));
            model.addAttribute("condition", gson.toJson(productSalesCondition));
            model.addAttribute("page", vProductSalesService.findAll(searchable));

            model.addAttribute("productLst",
                    productTypeService.findAllWithNoPageNoSort(Searchable.newSearchable()));

            Map<String, Object> sales = vProductSalesService.querySales();

            model.addAttribute("current", sales.get("current"));
            model.addAttribute("threemonth", sales.get("regular_90"));
            model.addAttribute("sixmonth", sales.get("regular_180"));
            model.addAttribute("twelvemonth", sales.get("regular_365"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "product/productsales/list";
    }


    /**
     * 导出excel
     *
     * @param condition
     * @param productSalesCondition
     * @param request
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "export/{condition}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("productsales_list")
    public String exportExcelFund(
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute VProductSalesCondition productSalesCondition,
            HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String path = request.getSession().getServletContext().getRealPath("/static/download");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        if (condition != null && condition.length() > 0) {
            productSalesCondition = gson.fromJson(condition, VProductSalesCondition.class);
        }
        Map<String, Object> filter = productSalesCondition.getSqlFilters();
        List<Object[]> list = vProductSalesService.findExportData(filter);
        String fileName = vProductSalesService.exportExcel(list, path);
        redirectAttributes.addFlashAttribute("fileName", fileName);
        return "redirect:/product/productSales/list";
    }

    /**
     * 活期宝购买详细
     * @param pageSize
     * @param pageCurrent
     * @param condition
     * @param productSalesDealCondition
     * @param model
     * @return
     */
    @RequestMapping(value = "currentDetail", method = { RequestMethod.GET, RequestMethod.POST })
    @RequiresPermissions("productsales_list")
    public String currentPageQuery(
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute ProductSalesDealCondition productSalesDealCondition,
            Model model) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        if (condition != null && condition.length() > 0) {
            productSalesDealCondition = gson.fromJson(condition, ProductSalesDealCondition.class);
        }
        Searchable searchable = Searchable.newSearchable();
        searchable.addSearchFilters(productSalesDealCondition.getAndFilters());
        searchable.setPage(pageCurrent, pageSize);
        model.addAttribute("condition", gson.toJson(productSalesDealCondition));
        model.addAttribute("buyTime", productSalesDealCondition.getBuyTime());
        model.addAttribute("productId", productSalesDealCondition.getProductId());
        model.addAttribute("page", currentBuyLogService.findAll(searchable));
        return "product/productsales/currentdetail";
    }

    /**
     * 定存宝购买详细
     * @param pageSize
     * @param pageCurrent
     * @param condition
     * @param productSalesDealCondition
     * @param model
     * @return
     */
    @RequestMapping(value = "regularDetail", method = { RequestMethod.GET, RequestMethod.POST })
    @RequiresPermissions("productsales_list")
    public String regularPageQuery(
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute ProductSalesDealCondition productSalesDealCondition,
            Model model) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        if (condition != null && condition.length() > 0) {
            productSalesDealCondition = gson.fromJson(condition, ProductSalesDealCondition.class);
        }
        Searchable searchable = Searchable.newSearchable();
        searchable.addSearchFilters(productSalesDealCondition.getAndFilters());
        searchable.setPage(pageCurrent, pageSize);
        model.addAttribute("condition", gson.toJson(productSalesDealCondition));
        model.addAttribute("buyTime", productSalesDealCondition.getBuyTime());
        model.addAttribute("productId", productSalesDealCondition.getProductId());
        model.addAttribute("page", regularBuyLogService.findAll(searchable));
        return "product/productsales/regulardetail";
    }
}