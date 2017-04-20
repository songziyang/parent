package com.ydzb.web.user.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.user.entity.User;
import com.ydzb.user.service.IUserService;
import com.ydzb.web.user.condition.UserMoneyCondition;
import com.ydzb.web.user.condition.UserReferralCondition;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
@RequestMapping(value = "userinfo/referralUser")
public class UserReferralController extends BaseController {


    @Autowired
    private IUserService userService;

    /**
     *
     * @param pageSize
     * @param pageCurrent
     * @param userCondition
     * @param model
     * @return
     */
    @RequestMapping(value = "listReferralUser", method = { RequestMethod.GET, RequestMethod.POST })
    @RequiresPermissions("referraluser_list")
    public String listPopularize(
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute(value = "userCondition")  UserReferralCondition userCondition,
            Model model) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();

        if (condition != null && condition.length() > 0) {
            userCondition = gson.fromJson(condition, UserReferralCondition.class);
        }
        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        // 添加查询条件
        searchable.addSearchFilters(userCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        // 设置排序条件
        searchable.addSort(new Sort(Sort.Direction.DESC, "referralNum"));
        model.addAttribute("condition", gson.toJson(userCondition));
        model.addAttribute("page", userService.findAll(searchable));
        return "userinfo/user/popularize";
    }

    @RequestMapping(value = "exportExcel/{condition}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("referraluser_list")
    public String exportExcelFund(
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute(value = "userCondition") UserReferralCondition userCondition,
            Model model, HttpSession session, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String path = request.getSession().getServletContext().getRealPath("/static/download");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();

        if (condition != null && condition.length() > 0) {
            userCondition = gson.fromJson(condition, UserReferralCondition.class);
        }
        Map<String, Object> filter = userCondition.getMysqlFilters();
        List<Object[]> list = userService.findReferralExportData(filter);
        String fileName = userService.exportRefferralUser(list, path);
        redirectAttributes.addFlashAttribute("fileName", fileName);

        return "redirect:/userinfo/referralUser/listReferralUser";
    }
    public IUserService getUserService() {
        return userService;
    }

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }
}