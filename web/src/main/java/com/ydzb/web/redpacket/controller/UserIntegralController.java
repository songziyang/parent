package com.ydzb.web.redpacket.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.packet.entity.Integral;
import com.ydzb.packet.entity.RpUser;
import com.ydzb.packet.service.IIntegralService;
import com.ydzb.packet.service.IUserIntegralRecordService;
import com.ydzb.packet.service.IUserIntegralService;
import com.ydzb.packet.service.IUserRedPacketService;
import com.ydzb.user.service.IVipGradeService;
import com.ydzb.web.redpacket.condition.IntegralCondition;
import com.ydzb.web.redpacket.condition.UserIntegralRecordCondition;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigInteger;

/**
 * 用户积分控制层
 * @author sy
 */
@Controller
@RequestMapping("redpacket/sendIntegral")
public class UserIntegralController extends BaseController {

    @Autowired
    private IUserRedPacketService userRedPacketService;
    @Autowired
    private IVipGradeService vipGradeService;
    @Autowired
    private IIntegralService integralService;
    @Autowired
    private IUserIntegralService userIntegralService;
    @Autowired
    private IUserIntegralRecordService userIntegralRecordService;

    /**
     * 用户红包列表
     * @param pageSize
     * @param pageCurrent
     * @param condition
     * @param model
     * @return
     */
    @RequestMapping(value = "list", method = { RequestMethod.GET,
            RequestMethod.POST })
    @RequiresPermissions("sendintegral_list")
    public String pageQuery(@RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute("condition") String condition, Model model,
            @ModelAttribute("integralCondition") IntegralCondition integralCondition) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        if (StringUtils.isNotEmpty(condition)) {
            integralCondition = gson.fromJson(condition, IntegralCondition.class);
        }
        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        // 添加查询条件
        searchable.addSearchFilters(integralCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        // 设置排序条件
        searchable.addSort(new Sort(Sort.Direction.DESC, "created"));
        model.addAttribute("condition", gson.toJson(integralCondition));
        model.addAttribute("page", integralService.findAll(searchable));
        return "redpacket/sendintegral/list";
    }

    /**
     * 根据红包id查询此红包发给哪些用户
     * @param id
     * @param pageSize
     * @param pageCurrent
     * @param model
     * @return
     */
    @RequestMapping(value = "{id}", method = { RequestMethod.GET, RequestMethod.POST })
    @RequiresPermissions("sendintegral_info")
    public String findById(@PathVariable("id") Long id,
        UserIntegralRecordCondition userIntegralRecordCondition,
        @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
        @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
        Model model) {

        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        userIntegralRecordCondition.setLinkId(id);
        userIntegralRecordCondition.setLinkType(24);
        searchable.addSearchFilters(userIntegralRecordCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        // 设置排序条件
        searchable.addSort(new Sort(Sort.Direction.DESC, "created"));
        model.addAttribute("condition", userIntegralRecordCondition);
        model.addAttribute("page", userIntegralRecordService.findAll(searchable));
        model.addAttribute("integralId", id);
        return "redpacket/sendintegral/info";
    }

    /**
     * 进入红包发送界面
     * @param model
     * @return
     */
    @RequestMapping(value = "send", method = { RequestMethod.GET, RequestMethod.POST })
    @RequiresPermissions("sendintegral_give")
    public String send(Model model) {
        model.addAttribute("vipGrades", vipGradeService.findAll(new Sort(Sort.Direction.ASC, "id")));
        model.addAttribute("integrals", integralService.findAll(Integral.STATE_INUSE));
        return "redpacket/sendintegral/send";
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
    @RequiresPermissions("sendintegral_give")
    public String queryRedPackCondition (
            RpUser rpUser, Model model, HttpSession session)
            throws Exception {
        BigInteger currentPerson = BigInteger.ZERO;
        if (rpUser.haveCondition()) {
            currentPerson = userRedPacketService.querySendUserCount(rpUser);
        }
        // 在页面中保存当前用户条件
        model.addAttribute("vipGrades", vipGradeService.findAll(new Sort(Sort.Direction.ASC, "id")));
        model.addAttribute("integrals", integralService.findAll(Integral.STATE_INUSE));
        model.addAttribute("currentPerson", currentPerson);
        model.addAttribute("condition", rpUser);
        // 保存在Session 中发送红包时候调用此condition 查找出所有用户
        session.setAttribute("integralCondition", rpUser);
        return "redpacket/sendintegral/send";
    }

    /**
     * 发送红包
     * @param id
     * @param session
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "sendIntegral", method = { RequestMethod.GET, RequestMethod.POST })
    @RequiresPermissions("sendintegral_give")
    public String sendRedpacket(Long id, HttpSession session, Model model) throws Exception {
        try {
            RpUser rpUser = (RpUser) session.getAttribute("integralCondition");
            if (!rpUser.haveCondition()) {
                model.addAttribute("integrals", integralService.findAll(Integral.STATE_INUSE));
                session.setAttribute("error", "请填写发送条件");
                return "redpacket/sendintegral/send";
            }

            // 发送红包
            String result = userIntegralService.sendIntegral(rpUser, id);
            session.setAttribute("message", result);

            return "redirect:/redpacket/sendIntegral/list";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("integrals", integralService.findAll(Integral.STATE_INUSE));
            session.setAttribute("error", "发送失败");
            return "redpacket/sendintegral/send";
        }
    }
}
