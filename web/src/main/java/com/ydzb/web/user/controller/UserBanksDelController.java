package com.ydzb.web.user.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.account.service.IWmInfoSmsTimerService;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.message.service.IInfoTemplateService;
import com.ydzb.packet.entity.SilverUser;
import com.ydzb.packet.service.ISilverExchangeService;
import com.ydzb.packet.service.IUserIntegralRecordService;
import com.ydzb.sms.service.ISmsHandleService;
import com.ydzb.user.entity.User;
import com.ydzb.user.entity.UserBanks;
import com.ydzb.user.entity.UserBanksDel;
import com.ydzb.user.entity.UserInfo;
import com.ydzb.user.service.*;
import com.ydzb.web.user.condition.UserBanksDelCondition;
import com.ydzb.web.user.condition.UserCondition;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value = "userinfo/banksDel")
public class UserBanksDelController extends BaseController {

    @Autowired
    private IUserBanksDelService userBanksDelService;
    @Autowired
    private ISmsHandleService smsHandleService;
    @Autowired
    private IUserBanksService userBanksService;

    /**
     * 分页列表
     * @param pageSize 每页显示条数
     * @param pageCurrent 当前页数(从0开始)
     * @param condition 查询条件json
     * @param userBanksDelCondition 查询条件
     * @param model
     * @return
     */
    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("userbanksdel_list")
    public String pageQuery(@RequestParam(defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(defaultValue = PAGE_CURRENT) int pageCurrent, final String condition, final Model model,
            @ModelAttribute UserBanksDelCondition userBanksDelCondition) {
        final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (StringUtils.isNotEmpty(condition)) {
            userBanksDelCondition = gson.fromJson(condition, UserBanksDelCondition.class);
        }
        // 创建查询
        final Searchable searchable = Searchable.newSearchable();
        // 添加查询条件
        searchable.addSearchFilters(userBanksDelCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        // 设置排序条件
        searchable.addSort(new Sort(Direction.DESC, "created"));

        model.addAttribute("condition", gson.toJson(userBanksDelCondition));
        model.addAttribute("page", userBanksDelService.findAll(searchable));
        model.addAttribute("banksDelStatus", userBanksDelCondition.getStatus());
        return "userinfo/banksdel/list";
    }

    /**
     * 详情
     * @param id 用户银行解绑id
     * @param model
     * @return
     */
    @RequestMapping(value = "detail/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("userbanksdel_detail")
    public String detail(@PathVariable final Long id, final Model model) {
        model.addAttribute("banksDel", userBanksDelService.findOne(id));
        model.addAttribute("userBanks", userBanksService.findByBanksDel(id));
        return "userinfo/banksdel/detail";
    }

    /**
     * 审核操作
     * @param operation success-操作成功    failure-操作失败
     * @param reason 拒绝原因
     * @param banksDelId 解绑id
     * @return
     */
    @RequestMapping(value = "validate/{operation}", method = {RequestMethod.POST})
    @RequiresPermissions("userbanksdel_validate")
    public String validate(@PathVariable String operation, final String reason, final Long banksDelId,
               final HttpSession session) throws Exception {
        userBanksDelService.validate(operation, reason, banksDelId);
        //没把发短信/站内信放入userBanksDelService.validate()中因为maven 循环依赖(circle dependency);
        final User user = userBanksDelService.findUserByBanksDel(banksDelId);
        if ("success".equals(operation)) {
            //站内信
            smsHandleService.addSiteContent("banksdel_validate_success",
                    user.getId(), "银行卡解绑成功", "name:" + user.getUsername(), 0);
            //发短信
            smsHandleService.sendUserSms("banksdel_validate_success", user.getMobile(), "name:" + user.getUsername());
        } else if ("failure".equals(operation)) {
            //站内信
            smsHandleService.addSiteContent("banksdel_validate_failure",
                    user.getId(), "银行卡解绑失败", "name:" + user.getUsername() + ",reason:" + reason, 0);
            //发短信
            smsHandleService.sendUserSms("banksdel_validate_failure",
                    user.getMobile(), "name:" + user.getUsername() + ",reason:" + reason);
        }
        session.setAttribute("message", "操作成功! ");
        return "redirect:/userinfo/banksDel/list";
    }
}