package com.ydzb.web.redpacket.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.packet.entity.RedPacketCash;
import com.ydzb.packet.entity.RedPacketVoucher;
import com.ydzb.packet.service.IRedPacketCashService;
import com.ydzb.packet.service.IRedpacketVoucherService;
import com.ydzb.web.redpacket.condition.RedPacketCashCondition;
import com.ydzb.web.redpacket.condition.RedPacketVoucherCondition;
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
 * 代金券控制层
 * @author sy
 */
@Controller
@RequestMapping("redpacket/redpacketVoucher")
public class RedPacketVoucherController extends BaseController {

    @Autowired
    private IRedpacketVoucherService voucherService;

    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("rpvoucher_list")
    public String queryPage(@RequestParam(defaultValue = "0") int pageSize,
            @RequestParam(defaultValue = "0") int pageCurrent,
            @ModelAttribute("condition") String condition,
            @ModelAttribute("voucherCondition") RedPacketVoucherCondition voucherCondition,
            Model model) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        if (condition != null && condition.length() > 0) {
            voucherCondition = gson.fromJson(condition, RedPacketVoucherCondition.class);
        }
        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        searchable.addSort(new Sort(Sort.Direction.DESC, "created"));
        // 添加查询条件
        searchable.addSearchFilters(voucherCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        model.addAttribute("condition", gson.toJson(voucherCondition));
        model.addAttribute("page", voucherService.findAll(searchable));
        return "redpacket/redpacketvoucher/list";
    }

    /**
     * 创建
     * @param model
     * @return
     */
    @RequestMapping(value = "create", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("rpvoucher_create")
    public String create(Model model) {
        return "redpacket/redpacketvoucher/edit";
    }

    /**
     * 保存
     * @param voucher
     * @param aBeginTime
     * @param aFinishTime
     * @param session
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @RequiresPermissions(value = { "rpvoucher_create", "rpvoucher_edit" }, logical = Logical.OR)
    public String save(RedPacketVoucher voucher, String aBeginTime, String aFinishTime,Integer[] productDaysArr,
                       HttpSession session, Model model) throws Exception {
        String result = voucherService.saveOne(voucher, aBeginTime, aFinishTime,productDaysArr);
        if (StringUtils.isEmpty(result)) {
            session.setAttribute("message", SAVE_SUCCESS);
            return "redirect:/redpacket/redpacketVoucher/list";
        } else {
            session.setAttribute("error", result);
            model.addAttribute("voucher", voucher);
            return "redpacket/redpacketVoucher/edit";
        }
    }


    /**
     * 编辑
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "edit/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("rpvoucher_edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("voucher", voucherService.findOne(id));
        return "redpacket/redpacketvoucher/edit";
    }

    /**
     * 停用
     * @param id
     * @param session
     * @return
     */
    @RequestMapping(value = "disable/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("rpvoucher_del")
    public String disable(@PathVariable Long id, HttpSession session) {
        voucherService.disable(id);
        session.setAttribute("message", "停用成功");
        return "redirect:/redpacket/redpacketVoucher/list";
    }

    /**
     * 启用
     * @param id
     * @param session
     * @return
     */
    @RequestMapping(value = "enable/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("rpvoucher_del")
    public String enable(@PathVariable Long id, HttpSession session) {
        voucherService.enable(id);
        session.setAttribute("message", "启用成功");
        return "redirect:/redpacket/redpacketVoucher/list";
    }
}