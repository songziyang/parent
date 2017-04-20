package com.ydzb.web.product.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.product.entity.ProductType;
import com.ydzb.product.service.IProductTypeService;
import com.ydzb.web.product.condition.ProductTypeCondition;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 产品类别控制层
 */
@Controller
@RequestMapping("product/productType")
public class ProductTypeController extends BaseController {

    @Autowired
    private IProductTypeService productTypeService;

    /**
     * 列表
     * @param pageSize
     * @param pageCurrent
     * @param condition
     * @param productTypeCondition
     * @param model
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("producttype_list")
    public String queryPage(@RequestParam(defaultValue = "0") int pageSize,
            @RequestParam(defaultValue = "0") int pageCurrent,
            @ModelAttribute("condition") String condition,
            @ModelAttribute("productTypeCondition") ProductTypeCondition productTypeCondition, Model model) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        if (StringUtils.isNotEmpty(condition)) {
            productTypeCondition = gson.fromJson(condition, ProductTypeCondition.class);
        }
        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        searchable.addSort(new Sort(Sort.Direction.ASC, "id"));
        // 添加查询条件
        searchable.addSearchFilters(productTypeCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        model.addAttribute("condition", gson.toJson(productTypeCondition));
        model.addAttribute("page", productTypeService.findAll(searchable));
        return "product/producttype/list";
    }

    /**
     * 创建
     * @return
     */
    @RequestMapping(value = "create", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("producttype_create")
    public String create() {
        return "product/producttype/edit";
    }

    /**
     * 保存
     * @param productType
     * @param session
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @RequiresPermissions(value = { "producttype_create", "producttype_edit" }, logical = Logical.OR)
    public String save(ProductType productType, HttpSession session, Model model) throws Exception {
        String result = productTypeService.saveOne(productType);
        if (StringUtils.isEmpty(result)) {
            session.setAttribute("message", SAVE_SUCCESS);
            return "redirect:/product/productType/list";
        } else {
            session.setAttribute("error", result);
            model.addAttribute("productType", productType);
            return "product/producttype/edit";
        }
    }

    /**
     * 编辑
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "edit/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("producttype_edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("productType", productTypeService.findOne(id));
        return "product/producttype/edit";
    }

    /**
     * 删除
     * @param id
     * @param session
     * @return
     */
    @RequestMapping(value = "delete/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("producttype_del")
    public String delete(@PathVariable Long id, HttpSession session) {
        productTypeService.delete(id);
        session.setAttribute("message", "删除成功");
        return "redirect:/product/productType/list";
    }
}