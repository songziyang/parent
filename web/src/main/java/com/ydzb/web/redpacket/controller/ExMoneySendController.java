package com.ydzb.web.redpacket.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.packet.entity.ExpMoney;
import com.ydzb.packet.entity.RedPacketInterest;
import com.ydzb.packet.entity.RpUser;
import com.ydzb.packet.service.IExMoneySendService;
import com.ydzb.packet.service.IExpMoneyService;
import com.ydzb.packet.service.IUserRedPacketService;
import com.ydzb.user.service.IVipGradeService;
import com.ydzb.web.redpacket.condition.ExpMoneyCondition;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigInteger;

@Controller
@RequestMapping("redpacket/expmoneysend")
public class ExMoneySendController extends BaseController {

    @Autowired
    private IExpMoneyService expMoneyService;
    @Autowired
    private IVipGradeService vipGradeService;
    @Autowired
    private IUserRedPacketService userRedPacketService;
    @Autowired
    private IExMoneySendService exMoneySendService;


    /**
     * 分页查询
     *
     * @param pageSize          每页显示几条
     * @param pageCurrent       当前页数-从0开始
     * @param condition
     * @param expMoneyCondition
     * @param model
     * @return
     */
    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("expmoneysend_give")
    public String pageQuery(
            @RequestParam(defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute String condition,
            @ModelAttribute ExpMoneyCondition expMoneyCondition,
            RpUser rpUser, Model model) {

        Gson gson = new GsonBuilder().
                excludeFieldsWithoutExposeAnnotation().create();
        if (!StringUtils.isEmpty(condition)) {
            expMoneyCondition = gson.fromJson(condition, ExpMoneyCondition.class);
        }
        //创建查询
        Searchable searchable = Searchable.newSearchable();
        //添加查询条件
        searchable.addSearchFilters(expMoneyCondition.getAndFilters());
        //设置排序条件
        searchable.addSort(new Sort(Sort.Direction.DESC, "created"));

        model.addAttribute("vipGrades", vipGradeService.findAll(new Sort(Sort.Direction.ASC, "id")));

        model.addAttribute("condition", rpUser);
        model.addAttribute("page", expMoneyService.findAllWithSort(searchable));
        return "redpacket/expmoneysend/list";
    }

    /**
     * 查询符合条件的用户
     *
     * @param rpUser
     * @param model
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "querySendUserCount", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("expmoneysend_give")
    public String queryRedPackCondition(
            RpUser rpUser, Model model, HttpSession session)
            throws Exception {
        BigInteger currentPerson = BigInteger.ZERO;
        if (rpUser.haveCondition()) {
            currentPerson = userRedPacketService.querySendUserCount(rpUser);
        }
        // 在页面中保存当前用户条件
        model.addAttribute("vipGrades", vipGradeService.findAll(new Sort(Sort.Direction.ASC, "id")));
        model.addAttribute("page", expMoneyService.findAll(ExpMoney.STATE_INUSE));
        model.addAttribute("currentPerson", currentPerson);
        model.addAttribute("condition", rpUser);
        // 保存在Session 中发送红包时候调用此condition 查找出所有用户
        session.setAttribute("expMoneyCondition", rpUser);
        return "redpacket/expmoneysend/list";
    }

    /**
     * 发送体验金
     *
     * @param id
     * @param session
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "sendmoney/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("expmoneysend_give")
    public String sendMoney(@PathVariable Long id, HttpSession session, Model model) throws Exception {
        System.out.println("==============");
        try {
            System.out.println("==============" + id );
            RpUser rpUser = (RpUser) session.getAttribute("expMoneyCondition");
            if (rpUser == null || !rpUser.haveCondition()) {
                //model.addAttribute("page", expMoneyService.findAll(RedPacketInterest.STATE_INUSE));
                session.setAttribute("error", "请填写发送条件");
                session.setAttribute("expMoneyCondition", rpUser);
                return "redirect:/redpacket/expmoneysend/list";
            }
            System.out.println("==============" + rpUser );
            // 发送体验金
            String result = exMoneySendService.sendMoney(rpUser, id);
            session.setAttribute("message", result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/redpacket/expmoneysend/list";
    }

    public IExpMoneyService getExpMoneyService() {
        return expMoneyService;
    }

    public void setExpMoneyService(IExpMoneyService expMoneyService) {
        this.expMoneyService = expMoneyService;
    }

    public IVipGradeService getVipGradeService() {
        return vipGradeService;
    }

    public void setVipGradeService(IVipGradeService vipGradeService) {
        this.vipGradeService = vipGradeService;
    }

    public IUserRedPacketService getUserRedPacketService() {
        return userRedPacketService;
    }

    public void setUserRedPacketService(IUserRedPacketService userRedPacketService) {
        this.userRedPacketService = userRedPacketService;
    }

    public IExMoneySendService getExMoneySendService() {
        return exMoneySendService;
    }

    public void setExMoneySendService(IExMoneySendService exMoneySendService) {
        this.exMoneySendService = exMoneySendService;
    }
}