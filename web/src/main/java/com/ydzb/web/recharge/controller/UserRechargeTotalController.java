package com.ydzb.web.recharge.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.user.entity.UserInfo;
import com.ydzb.user.entity.UserRecharge;
import com.ydzb.user.service.IUserInfoService;
import com.ydzb.user.service.IUserRechargeService;
import com.ydzb.web.recharge.condition.UserRechargeCondition;
import com.ydzb.web.recharge.condition.UserRechargeDetailCondition;
import com.ydzb.web.recharge.condition.UserRechargeTotalCondition;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value = "recharge/rechargetotal")
public class UserRechargeTotalController extends BaseController {

    @Autowired
    private IUserRechargeService userRechargeService;

    @Autowired
    private IUserInfoService userInfoService;


    /**
     * 根据条件分页查询
     *
     * @param pageSize
     * @param pageCurrent
     * @param condition
     * @param model
     * @return
     */
    @RequestMapping(value = "listRechargetotal", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("rechargetotal_list")
    public String pageQuery(
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute UserRechargeTotalCondition userRechargeTotalCondition,
            Model model) {


        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (condition != null && condition.length() > 0) {
            userRechargeTotalCondition = gson.fromJson(condition, UserRechargeTotalCondition.class);
        }

        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        // 添加查询条件
        searchable.addSearchFilters(userRechargeTotalCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        // 设置排序条件
        searchable.addSort(new Sort(Direction.DESC, "id"));
        model.addAttribute("condition", gson.toJson(userRechargeTotalCondition));
        model.addAttribute("page", userRechargeService.findAll(searchable));
        model.addAttribute("total", userRechargeService.findSumRecharge(userRechargeTotalCondition.getStartTime(), userRechargeTotalCondition.getEndTime()));
        model.addAttribute("totalPerson", userInfoService.findRechargePersons());
        model.addAttribute("totalFund", userInfoService.findRechargeAllFund());

        model.addAttribute("startTime", userRechargeTotalCondition.getStartTime());
        model.addAttribute("endTime", userRechargeTotalCondition.getEndTime());
        return "recharge/rechargetotal/list";
    }


    @RequestMapping(value = "exportExcel/{condition}", method = {
            RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("rechargetotal_list")
    public String exportExcel(
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute UserRechargeTotalCondition userRechargeTotalCondition, Model model,
            HttpSession session, HttpServletRequest request,
            RedirectAttributes redirectAttributes) {
        String path = request.getSession().getServletContext().getRealPath("/static/download");

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();

        if (condition != null && condition.length() > 0) {
            userRechargeTotalCondition = gson.fromJson(condition, UserRechargeTotalCondition.class);
        }
        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        // 添加查询条件
        searchable.addSearchFilters(userRechargeTotalCondition.getAndFilters());
        searchable.addSort(new Sort(Direction.DESC, "id"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Map<String, Object> filters = new HashMap<>();
        if (!StringUtils.isEmpty(userRechargeTotalCondition.getStartTime())) {
            Long lg = 0L;
            try {
                lg = formatter.parse(userRechargeTotalCondition.getStartTime()).getTime() / 1000;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            filters.put("startTime", lg);
        } else {
            Long lg = DateUtil.getSystemTimeDay(DateUtil.getCurrentDate());
            filters.put("startTime", lg);
        }


        if (!StringUtils.isEmpty(userRechargeTotalCondition.getEndTime())) {
            Long lt = 0L;
            try {
                lt = formatter.parse(userRechargeTotalCondition.getEndTime()).getTime() / 1000;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            filters.put("endTime", lt);
        } else {
            Long lt = DateUtil.getSystemTimeDay(DateUtil.getCurrentDate()) + 24 * 3600;
            filters.put("endTime", lt);
        }

        List<Object> list = userRechargeService.findExportData(filters);
        String fileName = userRechargeService.exportExcele(list, path);
        redirectAttributes.addFlashAttribute("fileName", fileName);
        return "redirect:/recharge/rechargetotal/listRechargetotal";
    }

    /**
     * 充值详情
     * @param pageSize
     * @param pageCurrent
     * @param condition
     * @param userRechargeDetailCondition
     * @param model
     * @return
     */
    @RequestMapping(value = "detail", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("rechargetotal_list")
    public String detailPageQuery(
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute UserRechargeDetailCondition userRechargeDetailCondition,
            Model model) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (condition != null && condition.length() > 0) {
            userRechargeDetailCondition = gson.fromJson(condition, UserRechargeDetailCondition.class);
        }

        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        // 添加查询条件
        searchable.addSearchFilters(userRechargeDetailCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        // 设置排序条件
        searchable.addSort(new Sort(Direction.DESC, "id"));
        model.addAttribute("condition", gson.toJson(userRechargeDetailCondition));
        model.addAttribute("page", userInfoService.findAll(searchable));
        return "recharge/rechargetotal/detail";
    }

    /**
     * 导出充值详情
     * @param condition
     * @param userRechargeDetailCondition
     * @param model
     * @param session
     * @param request
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "exportDetail/{condition}", method = {
            RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("rechargetotal_list")
    public String exportDetail(
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute UserRechargeDetailCondition userRechargeDetailCondition, Model model,
            HttpSession session, HttpServletRequest request,
            RedirectAttributes redirectAttributes) {
        String path = request.getSession().getServletContext().getRealPath("/static/download");

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();

        if (condition != null && condition.length() > 0) {
            userRechargeDetailCondition = gson.fromJson(condition, UserRechargeDetailCondition.class);
        }
        Map<String, Object> filter = userRechargeDetailCondition.getSqlFilters();
        List<Object[]> list= userRechargeService.findExportDetailData(filter);
        String fileName = userRechargeService.exportDetail(list, path);
        redirectAttributes.addFlashAttribute("fileName", fileName);
        return "redirect:/recharge/rechargetotal/detail";
    }





    public IUserRechargeService getUserRechargeService() {
        return userRechargeService;
    }

    public void setUserRechargeService(IUserRechargeService userRechargeService) {
        this.userRechargeService = userRechargeService;
    }

    public IUserInfoService getUserInfoService() {
        return userInfoService;
    }

    public void setUserInfoService(IUserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }
}
