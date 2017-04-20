package com.ydzb.web.user.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.user.entity.UserInfo;
import com.ydzb.user.service.IUserInfoService;
import com.ydzb.web.user.condition.UserInfoCondition;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * Created by CRF on 2017/3/7 0007.
 */
@Controller
@RequestMapping(value = "userinfo/userinfo")
public class UserInfoController extends BaseController {

    @Autowired
    private IUserInfoService userInfoService;

    public IUserInfoService getUserInfoService() {
        return userInfoService;
    }

    public void setUserInfoService(IUserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    /**
     * 列表方法
     * @param pageSize
     * @param pageCurrent
     * @param condition
     * @param userInfoCondition
     * @param model
     * @return
     */
    @RequestMapping(value = "signnlist", method = {RequestMethod.GET,
            RequestMethod.POST})
    @RequiresPermissions("user_signn_list")
    public String pageQuery(
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute UserInfoCondition userInfoCondition, Model model) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        if (condition != null && condition.length() > 0) {
            userInfoCondition = gson.fromJson(condition, UserInfoCondition.class);
        }

        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        // 添加查询条件
        searchable.addSearchFilters(userInfoCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        // 设置排序条件
        searchable.addSort(new Sort(Sort.Direction.DESC, "id"));
        model.addAttribute("condition", gson.toJson(userInfoCondition));
        model.addAttribute("page", userInfoService.findAll(searchable));

        return "userinfo/user/listuserinfosignn";
    }



    //导出
    @RequestMapping(value = "listUserInfoExportExcel/{condition}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("user_exportexcel")
    public String exportExcel(
            @PathVariable(value = "condition") String condition,
            @ModelAttribute UserInfoCondition userInfoCondition, HttpServletRequest request, RedirectAttributes redirectAttributes) {

        String logoRealPathDir = request.getSession().getServletContext().getRealPath("/static/download");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        if (condition != null && condition.length() > 0) {
            userInfoCondition = gson.fromJson(condition, UserInfoCondition.class);
        }

        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        // 添加查询条件
        searchable.addSearchFilters(userInfoCondition.getAndFilters());
        // 设置排序条件
        searchable.addSort(new Sort(Sort.Direction.DESC, "id"));

        List<UserInfo> list = userInfoService.findAll(searchable).getContent();
        String fileName = userInfoService.exportUser(list, logoRealPathDir);
        redirectAttributes.addFlashAttribute("fileName", fileName);
        return "redirect:/userinfo/userinfo/signnlist";
    }
}
