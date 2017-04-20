package com.ydzb.web.product.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.product.entity.Structure;
import com.ydzb.product.service.IStructureService;
import com.ydzb.web.product.condition.StructureCondition;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 产品信息控制层
 *
 * @author sy
 */
@Controller
@RequestMapping("product/structure")
public class StructureController extends BaseController {

    @Autowired
    private IStructureService structureService;

    /**
     * 列表
     *
     * @param pageSize
     * @param pageCurrent
     * @param condition
     * @param structureCondition
     * @param model
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("structure_list")
    public String queryPage(@RequestParam(defaultValue = PAGE_SIZE) int pageSize,
                            @RequestParam(defaultValue = PAGE_CURRENT) int pageCurrent,
                            @ModelAttribute("condition") String condition,
                            @ModelAttribute("structureCondition") StructureCondition structureCondition, Model model) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (condition != null && condition.length() > 0) {
            structureCondition = gson.fromJson(condition, StructureCondition.class);
        }

        //创建查询
        Searchable searchable = Searchable.newSearchable();
        //添加查询条件
        searchable.addSearchFilters(structureCondition.getAndFilters());
        //设置排序条件
        searchable.addSort(new Sort(Sort.Direction.DESC, "created"));
        //设置分页参数
        searchable.setPage(pageCurrent, pageSize);

        model.addAttribute("condition", gson.toJson(structureCondition));
        model.addAttribute("page", structureService.findAll(searchable));

        return "product/structure/list";
    }

    /**
     * 创建
     *
     * @return
     */
    @RequestMapping(value = "create", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("structure_create")
    public String create(Model model) {
        sendDefaultValuesToFront(model);
        return "product/structure/edit";
    }

    /**
     * 保存
     *
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @RequiresPermissions(value = {"structure_create", "structure_edit"}, logical = Logical.OR)
    public String save(Structure structure, HttpSession session, String startDates, String endDates) throws Exception {
        try {
            structureService.saveOne(structure, startDates, endDates);
            session.setAttribute("message", SAVE_SUCCESS);
            return "redirect:/product/structure/list";
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("message", SAVE_SUCCESS);
            return (structure == null || structure.getId() == null) ? "redirect:/product/structure/create" : "redirect:/product/structure/edit/" + structure.getId();
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
    @RequiresPermissions("structure_create")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("structure", structureService.findOne(id));
        sendDefaultValuesToFront(model);
        return "product/structure/edit";
    }

    private void sendDefaultValuesToFront(Model model) {
        model.addAttribute("defaultMaxCopies", Structure.MAX_COPIES_DEFAULT);
        model.addAttribute("defaultMinCopies", Structure.MIN_COPIES_DEFAULT);
        model.addAttribute("defaultVipMaxCopies", Structure.VIP_MAX_COPIES_DEFAULT);
        model.addAttribute("defaultDays", Structure.DAYS_DEFAULT);
        model.addAttribute("defaultHelpMaxApr", Structure.HELP_MAX_APR_DEFAULT);
        model.addAttribute("defaultApr", Structure.APR_DEFAULT);
    }
}