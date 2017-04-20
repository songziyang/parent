package com.ydzb.web.redpacket.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.packet.entity.RedPacketCash;
import com.ydzb.packet.service.IRedPacketCashService;
import com.ydzb.web.redpacket.condition.RedPacketCashCondition;
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
 * 红包-现金控制层
 * @author sy
 */
@Controller
@RequestMapping("redpacket/redpacketCash")
public class RedPacketCashController extends BaseController {

    @Autowired
    private IRedPacketCashService cashService;

    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("rpcash_list")
    public String queryPage(@RequestParam(defaultValue = "0") int pageSize,
            @RequestParam(defaultValue = "0") int pageCurrent,
            @ModelAttribute("condition") String condition,
            @ModelAttribute("redPacketCashCondition") RedPacketCashCondition cashCondition,
            Model model) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        if (condition != null && condition.length() > 0) {
            cashCondition = gson.fromJson(condition, RedPacketCashCondition.class);
        }
        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        searchable.addSort(new Sort(Sort.Direction.DESC, "created"));
        // 添加查询条件
        searchable.addSearchFilters(cashCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        model.addAttribute("condition", gson.toJson(cashCondition));
        model.addAttribute("page", cashService.findAll(searchable));
        return "redpacket/redpacketcash/list";
    }

    /**
     * 创建
     * @param model
     * @return
     */
    @RequestMapping(value = "create", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("rpcash_create")
    public String create(Model model) {
        return "redpacket/redpacketcash/edit";
    }

    /**
     * 保存
     * @param cash
     * @param aBeginTime
     * @param aFinishTime
     * @param session
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @RequiresPermissions(value = { "rpcash_create", "rpcash_edit" }, logical = Logical.OR)
    public String save(RedPacketCash cash, String aBeginTime, String aFinishTime,
                       HttpSession session, Model model) throws Exception {
        String result = cashService.saveOne(cash, aBeginTime, aFinishTime);
        if (StringUtils.isEmpty(result)) {
            session.setAttribute("message", SAVE_SUCCESS);
            return "redirect:/redpacket/redpacketCash/list";
        } else {
            session.setAttribute("error", result);
            model.addAttribute("redpacketCash", cash);
            return "redpacket/redpacketCash/edit";
        }
    }

    /**
     * 编辑
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "edit/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("rpcash_edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("redpacketCash", cashService.findOne(id));
        return "redpacket/redpacketcash/edit";
    }

    /**
     * 停用
     * @param id
     * @param session
     * @return
     */
    @RequestMapping(value = "disable/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("rpcash_del")
    public String disable(@PathVariable Long id, HttpSession session) {
        cashService.disable(id);
        session.setAttribute("message", "停用成功");
        return "redirect:/redpacket/redpacketCash/list";
    }

    /**
     * 启用
     * @param id
     * @param session
     * @return
     */
    @RequestMapping(value = "enable/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("rpcash_del")
    public String enable(@PathVariable Long id, HttpSession session) {
        cashService.enable(id);
        session.setAttribute("message", "启用成功");
        return "redirect:/redpacket/redpacketCash/list";
    }
}