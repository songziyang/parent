package com.ydzb.web.withdraw.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.web.withdraw.condition.WithDrawNumCondition;
import com.ydzb.withdraw.service.IWithdrawNumService;
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
import java.util.List;
import java.util.Map;

/**
 * 用户提现次数控制层
 */
@Controller
@RequestMapping(value = "userwithdraw/withdrawnum")
public class UserWithDrawNumController extends BaseController {

    @Autowired
    private IWithdrawNumService withdrawNumService;

    /**
     * 分页查询 @RequestParam 设置pageSize pageCurrent 默认值
     *
     * @param pageSize             每页显示数量
     * @param pageCurrent          当前页码
     * @param withDrawNumCondition 查询条件
     * @param model
     * @return
     */
    @RequestMapping(value = "list", method = {RequestMethod.GET,
            RequestMethod.POST})
    @RequiresPermissions("withdrawnum_list")
    public String pageQuery(
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute WithDrawNumCondition withDrawNumCondition, Model model) throws Exception {
        withdrawNumService.deleteAllWithdrawNum();
        withdrawNumService.initData();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (condition != null && condition.length() > 0) {
            withDrawNumCondition = gson.fromJson(condition, WithDrawNumCondition.class);
        }
        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        searchable.addSearchFilters(withDrawNumCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        // 设置排序条件
        //searchable.addSort(new Sort(Direction.DESC, "applicationTime"));
        addSort(searchable, withDrawNumCondition);

        model.addAttribute("condition", gson.toJson(withDrawNumCondition));
        model.addAttribute("page", withdrawNumService.findAll(searchable));

        return "userwithdraw/withdrawnum/list";
    }

    /**
     * 导出excel
     *
     * @param condition
     * @param withDrawNumCondition
     * @param request
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "export/{condition}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("withdrawnum_list")
    public String exportExcel(
            @ModelAttribute("condition") String condition,
            @ModelAttribute("withDrawNumCondition") WithDrawNumCondition withDrawNumCondition,
            HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String path = request.getSession().getServletContext().getRealPath("/static/download");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        if (condition != null && condition.length() > 0) {
            withDrawNumCondition = gson.fromJson(condition, WithDrawNumCondition.class);
        }
        Map<String, Object> filter = withDrawNumCondition.getSqlFilters();
        List<Object[]> list = withdrawNumService.findExportData(filter);
        String fileName = withdrawNumService.exportExcel(list, path);
        redirectAttributes.addFlashAttribute("fileName", fileName);
        return "redirect:/userwithdraw/withdrawnum/list";
    }


    /**
     * 排序
     *
     * @param searchable
     */
    private void addSort(Searchable searchable, WithDrawNumCondition withDrawNumCondition) {
        if (withDrawNumCondition.getOrderNumber() != null) {
            switch (withDrawNumCondition.getOrderNumber()) {
                case 1:
                    if ("desc".equals(withDrawNumCondition.getOrderSort())) {
                        searchable.addSort(new Sort(Sort.Direction.DESC, "countNum"));
                    } else {
                        searchable.addSort(new Sort(Sort.Direction.ASC, "countNum"));
                    }
                    break;
                case 2:
                    if ("desc".equals(withDrawNumCondition.getOrderSort())) {
                        searchable.addSort(new Sort(Sort.Direction.DESC, "rechargeNum"));
                    } else {
                        searchable.addSort(new Sort(Sort.Direction.ASC, "rechargeNum"));
                    }
                    break;
                case 3:
                    if ("desc".equals(withDrawNumCondition.getOrderSort())) {
                        searchable.addSort(new Sort(Sort.Direction.DESC, "withdrawTime"));
                    } else {
                        searchable.addSort(new Sort(Sort.Direction.ASC, "withdrawTime"));
                    }
                    break;
                case 4:
                    if ("desc".equals(withDrawNumCondition.getOrderSort())) {
                        searchable.addSort(new Sort(Sort.Direction.DESC, "rechargeTime"));
                    } else {
                        searchable.addSort(new Sort(Sort.Direction.ASC, "rechargeTime"));
                    }
                    break;
            }
        } else {
            searchable.addSort(new Sort(Sort.Direction.DESC, "created"));
        }


    }
}