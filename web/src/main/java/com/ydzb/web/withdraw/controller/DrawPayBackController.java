package com.ydzb.web.withdraw.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.admin.shiro.ShiroUser;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.user.service.IUserService;
import com.ydzb.web.withdraw.condition.PayBackDrawCondition;
import com.ydzb.withdraw.base.Constance;
import com.ydzb.withdraw.entity.UserWithDraw;
import com.ydzb.withdraw.service.IUserWithDrawNumService;
import com.ydzb.withdraw.service.IUserWithDrawService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "userwithdraw/paback")
public class DrawPayBackController extends BaseController {

    @Autowired
    private IUserWithDrawService userWithDrawService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserWithDrawNumService userWithDrawNumService;

    /**
     * 分页查询 @RequestParam 设置pageSize pageCurrent 默认值
     *
     * @param pageSize             每页显示数量
     * @param pageCurrent          当前页码
     * @param payBackDrawCondition 查询条件
     * @param model
     * @return
     */
    @RequestMapping(value = "listWithDrawPayBack", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("withdraws_list")
    public String pageQuery(@RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
                            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
                            @ModelAttribute(value = "condition") String condition,
                            @ModelAttribute PayBackDrawCondition payBackDrawCondition,
                            Model model, HttpSession session) throws Exception {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        Boolean poundage = payBackDrawCondition.getPoundage();
        if (condition != null && condition.length() > 0) {
            payBackDrawCondition = gson.fromJson(condition, PayBackDrawCondition.class);
        }
        //为了把超链接上的poundage属性加到condition中
        if (payBackDrawCondition.getPoundage() == null) {
            payBackDrawCondition.setPoundage(poundage);
        }

        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        searchable.addSearchFilters(payBackDrawCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        // 设置排序条件
        addSort(searchable, payBackDrawCondition);

        model.addAttribute("condition", gson.toJson(payBackDrawCondition));
        model.addAttribute("page", userWithDrawService.findAll(searchable));
        session.setAttribute("pageCurrentSession", pageCurrent);
        session.setAttribute("conditionSession", gson.toJson(payBackDrawCondition));
        Map<String, Object> filters = payBackDrawCondition.getSumFilters();
        Object[] moneys = userWithDrawService.findSumMoney(filters);
        model.addAttribute("withdrawMoney", moneys[0]);
        model.addAttribute("advanceMoney", moneys[1]);
        model.addAttribute("payTimeStart", payBackDrawCondition.getApplicationTimeStart());
        model.addAttribute("payTimeEnd", payBackDrawCondition.getApplicationTimeEnd());
        return "userwithdraw/payback/list";
    }


    /**
     * 根据id查看 还款信息
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "findWithDrawByID/{id}", method = {
            RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("withdraws_list")
    public String findWithDrawByID(@PathVariable("id") Long id, Model model, HttpSession session) {
        UserWithDraw userWithDraw = userWithDrawService.findOne(id);
        if (userWithDraw != null) {
            model.addAttribute("userWithDraw", userWithDraw);
            model.addAttribute("user", userService.findOne(userWithDraw.getUser().getId()));
            model.addAttribute("condition", session.getAttribute("conditionSession"));
            model.addAttribute("pageCurrent", session.getAttribute("pageCurrentSession"));
        }
        return "userwithdraw/payback/edit";
    }


    /**
     * 民生打款
     *
     * @param id
     * @param session
     * @return
     * @throws Exception
     */
    @RequiresPermissions("withdraws_detailed")
    @RequestMapping(value = "loanMoney/{id}/{condition}/{pageCurrent}", method = {RequestMethod.GET, RequestMethod.POST})
    public String loanMoney(@PathVariable("id") Long id, @PathVariable("condition") String condition, @PathVariable("pageCurrent") int pageCurrent, RedirectAttributes attributes, HttpSession session) throws Exception {
        try {
            Subject currentUser = SecurityUtils.getSubject();
            ShiroUser shiroUser = (ShiroUser) currentUser.getPrincipal();
            attributes.addAttribute("pageCurrent", pageCurrent);
            attributes.addAttribute("condition", condition);
            String result = userWithDrawService.updateLoanMoney(id, shiroUser.getUsername());
            if (StringUtils.isEmpty(result)) {
                userWithDrawService.querysendRequest(id);
                userWithDrawService.sendMessage(id);
                session.setAttribute("message", "操作成功");
            } else {
                session.setAttribute("error", result);
            }
        } catch (Exception e) {
            session.setAttribute("error", "操作失败！");
            e.printStackTrace();
        }
        return "redirect:/userwithdraw/paback/listWithDrawPayBack";
    }


    /**
     * 放款成功
     *
     * @param id
     * @param session
     * @return
     */
    @RequiresPermissions("withdraws_detailed")
    @RequestMapping(value = "minSubmitWithDraw/{id}/{condition}/{pageCurrent}", method = {
            RequestMethod.GET, RequestMethod.POST})
    public String minSubmitWithDraw(@PathVariable("id") Long id, @PathVariable("condition") String condition, @PathVariable("pageCurrent") int pageCurrent, RedirectAttributes attributes, HttpSession session) throws Exception {
        try {
            attributes.addAttribute("pageCurrent", pageCurrent);
            attributes.addAttribute("condition", condition);
            String result = userWithDrawService.minSubmitWithDraw(id);
            if (StringUtils.isEmpty(result)) {
                session.setAttribute("message", "已批准放款");
            } else {
                session.setAttribute("error", result);
            }
        } catch (Exception e) {
            session.setAttribute("error", "操作失败，已经放款！");
            e.printStackTrace();
        }
        return "redirect:/userwithdraw/paback/listWithDrawPayBack";
    }


    /**
     * 放款失败
     *
     * @param draw
     * @return
     */
    @RequiresPermissions("withdraws_detailed")
    @RequestMapping(value = "minFailPayBack", method = {RequestMethod.GET,
            RequestMethod.POST})
    public String minFailPayBack(UserWithDraw draw, String condition, int pageCurrent, RedirectAttributes attributes, HttpSession session) {
        try {
            attributes.addAttribute("pageCurrent", pageCurrent);
            attributes.addAttribute("condition", condition);
            UserWithDraw userWithDraw = userWithDrawService.findOne(draw.getId());
            userWithDrawService.minFailPayBack(userWithDraw.getId(), draw.getProbleDescription(), Constance.PAYBACK_FAIL, userWithDraw.getAdvanceMoney());
            session.setAttribute("message", "操作成功");
        } catch (Exception e) {
            session.setAttribute("error", "操作失败，已经放款！");
            e.printStackTrace();
        }
        return "redirect:/userwithdraw/paback/listWithDrawPayBack";
    }


    /**
     * 手动打款
     *
     * @param id
     * @param session
     * @return
     * @throws Exception
     */
    @RequiresPermissions("withdraws_detailed")
    @RequestMapping(value = "sdloanMoney/{id}/{condition}/{pageCurrent}", method = {RequestMethod.GET, RequestMethod.POST})
    public String sdloanMoney(@PathVariable("id") Long id, @PathVariable("condition") String condition, @PathVariable("pageCurrent") int pageCurrent, RedirectAttributes attributes, HttpSession session) throws Exception {
        try {
            attributes.addAttribute("pageCurrent", pageCurrent);
            attributes.addAttribute("condition", condition);
            String result = userWithDrawService.updatesdloanMoney(id);
            if (StringUtils.isEmpty(result)) {
                userWithDrawService.sendMessage(id);
                session.setAttribute("message", "操作成功");
            } else {
                session.setAttribute("error", result);
            }
        } catch (Exception e) {
            session.setAttribute("error", "操作失败！");
            e.printStackTrace();
        }
        return "redirect:/userwithdraw/paback/listWithDrawPayBack";
    }


    /**
     * @param condition
     * @param withDrawCondition
     * @param model
     * @param session
     * @param request
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "exportExcel/{condition}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("withdraws_list")
    public String exportExcel(
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute PayBackDrawCondition withDrawCondition,
            Model model, HttpSession session, HttpServletRequest request,
            RedirectAttributes redirectAttributes) {
        String path = request.getSession().getServletContext().getRealPath("/static/download");

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        if (condition != null && condition.length() > 0) {
            withDrawCondition = gson.fromJson(condition, PayBackDrawCondition.class);
        }
        Map<String, Object> filters = withDrawCondition.getSqlFilters();
        List<Object[]> list = userWithDrawNumService.findWithdrawExportData(filters);
        String fileName = userWithDrawNumService.exportWithDrawExcel(list, path);
        redirectAttributes.addFlashAttribute("fileName", fileName);
        return "redirect:/userwithdraw/paback/listWithDrawPayBack";
    }


    /**
     * 排序
     *
     * @param searchable
     * @param payBackDrawCondition
     */
    public void addSort(Searchable searchable, PayBackDrawCondition payBackDrawCondition) {
        if (payBackDrawCondition.getOrderNumber() != null) {
            switch (payBackDrawCondition.getOrderNumber()) {
                case 1:
                    if ("desc".equals(payBackDrawCondition.getOrderSort())) {
                        searchable.addSort(new Sort(Direction.DESC, "withdrawMoney"));
                    } else {
                        searchable.addSort(new Sort(Direction.ASC, "withdrawMoney"));
                    }
                    break;
                case 2:
                    if ("desc".equals(payBackDrawCondition.getOrderSort())) {
                        searchable.addSort(new Sort(Direction.DESC, "applicationTime"));
                    } else {
                        searchable.addSort(new Sort(Direction.ASC, "applicationTime"));
                    }
                    break;
            }
        } else {
            searchable.addSort(new Sort(Direction.DESC, "applicationTime"));
        }
    }


    public IUserWithDrawService getUserWithDrawService() {
        return userWithDrawService;
    }

    public void setUserWithDrawService(IUserWithDrawService userWithDrawService) {
        this.userWithDrawService = userWithDrawService;
    }


    public IUserService getUserService() {
        return userService;
    }

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }


}
