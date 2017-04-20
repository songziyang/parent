package com.ydzb.web.redpacket.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.packet.entity.ExpMoney;
import com.ydzb.packet.entity.RedPacketInterest;
import com.ydzb.packet.service.IExpMoneyService;
import com.ydzb.web.redpacket.condition.ExpMoneyCondition;
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
 * 体验金控制层
 * @author sy
 */
@Controller
@RequestMapping("redpacket/expmoney")
public class ExpMoneyController extends BaseController {

    @Autowired
    private IExpMoneyService expMoneyService;

    /**
     * 列表
     * @param pageSize
     * @param pageCurrent
     * @param condition
     * @param expMoneyCondition
     * @param model
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("expmoney_list")
    public String queryPage(@RequestParam(defaultValue = "0") int pageSize,
            @RequestParam(defaultValue = "0") int pageCurrent,
            @ModelAttribute("condition") String condition,
            @ModelAttribute("expMoneyCondition") ExpMoneyCondition expMoneyCondition,
            Model model) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        if (condition != null && condition.length() > 0) {
            expMoneyCondition = gson.fromJson(condition, ExpMoneyCondition.class);
        }
        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        searchable.addSort(new Sort(Sort.Direction.DESC, "created"));
        // 添加查询条件
        searchable.addSearchFilters(expMoneyCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        model.addAttribute("condition", gson.toJson(expMoneyCondition));
        model.addAttribute("page", expMoneyService.findAll(searchable));
        return "redpacket/expmoney/list";
    }

    /**
     * 创建
     * @param model
     * @return
     */
    @RequestMapping(value = "create", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("expmoney_create")
    public String create(Model model) {
        return "redpacket/expmoney/edit";
    }

    /**
     * 保存
     * @param expMoney
     * @param session
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @RequiresPermissions(value = { "expmoney_create", "expmoney_edit" }, logical = Logical.OR)
    public String save(ExpMoney expMoney, HttpSession session, Model model) throws Exception {
        String result = expMoneyService.saveOne(expMoney);
        if (StringUtils.isEmpty(result)) {
            session.setAttribute("message", SAVE_SUCCESS);
            return "redirect:/redpacket/expmoney/list";
        } else {
            session.setAttribute("error", result);
            model.addAttribute("expMoney", expMoney);
            return "redpacket/expmoney/edit";
        }
    }

    /**
     * 编辑
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "edit/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("expmoney_edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("expMoney", expMoneyService.findOne(id));
        return "redpacket/expmoney/edit";
    }

    /**
     * 停用
     * @param id
     * @param session
     * @return
     */
    @RequestMapping(value = "disable/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("expmoney_del")
    public String disable(@PathVariable Long id, HttpSession session) {
        expMoneyService.disable(id);
        session.setAttribute("message", "停用成功");
        return "redirect:/redpacket/expmoney/list";
    }

    /**
     * 启用
     * @param id
     * @param session
     * @return
     */
    @RequestMapping(value = "enable/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("expmoney_del")
    public String enable(@PathVariable Long id, HttpSession session) {
        expMoneyService.enable(id);
        session.setAttribute("message", "启用成功");
        return "redirect:/redpacket/expmoney/list";
    }
}