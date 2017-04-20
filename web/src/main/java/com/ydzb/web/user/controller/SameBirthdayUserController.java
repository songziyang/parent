package com.ydzb.web.user.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.user.service.IUserService;
import com.ydzb.web.user.condition.UserCondition;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 周年生日活动
 */
@Controller
@RequestMapping(value = "userinfo/samebirthday")
public class SameBirthdayUserController extends BaseController {

    @Autowired
    private IUserService userService;

    /**
     * 列表
     * @param condition
     * @param userCondition
     * @param model
     * @return
     */
    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("samebirthday_list")
    public String listPopularize(@ModelAttribute(value = "condition") String condition,
            @ModelAttribute(value = "userCondition") UserCondition userCondition,
            Model model) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();

        if (condition != null && condition.length() > 0) {
            userCondition = gson.fromJson(condition, UserCondition.class);
        }
        model.addAttribute("condition", gson.toJson(userCondition));

        model.addAttribute("vipusers", userService.findSameBirthdayUsers(userCondition.getSqlFilters()));
        return "userinfo/samebirthday/list";
    }

    /**
     * 导出excel
     * @param condition
     * @param userCondition
     * @param request
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "exportExcel/{condition}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("samebirthday_list")
    public String exportExcelFund(
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute(value = "userCondition") UserCondition userCondition,
            HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String path = request.getSession().getServletContext().getRealPath("/static/download");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();

        if (condition != null && condition.length() > 0) {
            userCondition = gson.fromJson(condition, UserCondition.class);
        }
        Map<String, Object> filter = userCondition.getSqlFilters();
        List<Object[]> list = userService.findSameBirthdayExportData(filter);
        String fileName = userService.exportSameBirthdayUser(list, path);
        redirectAttributes.addFlashAttribute("fileName", fileName);

        return "redirect:/userinfo/samebirthday/list";
    }
}