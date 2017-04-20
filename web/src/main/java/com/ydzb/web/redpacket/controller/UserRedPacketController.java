package com.ydzb.web.redpacket.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.packet.entity.RedPacketInterest;
import com.ydzb.packet.entity.RpUser;
import com.ydzb.packet.entity.UserRedPacket;
import com.ydzb.packet.service.IRedPacketInterestService;
import com.ydzb.packet.service.IUserRedPacketService;
import com.ydzb.user.service.IVipGradeService;
import com.ydzb.web.redpacket.condition.RedPacketInterestCondition;
import com.ydzb.web.redpacket.condition.UserRedPacketCondition;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigInteger;

/**
 * 用户红包控制层
 */
@Controller
@RequestMapping("redpacket/userRedpacket")
public class UserRedPacketController extends BaseController {

    @Autowired
    private IRedPacketInterestService interestService;
    @Autowired
    private IUserRedPacketService userRedPacketService;
    @Autowired
    private IVipGradeService vipGradeService;

    /**
     * 用户红包列表
     * @param pageSize
     * @param pageCurrent
     * @param condition
     * @param interestCondition
     * @param model
     * @return
     */
    @RequestMapping(value = "list", method = { RequestMethod.GET,
            RequestMethod.POST })
    @RequiresPermissions("userrp_list")
    public String pageQuery(
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute("condition") String condition,
            @ModelAttribute("interestCondition") RedPacketInterestCondition interestCondition, Model model) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();

        if (condition != null && condition.length() > 0) {
            interestCondition = gson.fromJson(condition, RedPacketInterestCondition.class);
        }

        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        // 添加查询条件
        searchable.addSearchFilters(interestCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        // 设置排序条件
        searchable.addSort(new Sort(Sort.Direction.DESC, "created"));
        model.addAttribute("condition", gson.toJson(interestCondition));
        model.addAttribute("page", interestService.findAll(searchable));
        return "redpacket/userredpacket/list";
    }

    /**
     * 根据红包id查询此红包发给哪些用户
     * @param id
     * @param userRedPaperCondition
     * @param pageSize
     * @param pageCurrent
     * @param model
     * @return
     */
    @RequestMapping(value = "{id}", method = { RequestMethod.GET, RequestMethod.POST })
    @RequiresPermissions("userrp_info")
    public String findRedPaperById(@PathVariable("id") Long id,
            UserRedPacketCondition userRedPaperCondition,
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            Model model) {

        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        userRedPaperCondition.setRedpacketId(id);
        userRedPaperCondition.setRedpacketType((byte)2);
        searchable.addSearchFilters(userRedPaperCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        // 设置排序条件
        searchable.addSort(new Sort(Sort.Direction.DESC, "created"));
        model.addAttribute("condition", userRedPaperCondition);
        model.addAttribute("page", userRedPacketService.findAll(searchable));
        model.addAttribute("redpacketId", id);
        return "redpacket/userredpacket/info";
    }

    /**
     *进入红包发送界面
     * @param model
     * @return
     */
    @RequestMapping(value = "send", method = { RequestMethod.GET, RequestMethod.POST })
    @RequiresPermissions("userrp_give")
    public String send(Model model) {
        model.addAttribute("vipGrades", vipGradeService.findAll(new Sort(Sort.Direction.ASC, "id")));
        model.addAttribute("redpackets", interestService.findAll(RedPacketInterest.STATE_INUSE));
        return "redpacket/userredpacket/send";
    }

    /**
     * 查询符合条件的用户
     * @param rpUser
     * @param model
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "querySendUserCount", method = { RequestMethod.GET, RequestMethod.POST })
    @RequiresPermissions("userrp_give")
    public String queryRedPackCondition (
            RpUser rpUser, Model model, HttpSession session)
            throws Exception {

        BigInteger currentPerson = BigInteger.ZERO;
        if (rpUser.haveCondition()) {
            currentPerson = userRedPacketService.querySendUserCount(rpUser);
        }
        // 在页面中保存当前用户条件
        model.addAttribute("vipGrades", vipGradeService.findAll(new Sort(Sort.Direction.ASC, "id")));
        model.addAttribute("redpackets", interestService.findAll(RedPacketInterest.STATE_INUSE));
        model.addAttribute("currentPerson", currentPerson);
        model.addAttribute("condition", rpUser);
        // 保存在Session 中发送红包时候调用此condition 查找出所有用户
        session.setAttribute("redpaketCondition", rpUser);
        return "redpacket/userredpacket/send";
    }

    /**
     * 发送红包
     * @param id
     * @param session
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "sendRedpacket", method = { RequestMethod.GET, RequestMethod.POST })
    @RequiresPermissions("userrp_give")
    public String sendRedpacket(Long id, HttpSession session, Model model) throws Exception {
        try {
            RpUser rpUser = (RpUser) session.getAttribute("redpaketCondition");

            if (!rpUser.haveCondition()) {
                model.addAttribute("redpackets", interestService.findAll(RedPacketInterest.STATE_INUSE));
                session.setAttribute("error", "请填写发送条件");
                return "redpacket/userredpacket/send";
            }

            // 发送红包
            String result = userRedPacketService.sendRedpacketInterest(rpUser, id);
            session.setAttribute("message", result);
            return "redirect:/redpacket/userRedpacket/list";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("redpackets", interestService.findAll(RedPacketInterest.STATE_INUSE));
            session.setAttribute("error", "发送失败");
            return "redpacket/userredpacket/send";
        }
    }
}