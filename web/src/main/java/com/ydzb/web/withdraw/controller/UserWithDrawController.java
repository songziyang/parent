package com.ydzb.web.withdraw.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.user.service.IUserService;
import com.ydzb.web.withdraw.condition.WithDrawCondition;
import com.ydzb.withdraw.base.Constance;
import com.ydzb.withdraw.entity.UserWithDraw;
import com.ydzb.withdraw.service.IUserWithDrawNumService;
import com.ydzb.withdraw.service.IUserWithDrawService;
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
import java.util.List;
import java.util.Map;


/**
 * 放款审核
 *
 * @author benhang
 */

@Controller
@RequestMapping(value = "userwithdraw/withdraw")
public class UserWithDrawController extends BaseController {

    @Autowired
    private IUserWithDrawService userWithDrawService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserWithDrawNumService userWithDrawNumService;

    /**
     * 分页查询 @RequestParam 设置pageSize pageCurrent 默认值
     *
     * @param pageSize          每页显示数量
     * @param pageCurrent       当前页码
     * @param withDrawCondition 查询条件
     * @param model
     * @return
     */
    @RequestMapping(value = "listUserWithDraw", method = {RequestMethod.GET,
            RequestMethod.POST})
    @RequiresPermissions("withdraw_list")
    public String pageQuery(
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute WithDrawCondition withDrawCondition, Model model, HttpSession session) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (condition != null && condition.length() > 0) {
            withDrawCondition = gson.fromJson(condition, WithDrawCondition.class);
        }
        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        searchable.addSearchFilters(withDrawCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        // 设置排序条件
        addSort(searchable, withDrawCondition);

        model.addAttribute("condition", gson.toJson(withDrawCondition));
        model.addAttribute("page", userWithDrawService.findAll(searchable));
        session.setAttribute("pageCurrentSession", pageCurrent);
        session.setAttribute("conditionSession", gson.toJson(withDrawCondition));
        return "userwithdraw/withdraw/list";
    }


    /**
     * 根据放款列表ID查看放款详细信息
     *
     * @param id
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "findWithDrawByID/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("withdraw_detailed")
    public String editWithDraw(@PathVariable("id") Long id, Model model, HttpSession session) {
        UserWithDraw userWithDraw = userWithDrawService.findOne(id);
        if (userWithDraw != null) {
            model.addAttribute("userWithDraw", userWithDraw);
            model.addAttribute("user", userService.findOne(userWithDraw.getUser().getId()));
            model.addAttribute("condition", session.getAttribute("conditionSession"));
            model.addAttribute("pageCurrent", session.getAttribute("pageCurrentSession"));
            return "userwithdraw/withdraw/edit";
        }
        return "redirect:/userwithdraw/withdraw/listUserWithDraw";
    }


    /**
     * 放款审核失败
     *
     * @param draw
     * @return
     */
    @RequiresPermissions("withdraw_fail")
    @RequestMapping(value = "failWithDraw", method = {RequestMethod.GET, RequestMethod.POST})
    public String failWithDraw(UserWithDraw draw, String condition, int pageCurrent, RedirectAttributes attributes, HttpSession session) {
        try {
            attributes.addAttribute("pageCurrent", pageCurrent);
            attributes.addAttribute("condition", condition);
            userWithDrawService.updateWithDrawFailNew(draw.getId(), draw.getProbleDescription(), Constance.VERIFY_FAIL, draw.getAdvanceMoney());
            session.setAttribute("message", "审核成功！");
        } catch (Exception e) {
            session.setAttribute("error", "审核失败，已被审核！");
            e.printStackTrace();
        }
        return "redirect:/userwithdraw/withdraw/listUserWithDraw";
    }

    /**
     * 审核成功
     *
     * @param draw
     * @param model
     * @return
     */
    @RequiresPermissions("withdraw_success")
    @RequestMapping(value = "submitWithDraw", method = {RequestMethod.GET,
            RequestMethod.POST})
    public String submitWithDraw(UserWithDraw draw, String condition, int pageCurrent, Model model, RedirectAttributes attributes, HttpSession session) {
        try {
            attributes.addAttribute("pageCurrent", pageCurrent);
            attributes.addAttribute("condition", condition);
            userWithDrawService.updateWithDrawSuccess(draw.getId(), draw.getAdvanceMoney());
            session.setAttribute("message", "审核成功！");
        } catch (Exception e) {
            session.setAttribute("error", "审核失败，已被审核！");
            e.printStackTrace();
        }
        return "redirect:/userwithdraw/withdraw/listUserWithDraw";
    }


    @RequestMapping(value = "exportExcel/{condition}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("withdraw_list")
    public String exportExcel(
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute WithDrawCondition withDrawCondition, Model model,
            HttpSession session, HttpServletRequest request,
            RedirectAttributes redirectAttributes) {
        String path = request.getSession().getServletContext().getRealPath("/static/download");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (condition != null && condition.length() > 0) {
            withDrawCondition = gson.fromJson(condition, WithDrawCondition.class);
        }
        Map<String, Object> filters = withDrawCondition.getSqlFilters();
        List<Object[]> list = userWithDrawNumService.findWithdrawExportData(filters);
        String fileName = userWithDrawNumService.exportWithDrawExcel(list, path);
        redirectAttributes.addFlashAttribute("fileName", fileName);
        return "redirect:/userwithdraw/withdraw/listUserWithDraw";
    }


    /**
     * 排序
     *
     * @param searchable
     * @param withDrawCondition
     */
    public void addSort(Searchable searchable, WithDrawCondition withDrawCondition) {
        if (withDrawCondition.getOrderNumber() != null) {
            switch (withDrawCondition.getOrderNumber()) {
                case 1:
                    //提现金额
                    if ("desc".equals(withDrawCondition.getOrderSort())) {
                        searchable.addSort(new Sort(Direction.DESC, "withdrawMoney"));
                    } else {
                        searchable.addSort(new Sort(Direction.ASC, "withdrawMoney"));
                    }
                    break;
                case 2:
                    //提现申请时间
                    if ("desc".equals(withDrawCondition.getOrderSort())) {
                        searchable.addSort(new Sort(Direction.DESC, "applicationTime"));
                    } else {
                        searchable.addSort(new Sort(Direction.ASC, "applicationTime"));
                    }
                    break;
                case 3:
                    //提现统计次数
                    if ("desc".equals(withDrawCondition.getOrderSort())) {
                        searchable.addSort(new Sort(Direction.DESC, "user.userWithDrawNum.countNum"));
                    } else {
                        searchable.addSort(new Sort(Direction.ASC, "user.userWithDrawNum.countNum"));
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

    public IUserWithDrawNumService getUserWithDrawNumService() {
        return userWithDrawNumService;
    }

    public void setUserWithDrawNumService(IUserWithDrawNumService userWithDrawNumService) {
        this.userWithDrawNumService = userWithDrawNumService;
    }
}
