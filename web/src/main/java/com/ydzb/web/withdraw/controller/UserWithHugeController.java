package com.ydzb.web.withdraw.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.user.service.IUserService;
import com.ydzb.web.withdraw.condition.WithHugeCondition;
import com.ydzb.withdraw.service.IUserWithDrawNumService;
import com.ydzb.withdraw.service.IUserWithHugeService;
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


/**
 * 大额提现
 * @author benhang
 */

@Controller
@RequestMapping(value = "userwithdraw/withhuge")
public class UserWithHugeController extends BaseController {

    @Autowired
    private IUserWithHugeService userWithHugeService;
    @Autowired
    private IUserWithDrawNumService userWithDrawNumService;

    /**
     * 分页查询 @RequestParam 设置pageSize pageCurrent 默认值
     *
     * @param pageSize          每页显示数量
     * @param pageCurrent       当前页码
     * @param model
     * @return
     */
    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("withhuge_list")
    public String pageQuery(
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute WithHugeCondition withHugeCondition, Model model, HttpSession session) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (condition != null && condition.length() > 0) {
            withHugeCondition = gson.fromJson(condition, WithHugeCondition.class);
        }
        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        searchable.addSearchFilters(withHugeCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        // 设置排序条件
        searchable.addSort(new Sort(Direction.DESC, "optime"));
        model.addAttribute("condition", gson.toJson(withHugeCondition));
        model.addAttribute("page", userWithHugeService.findAll(searchable));
        session.setAttribute("pageCurrentSession", pageCurrent);
        session.setAttribute("conditionSession", gson.toJson(withHugeCondition));
        return "userwithdraw/withhuge/list";
    }



    @RequestMapping(value = "exportExcel/{condition}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("withhuge_list")
    public String exportExcel(
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute WithHugeCondition withHugeCondition,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes) {
        String path = request.getSession().getServletContext().getRealPath("/static/download");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (condition != null && condition.length() > 0) {
            withHugeCondition = gson.fromJson(condition, WithHugeCondition.class);
        }
        Map<String, Object> filters = withHugeCondition.getSqlFilters();
        List<Object[]> list = userWithHugeService.findWithdrawExportData(filters);
        String fileName = userWithHugeService.exportExcel(list, path);
        redirectAttributes.addFlashAttribute("fileName", fileName);
        return "redirect:/userwithdraw/withhuge/list";
    }
}
