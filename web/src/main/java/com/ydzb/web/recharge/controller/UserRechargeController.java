package com.ydzb.web.recharge.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.user.entity.User;
import com.ydzb.user.entity.UserRecharge;
import com.ydzb.user.service.IUserRechargeService;
import com.ydzb.web.recharge.condition.UserRechargeCondition;
import com.ydzb.web.user.condition.UserReferralCondition;
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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "recharge/requestresponse")
public class UserRechargeController extends BaseController {


    @Autowired
    private IUserRechargeService userRechargeService;

    /**
     * 根据条件分页查询
     *
     * @param pageSize
     * @param pageCurrent
     * @param condition
     * @param model
     * @return
     */
    @RequestMapping(value = "listRequestresponse", method = {
            RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("requestresponse_list")
    public String pageQuery(
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute UserRechargeCondition userRechargeCondition,
            Model model) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        if (condition != null && condition.length() > 0) {
            userRechargeCondition = gson.fromJson(condition, UserRechargeCondition.class);
        }

        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        // 添加查询条件
        searchable.addSearchFilters(userRechargeCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        // 设置排序条件
        searchable.addSort(new Sort(Direction.DESC, "id"));
        model.addAttribute("condition", gson.toJson(userRechargeCondition));
        model.addAttribute("page", userRechargeService.findAll(searchable));
        return "recharge/requestresponse/list";
    }

    /**
     * 导出用户状态
     * @param condition
     * @param userRechargeCondition
     * @param request
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "exportExcel/{condition}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("requestresponse_list")
    public String exportExcel(
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute(value = "userRechargeCondition") UserRechargeCondition userRechargeCondition,
            HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String path = request.getSession().getServletContext().getRealPath("/static/download");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();

        if (condition != null && condition.length() > 0) {
            userRechargeCondition = gson.fromJson(condition, UserRechargeCondition.class);
        }
        Map<String, Object> filter = userRechargeCondition.getSqlFilters();
        List<Object[]> list = userRechargeService.findExportResponseData(filter);
        String fileName = userRechargeService.exportRechargeState(list, path);
        redirectAttributes.addFlashAttribute("fileName", fileName);
        return "redirect:/recharge/requestresponse/listRequestresponse";
    }

    public IUserRechargeService getUserRechargeService() {
        return userRechargeService;
    }

    public void setUserRechargeService(IUserRechargeService userRechargeService) {
        this.userRechargeService = userRechargeService;
    }

}
