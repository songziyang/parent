package com.ydzb.web.redpacket.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.packet.entity.SilverProduct;
import com.ydzb.packet.service.ISilverProductService;
import com.ydzb.web.redpacket.condition.SilverProductCondition;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("redpacket/silver")
public class SilverProductController extends BaseController {


    @Autowired
    private ISilverProductService silverProductService;

    /**
     * @param pageSize
     * @param pageCurrent
     * @param condition
     * @param silverProductCondition
     * @param model
     * @return
     */
    @RequestMapping(value = "listSilver", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("silver_list")
    public String pageQuery(
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute SilverProductCondition silverProductCondition, Model model) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        if (condition != null && condition.length() > 0) {
            silverProductCondition = gson.fromJson(condition, SilverProductCondition.class);
        }

        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        // 添加查询条件
        searchable.addSearchFilters(silverProductCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        // 设置排序条件
        searchable.addSort(new Sort(Sort.Direction.DESC, "id"));
        model.addAttribute("condition", gson.toJson(silverProductCondition));
        model.addAttribute("page", silverProductService.findAll(searchable));
        return "redpacket/silver/list";
    }


    // 添加
    @RequestMapping(value = "createSilver", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("silver_create")
    public String createSilver(Model model) {

        return "redpacket/silver/edit";
    }

    // 保存
    @RequestMapping(value = "saveSilver", method = RequestMethod.POST)
    @RequiresPermissions(value = {"silver_create", "silver_edit"}, logical = Logical.OR)
    public String saveAdmin(SilverProduct silverProduct, HttpSession session, Model model) throws Exception {
        silverProductService.saveSilver(silverProduct);
        session.setAttribute("message", SAVE_SUCCESS);
        return "redirect:/redpacket/silver/listSilver";


    }

    // 删除
    @RequestMapping(value = "deleteSilver/{id}", method = {RequestMethod.GET,
            RequestMethod.POST})
    @RequiresPermissions("silver_del")
    public String deleteAdmin(@PathVariable("id") Long id, HttpSession session)
            throws Exception {
        silverProductService.deleteSilver(id);
        session.setAttribute("message", DELETE_SUCCESS);
        return "redirect:/redpacket/silver/listSilver";
    }

    // 批量删除
    @RequestMapping(value = "deleteSilvers", method = {RequestMethod.GET,
            RequestMethod.POST})
    @RequiresPermissions("silver_del")
    public String deleteAdmins(Long[] ids, HttpSession session)
            throws Exception {
        silverProductService.deleteSilver(ids);
        session.setAttribute("message", DELETE_SUCCESS);
        return "redirect:/redpacket/silver/listSilver";
    }

    // 编辑
    @RequestMapping(value = "editSilver/{id}", method = {RequestMethod.GET,RequestMethod.POST})
    @RequiresPermissions("silver_edit")
    public String editSilver(@PathVariable("id") Long id, Model model) {
        model.addAttribute("silver", silverProductService.findOne(id));
        return "redpacket/silver/edit";
    }


    public ISilverProductService getSilverProductService() {
        return silverProductService;
    }

    public void setSilverProductService(ISilverProductService silverProductService) {
        this.silverProductService = silverProductService;
    }
}