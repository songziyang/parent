package com.ydzb.web.redpacket.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.packet.entity.Integral;
import com.ydzb.packet.service.IIntegralService;
import com.ydzb.web.redpacket.condition.IntegralCondition;
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
 * 积分管理controller
 */
@Controller
@RequestMapping("redpacket/integral")
public class IntegralController extends BaseController {

    @Autowired
    private IIntegralService integralService;

    /**
     * 分页列表
     * @param pageSize
     * @param pageCurrent
     * @param condition
     * @param integralCondition
     * @param model
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("integral_list")
    public String queryPage(@RequestParam(defaultValue = "0") int pageSize,
            @RequestParam(defaultValue = "0") int pageCurrent,
            @ModelAttribute("condition") String condition, Model model,
            @ModelAttribute("integralCondition") IntegralCondition integralCondition) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        if (StringUtils.isNotEmpty(condition)) {
            integralCondition = gson.fromJson(condition, IntegralCondition.class);
        }
        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        searchable.addSort(new Sort(Sort.Direction.DESC, "created"));
        // 添加查询条件
        searchable.addSearchFilters(integralCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        model.addAttribute("condition", gson.toJson(integralCondition));
        model.addAttribute("page", integralService.findAll(searchable));
        return "redpacket/integral/list";
    }

    /**
     * 创建
     * @return
     */
    @RequestMapping(value = "create", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("integral_create")
    public String create() {
        return "redpacket/integral/edit";
    }

    /**
     * 保存
     * @param session
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @RequiresPermissions(value = { "integral_create", "integral_edit" }, logical = Logical.OR)
    public String save(Integral integral, HttpSession session, Model model) throws Exception {
        integral = integralService.saveOne(integral);
        if (integral != null) {
            session.setAttribute("message", SAVE_SUCCESS);
            return "redirect:/redpacket/integral/list";
        } else {
            session.setAttribute("error", "保存失败!");
            model.addAttribute("integral", integral);
            return "redpacket/integral/edit";
        }
    }

    /**
     * 编辑
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "edit/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("integral_edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("integral", integralService.findOne(id));
        return "redpacket/integral/edit";
    }

    /**
     * 停用
     * @param id
     * @param session
     * @return
     */
    @RequestMapping(value = "disable/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("integral_del")
    public String disable(@PathVariable Long id, HttpSession session) {
        integralService.disable(id);
        session.setAttribute("message", "停用成功");
        return "redirect:/redpacket/integral/list";
    }

    /**
     * 启用
     * @param id
     * @param session
     * @return
     */
    @RequestMapping(value = "enable/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("integral_del")
    public String enable(@PathVariable Long id, HttpSession session) {
        integralService.enable(id);
        session.setAttribute("message", "启用成功");
        return "redirect:/redpacket/integral/list";
    }
}