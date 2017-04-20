package com.ydzb.web.user.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.packet.entity.SilverUser;
import com.ydzb.packet.service.ISilverExchangeService;
import com.ydzb.packet.service.IUserIntegralRecordService;
import com.ydzb.product.entity.ActivityQingming;
import com.ydzb.product.service.IActivityQingmingService;
import com.ydzb.sms.service.ISmsHandleService;
import com.ydzb.user.entity.User;
import com.ydzb.user.entity.UserBanks;
import com.ydzb.user.entity.UserInfo;
import com.ydzb.user.service.IUserBanksService;
import com.ydzb.user.service.IUserInfoService;
import com.ydzb.user.service.IUserService;
import com.ydzb.user.service.IVipGradeService;
import com.ydzb.web.user.condition.UserCondition;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
@RequestMapping(value = "userinfo/user")
public class UserController extends BaseController {


    @Autowired
    private IUserService userService;
    @Autowired
    private IVipGradeService vipGradeService;
    @Autowired
    private ISmsHandleService smsHandleService;
    @Autowired
    private IUserBanksService userBanksService;
    @Autowired
    private ISilverExchangeService silverExchangeService;
    @Autowired
    private IUserInfoService userInfoService;
    @Autowired
    private IUserIntegralRecordService userIntegralRecordService;
    @Autowired
    private IActivityQingmingService activityQingmingService;

    /**
     * 分页查询 @RequestParam 设置pageSize pageCurrent 默认值
     *
     * @param pageSize      每页显示数量
     * @param pageCurrent   当前页码
     * @param userCondition 查询条件
     * @param model
     * @return
     */
    @RequestMapping(value = "listUser", method = {RequestMethod.GET,
            RequestMethod.POST})
    @RequiresPermissions("user_list")
    public String pageQuery(
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute UserCondition userCondition, Model model) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        if (condition != null && condition.length() > 0) {
            userCondition = gson.fromJson(condition, UserCondition.class);
        }

        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        // 添加查询条件
        searchable.addSearchFilters(userCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        // 设置排序条件
        searchable.addSort(new Sort(Direction.DESC, "created"));

        model.addAttribute("vipGrades", vipGradeService.findAll(new Sort(Sort.Direction.ASC, "id")));
        model.addAttribute("condition", gson.toJson(userCondition));

        model.addAttribute("page", userService.findAll(searchable));

        return "userinfo/user/list";
    }


    // 删除
    @RequestMapping(value = "deleteUser/{id}", method = {RequestMethod.GET,
            RequestMethod.POST})
    @RequiresPermissions("user_del")
    public String deleteRole(@PathVariable("id") Long id, HttpSession session)
            throws Exception {
        userService.deleteUser(id);
        session.setAttribute("message", DELETE_SUCCESS);

        return "redirect:/userinfo/user/listUser";
    }


    //设置手机号
    @RequestMapping(value = "userMobile/{id}", method = RequestMethod.GET)
    @RequiresPermissions("user_mobile")
    public String userSettingMobile(@PathVariable("id") Long id, @RequestParam(required = false) String userMobile,
                                    Model model, HttpSession session) throws Exception {
        User user = userService.findOne(id);
        String message = userService.existUsernameOrMobile(userMobile, id);
        session.setAttribute("message", message);
        return "redirect:/userinfo/user/listUserInfo/" + id;
    }

    //设置微信号
    @RequestMapping(value = "wechatNumber/{id}", method = RequestMethod.GET)
    @RequiresPermissions("user_wechatnumber")
    public String userSettingWechat(@PathVariable("id") Long id, @RequestParam(required = false) String wechatNumber,
                                    Model model, HttpSession session) throws Exception {
        if (StringUtils.isNotEmpty(wechatNumber)) {
            wechatNumber = new String(wechatNumber.getBytes("iso8859-1"), "UTF-8");
        }
        User user = userService.findOne(id);
        String message = userService.setWechatNumber(user, wechatNumber);
        session.setAttribute("message", message);
        return "redirect:/userinfo/user/listUserInfo/" + id;
    }

    //设置qq号
    @RequestMapping(value = "qqNumber/{id}", method = RequestMethod.GET)
    @RequiresPermissions("user_qqnumber")
    public String userSettingQq(@PathVariable("id") Long id, @RequestParam(required = false) String qqNumber,
                                Model model, HttpSession session) throws Exception {
        User user = userService.findOne(id);
        String message = userService.setQqNumber(user, qqNumber);
        session.setAttribute("message", message);
        return "redirect:/userinfo/user/listUserInfo/" + id;
    }


    //设置购买限制
    @RequestMapping(value = "buyLimit/{id}", method = RequestMethod.GET)
    @RequiresPermissions("user_buylimit")
    public String buyLimit(@PathVariable("id") Long id, @RequestParam(required = false) Integer buyLimit, HttpSession session) throws Exception {
        User user = userService.findOne(id);
        String message = userService.setBuyLimit(user, buyLimit);
        session.setAttribute("message", message);
        if (user != null) {
            StringBuffer sb = new StringBuffer();
            sb.append("name:" + user.getUsername());

            if (buyLimit == null || buyLimit == 0) {
                if (user.getUserLeve().getId() > 1) {
                    sb.append(",bvalue:无限额");
                } else {
                    sb.append(",bvalue:" + user.getUserLeve().getBuyLimit().intValue());
                }
            } else {
                sb.append(",bvalue:" + buyLimit);
            }

            if (user.getUserInvestinfo() != null) {
                if (user.getUserInvestinfo().getRedeemLimit() == null || user.getUserInvestinfo().getRedeemLimit() == 0) {
                    sb.append(",svalue:" + user.getUserLeve().getRedeemLimit().intValue());
                } else {
                    sb.append(",svalue:" + user.getUserInvestinfo().getRedeemLimit().intValue());
                }
            }

            smsHandleService.sendUserSms("limit_dayloan", user.getMobile(), sb.toString());
        }
        return "redirect:/userinfo/user/listUserInfo/" + id;
    }


    //设置赎回限制
    @RequestMapping(value = "redeemLimit/{id}", method = RequestMethod.GET)
    @RequiresPermissions("user_redeemlimit")
    public String redeemLimit(@PathVariable("id") Long id, @RequestParam(required = false) Integer redeemLimit, HttpSession session) throws Exception {
        User user = userService.findOne(id);
        String message = userService.setRedeemLimit(user, redeemLimit);
        session.setAttribute("message", message);
        if (user != null) {
            StringBuffer sb = new StringBuffer();
            sb.append("name:" + user.getUsername());
            if (user.getUserInvestinfo() != null) {
                if (user.getUserInvestinfo().getBuyLimit() == null || user.getUserInvestinfo().getBuyLimit() == 0) {
                    if (user.getUserLeve().getId() > 1) {
                        sb.append(",bvalue:无限额");
                    } else {
                        sb.append(",bvalue:" + user.getUserLeve().getBuyLimit().intValue());
                    }
                } else {
                    sb.append(",bvalue:" + user.getUserInvestinfo().getBuyLimit().intValue());
                }
            }

            if (redeemLimit == null || redeemLimit == 0) {
                sb.append(",svalue:" + user.getUserLeve().getRedeemLimit().intValue());
            } else {
                sb.append(",svalue:" + redeemLimit);
            }

            smsHandleService.sendUserSms("limit_dayloan", user.getMobile(), sb.toString());
        }
        return "redirect:/userinfo/user/listUserInfo/" + id;
    }


    //设置推荐人
    @RequestMapping(value = "userRefferral/{id}/{refferralUserId}", method = RequestMethod.GET)
    @RequiresPermissions("user_mobile")
    public String userSettingReferralMobile(@PathVariable("id") Long id, @PathVariable("refferralUserId") Long refferralUserId, Model model, HttpSession session)
            throws Exception {
        User user = userService.findOne(id);
        String message = userService.setRefferralUser(user, refferralUserId);
        session.setAttribute("message", message);
        return "redirect:/userinfo/user/listUserInfo/" + id;
    }

    // 批量删除
    @RequestMapping(value = "deleteUsers", method = {RequestMethod.GET,
            RequestMethod.POST})
    @RequiresPermissions("user_del")
    public String deleteRoles(Long[] ids, HttpSession session) throws Exception {
        userService.deleteUser(ids);
        session.setAttribute("message", DELETE_SUCCESS);
        return "redirect:/userinfo/user/listUser";
    }

    //用户详情
    @RequestMapping(value = "listUserInfo/{id}", method = {RequestMethod.GET,
            RequestMethod.POST})
    @RequiresPermissions("user_listinfo")
    public String listUserInfo(@PathVariable("id") Long id, Model model) {
        model.addAttribute("vipGrades", vipGradeService.findAll(new Sort(Sort.Direction.ASC, "id")));
        model.addAttribute("user", userService.findOne(id));
        List<UserBanks> userCards = userBanksService.queryUserCards(id);
        if (userCards != null && !userCards.isEmpty()) {
            model.addAttribute("userCards", userCards);
        }
        SilverUser silverUser = silverExchangeService.querySilverUserByUserId(id);
        if (silverUser != null) {
            model.addAttribute("sliver", silverUser);
        }

        ActivityQingming activityQingming = activityQingmingService.findByUser(id);
        if (activityQingming != null) {
            model.addAttribute("actQingming", activityQingming);
        }

        return "userinfo/user/listinfo";
    }

    //用户锁定
    @RequestMapping(value = "userLogin/{id}", method = RequestMethod.GET)
    @RequiresPermissions("user_login")
    public String userLogin(@PathVariable("id") Long id, Model model,
                            HttpSession session) {
        User user = userService.findOne(id);
        if (user != null) {
            user.setIsLogin(0);
            user.setLockTime(null);
            userService.update(user);
        }
        session.setAttribute("message", "解锁成功");
        return "redirect:/userinfo/user/listUserInfo/" + id;
    }

    //用户解锁
    @RequestMapping(value = "userUnLogin/{id}", method = RequestMethod.GET)
    @RequiresPermissions("user_unlogin")
    public String userUnLogin(@PathVariable("id") Long id, Model model,
                              HttpSession session) {
        User user = userService.findOne(id);
        if (user != null) {
            user.setIsLogin(-1);
            user.setLockTime(DateUtil.getSystemTimeSeconds());
            userService.update(user);
        }
        session.setAttribute("message", "锁定成功");
        return "redirect:/userinfo/user/listUserInfo/" + id;
    }

    //解锁绑卡
    @RequestMapping(value = "unbindCard/{id}", method = RequestMethod.GET)
    @RequiresPermissions("user_unbindcard")
    public String unbindCard(@PathVariable("id") Long id, Model model,
                             HttpSession session) {
        UserInfo userInfo = userInfoService.findByUserId(id);
        if (userInfo != null) {
            userInfo.setEgg(0);
            userInfoService.update(userInfo);
        }
        session.setAttribute("message", "解锁绑卡成功");
        return "redirect:/userinfo/user/listUserInfo/" + id;
    }

    //身份证验证
    @RequestMapping(value = "validataCard", method = RequestMethod.POST)
    @RequiresPermissions("user_verify")
    public String validataCard(Long userId, String realname, String idCard, Model model, HttpSession session) {
        boolean flag = userService.findUserByIdCard(idCard);
        if (flag) {
            User user = userService.findOne(userId);
            if (user != null) {
                user.setRealName(realname);
                user.setIdCard(idCard);
                userService.update(user);
                List<UserBanks> bankses = userBanksService.findUserCardsByUserIdAndState(user.getId());
                if (bankses != null && !bankses.isEmpty()) {
                    userIntegralRecordService.authentication(user.getId());
                }
                session.setAttribute("message", "验证成功");
            }
        } else {
            session.setAttribute("error", "身份证号已存在！");
        }

        return "redirect:/userinfo/user/listUserInfo/" + userId;
    }

    //身份证验证
    @RequestMapping(value = "showValidataCard/{id}", method = RequestMethod.GET)
    @RequiresPermissions("user_verify")
    public String showValidataCard(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findOne(id));
        return "userinfo/user/validata";
    }


    //设置类型
    @RequestMapping(value = "userType/{id}/{userType}", method = RequestMethod.GET)
    @RequiresPermissions("user_type")
    public String userSettingType(@PathVariable("id") Long id, @PathVariable("userType") Integer userType, Model model, HttpSession session)
            throws Exception {
        User user = userService.findOne(id);
        if (user != null) {
            user.setUserType(userType);
            userService.update(user);
        }
        session.setAttribute("message", "设置类型成功");
        return "redirect:/userinfo/user/listUserInfo/" + id;
    }

    //设置等级
    @RequestMapping(value = "userLeve/{id}/{userLeve}", method = RequestMethod.GET)
    @RequiresPermissions("user_leve")
    public String userSettingLeve(@PathVariable("id") Long id, @PathVariable("userLeve") Long userLeve, HttpSession session)
            throws Exception {
        try {
            session.setAttribute("message", userService.userSettingLeve(id, userLeve));
        } catch (Exception e) {
            session.setAttribute("message", "设置失败");
        }
        return "redirect:/userinfo/user/listUserInfo/" + id;
    }



    //充值
    @RequestMapping(value = "showRechargeUser/{id}", method = RequestMethod.GET)
    @RequiresPermissions("user_recharge")
    public String showRechargeUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findOne(id));
        return "userinfo/user/recharge";
    }

    @RequestMapping(value = "rechargeUser", method = RequestMethod.POST)
    @RequiresPermissions("user_recharge")
    public String rechargeUser(Long userId, BigDecimal fund, String remark, Model model, HttpSession session) throws Exception {
        userService.saveRecharge(userId, fund, remark);
        session.setAttribute("message", "充值成功");
        User user = userService.findOne(userId);
        if (user != null) {
            // 放款成功进行发送短信
            DecimalFormat df = new DecimalFormat("#.00");
            StringBuffer sb = new StringBuffer();
            sb.append("成功充值");
            sb.append(df.format(fund));
            sb.append("元，感谢您的支持，快去投资赚收益吧！");
            smsHandleService.sendUserSms("invest_send_old", user.getMobile(), sb.toString());
        }
        return "redirect:/userinfo/user/listUserInfo/" + userId;
    }

    @RequestMapping(value = "saveDescribe", method = RequestMethod.POST)
    @RequiresPermissions("user_describe")
    public String saveDescribe(Long userId, String remark, HttpSession session) {
        User user = userService.findOne(userId);
        if (user != null) {
            if (StringUtils.isEmpty(remark.trim())) remark = null;
            user.setRemark(remark);
            userService.update(user);
        }
        session.setAttribute("message", "保存成功");
        return "redirect:/userinfo/user/listUserInfo/" + user.getId();
    }


    /**设置电子账户类型
     * @author: CRF
     * @param id
     * @param accountType
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "accountttype/{id}/{accounttype}", method = RequestMethod.GET)
    @RequiresPermissions("user_accounttype")
    public String userSettingAcountType(@PathVariable("id") Long id, @PathVariable("accounttype") Integer accountType, HttpSession session)
            throws Exception {
        try {
            session.setAttribute("message", userService.userSettingAccountType(id, accountType));
        } catch (Exception e) {
            session.setAttribute("message", "设置失败");
        }
        return "redirect:/userinfo/user/listUserInfo/" + id;
    }


    //导出
    @RequestMapping(value = "listUserExportExcel/{condition}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("user_exportexcel")
    public String exportExcel(
            @PathVariable(value = "condition") String condition,
            @ModelAttribute UserCondition userCondition, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String logoRealPathDir = request.getSession().getServletContext().getRealPath("/static/download");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        if (condition != null && condition.length() > 0) {
            userCondition = gson.fromJson(condition, UserCondition.class);
        }
        Map<String, Object> filter = userCondition.getSqlFilters();
        List<Object[]> list = userService.findExportData(filter);
        String fileName = userService.exportUser(list, logoRealPathDir);
        redirectAttributes.addFlashAttribute("fileName", fileName);
        return "redirect:/userinfo/user/listUser";
    }

    /**
     * 查询推荐人用户
     *
     * @param condition
     * @return
     */
    @RequestMapping(value = "findReferralUser", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json; charset=utf-8")
    @RequiresPermissions("user_mobile")
    @ResponseBody
    public String findUser(String condition) {
        return userService.findByUsernameOrMobile(condition, condition);
    }


    public IUserService getUserService() {
        return userService;
    }

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    public IVipGradeService getVipGradeService() {
        return vipGradeService;
    }

    public void setVipGradeService(IVipGradeService vipGradeService) {
        this.vipGradeService = vipGradeService;
    }

    public ISmsHandleService getSmsHandleService() {
        return smsHandleService;
    }

    public void setSmsHandleService(ISmsHandleService smsHandleService) {
        this.smsHandleService = smsHandleService;
    }

    public IUserBanksService getUserBanksService() {
        return userBanksService;
    }

    public void setUserBanksService(IUserBanksService userBanksService) {
        this.userBanksService = userBanksService;
    }

    public ISilverExchangeService getSilverExchangeService() {
        return silverExchangeService;
    }

    public void setSilverExchangeService(ISilverExchangeService silverExchangeService) {
        this.silverExchangeService = silverExchangeService;
    }

    public IUserInfoService getUserInfoService() {
        return userInfoService;
    }

    public void setUserInfoService(IUserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    public IUserIntegralRecordService getUserIntegralRecordService() {
        return userIntegralRecordService;
    }

    public void setUserIntegralRecordService(IUserIntegralRecordService userIntegralRecordService) {
        this.userIntegralRecordService = userIntegralRecordService;
    }
}
