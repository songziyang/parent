package com.ydzb.web.product.controller;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.core.entity.search.filter.SearchFilter;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import com.ydzb.product.entity.ProductInfo;
import com.ydzb.product.service.ICurrentQueueService;
import com.ydzb.product.service.IProductInfoService;
import com.ydzb.product.service.IProductTypeService;
import com.ydzb.web.product.condition.ProductInfoCondition;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 产品信息控制层
 *
 * @author sy
 */
@Controller
@RequestMapping("product/productInfo")
public class ProductInfoController extends BaseController {

    @Autowired
    private IProductInfoService productInfoService;
    @Autowired
    private IProductTypeService productTypeService;
    @Autowired
    private ICurrentQueueService currentQueueService;

    /**
     * 列表
     *
     * @param pageSize
     * @param pageCurrent
     * @param condition
     * @param productInfoCondition
     * @param model
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("productinfo_list")
    public String queryPage(@RequestParam(defaultValue = "0") int pageSize,
                            @RequestParam(defaultValue = "0") int pageCurrent,
                            @ModelAttribute("condition") String condition,
                            @ModelAttribute("productInfoCondition") ProductInfoCondition productInfoCondition, Model model) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        if (condition != null && condition.length() > 0) {
            productInfoCondition = gson.fromJson(condition, ProductInfoCondition.class);
        }
        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        searchable.addSort(new Sort(Sort.Direction.DESC, "created"));
        // 添加查询条件
        searchable.addSearchFilters(productInfoCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        model.addAttribute("condition", gson.toJson(productInfoCondition));
        model.addAttribute("page", productInfoService.findAll(searchable));
        return "product/productinfo/list";
    }

    /**
     * 创建
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "create", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("productinfo_create")
    public String create(Model model) {
        Searchable searchable = Searchable.newSearchable();
        searchable.addSort(new Sort(Sort.Direction.ASC, "id"));
        List<SearchFilter> filters = Lists.newArrayList();
        filters.add(SearchFilterHelper.newCondition("id", SearchOperator.gt, 1));
        searchable.addSearchFilters(filters);
        //产品类别列表
        model.addAttribute("productTypes", productTypeService.findAllWithSort(searchable));
        model.addAttribute("currentPrepayCopies", currentQueueService.querySumCopies());
        return "product/productinfo/edit";
    }

    /**
     * 保存
     *
     * @param productInfo
     * @param session
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @RequiresPermissions(value = {"productinfo_create", "productinfo_edit"}, logical = Logical.OR)
    public String save(ProductInfo productInfo, HttpSession session, Model model) throws Exception {
        String result = productInfoService.saveOne(productInfo);
        if (StringUtils.isEmpty(result)) {
            session.setAttribute("message", SAVE_SUCCESS);
            return "redirect:/product/productInfo/list";
        } else {
            session.setAttribute("error", result);
            model.addAttribute("productInfo", productInfo);
            model.addAttribute("productTypes", productTypeService.findAll(new Sort(Sort.Direction.ASC, "id")));
            return "product/productinfo/edit";
        }
    }

    /**
     * 编辑
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "edit/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("productinfo_edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("productInfo", productInfoService.findOne(id));
        model.addAttribute("productTypes", productTypeService.findAll(new Sort(Sort.Direction.ASC, "id")));
        //model.addAttribute("currentPrepayCopies", currentQueueService.querySumCopies());
        return "product/productinfo/copy";
    }

    /**
     * 清空活期宝剩余份数
     *
     * @param id
     * @param session
     * @return
     */
    @RequestMapping(value = "emptyCurrent/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("productinfo_edit")
    public String emptyCurrent(@PathVariable Long id, HttpSession session) {
        session.setAttribute("message", productInfoService.emptyCurrent(id));
        return "redirect:/product/productInfo/list";
    }

    /**
     * 删除
     *
     * @param id
     * @param session
     * @return
     */
    @RequestMapping(value = "delete/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("productinfo_del")
    public String delete(@PathVariable Long id, HttpSession session) {
        productInfoService.delete(id);
        session.setAttribute("message", "删除成功");
        return "redirect:/product/productInfo/list";
    }

    /**
     * 获得产品名称
     *
     * @param productTypeId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryProductName/{productTypeId}", method = RequestMethod.GET)
    @RequiresPermissions(value = {"productinfo_create", "productinfo_edit"}, logical = Logical.OR)
    public String getProductName(@PathVariable Long productTypeId) {
        return productInfoService.queryProductName(productTypeId);
    }
}