package com.ydzb.web.redpacket.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.packet.entity.UserIntegralExchange;
import com.ydzb.packet.service.IUserIntegralExchangeService;
import com.ydzb.sms.service.ISmsHandleService;
import com.ydzb.web.redpacket.condition.SilverExchangeCondition;
import com.ydzb.web.redpacket.condition.UserIntegralExchangeCondition;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("redpacket/userintegral")
public class UserIntegralExchangeController extends BaseController {

    @Autowired
    private IUserIntegralExchangeService userIntegralExchangeService;
    @Autowired
    private ISmsHandleService smsHandleService;

    @RequestMapping(value = "listUserIntegral", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("userintegral_list")
    public String pageQuery(
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute UserIntegralExchangeCondition userIntegralExchangeCondition, Model model) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        if (condition != null && condition.length() > 0) {
            userIntegralExchangeCondition = gson.fromJson(condition, UserIntegralExchangeCondition.class);
        }

        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        // 添加查询条件
        searchable.addSearchFilters(userIntegralExchangeCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        // 设置排序条件
        searchable.addSort(new Sort(Sort.Direction.DESC, "created"));
        model.addAttribute("condition", gson.toJson(userIntegralExchangeCondition));
        model.addAttribute("page", userIntegralExchangeService.findAll(searchable));
        model.addAttribute("status", userIntegralExchangeCondition.getStatus() == null? 1: userIntegralExchangeCondition.getStatus());
        return "redpacket/userintegral/list";
    }


    @RequestMapping(value = "auditSuccess/{id}/{status}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("userintegral_deal")
    public String auditSuccess(@PathVariable Long id, @PathVariable Integer status, HttpSession session) {
        try {
            userIntegralExchangeService.updateExchange(id, status);
            if (status == 2) {
                UserIntegralExchange userIntegralExchange = userIntegralExchangeService.findOne(id);
                smsHandleService.sendUserSms("send_delivery", userIntegralExchange.getUser().getMobile(), "name:" + userIntegralExchange.getUser().getUsername());
            }
            session.setAttribute("message", "操作成功!");
        } catch (Exception e) {
            session.setAttribute("error", "操作失败！");
            e.printStackTrace();
        }
        return "redirect:/redpacket/userintegral/listUserIntegral";
    }

    /**
     * 订单取消
     * @param id
     * @param session
     * @return
     */
    @RequestMapping(value = "auditFailure/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("userintegral_deal")
    public String auditFailure(@PathVariable Long id, HttpSession session) {
        try {
            userIntegralExchangeService.auditFailure(id);
            session.setAttribute("message", "操作成功!");
        } catch (Exception e) {
            session.setAttribute("error", "操作失败！");
            e.printStackTrace();
        }
        return "redirect:/redpacket/userintegral/listUserIntegral";
    }

    /**
     * 重置为待处理
     * @param id
     * @param session
     * @return
     */
    @RequestMapping(value = "underhandle/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("userintegral_deal")
    public String resetToUnderHandle(@PathVariable Long id, HttpSession session) {
        try {
            userIntegralExchangeService.resetToUnderHandle(id);
            session.setAttribute("message", "操作成功!");
        } catch (Exception e) {
            session.setAttribute("error", "操作失败！");
            e.printStackTrace();
        }
        return "redirect:/redpacket/userintegral/listUserIntegral";
    }


    /**
     * 确认收货/确认收货
     * @param status 状态 2:已发货 3:确认收货
     * @param ids
     * @param session
     * @return
     */
    @RequestMapping(value = "auditSuccess/{status}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("userintegral_deal")
    public String auditSuccess(@PathVariable Integer status, Long[] ids, HttpSession session) {
        try {
            userIntegralExchangeService.updateExchange(ids, status);
            if (status == 2) {
                for (Long id: ids) {
                    UserIntegralExchange userIntegralExchange = userIntegralExchangeService.findOne(id);
                    smsHandleService.sendUserSms("send_delivery", userIntegralExchange.getUser().getMobile(), "name:" + userIntegralExchange.getUser().getUsername());
                }
            }
            session.setAttribute("message", "操作成功!");
        } catch (Exception e) {
            session.setAttribute("error", "操作失败！");
            e.printStackTrace();
        }
        return "redirect:/redpacket/userintegral/listUserIntegral";
    }

    @RequestMapping(value = "exportExcel/{condition}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("userintegral_list")
    public String exportExcel(
            @PathVariable(value = "condition") String condition,
            UserIntegralExchangeCondition userIntegralExchangeCondition, Model model,
            HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String logoRealPathDir = request.getSession().getServletContext().getRealPath("/static/download");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (condition != null && condition.length() > 0) {
            userIntegralExchangeCondition = gson.fromJson(condition, UserIntegralExchangeCondition.class);
        }
        Map<String, Object> filter = userIntegralExchangeCondition.getSqlFilters();
        List<Object[]> list = userIntegralExchangeService.findExportData(filter);
        String fileName = userIntegralExchangeService.exportExcel(list, logoRealPathDir);
        redirectAttributes.addFlashAttribute("fileName", fileName);
        return "redirect:/redpacket/userintegral/listUserIntegral";
    }


    public IUserIntegralExchangeService getUserIntegralExchangeService() {
        return userIntegralExchangeService;
    }

    public void setUserIntegralExchangeService(IUserIntegralExchangeService userIntegralExchangeService) {
        this.userIntegralExchangeService = userIntegralExchangeService;
    }

    public ISmsHandleService getSmsHandleService() {
        return smsHandleService;
    }

    public void setSmsHandleService(ISmsHandleService smsHandleService) {
        this.smsHandleService = smsHandleService;
    }
}