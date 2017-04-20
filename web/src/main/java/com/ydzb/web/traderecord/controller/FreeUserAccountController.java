package com.ydzb.web.traderecord.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.product.service.IFreeUserAccountService;
import com.ydzb.web.traderecord.condition.FreeUserAccountCondition;
import org.apache.commons.lang3.StringUtils;
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
 * 随心存已购controller
 */
@Controller
@RequestMapping(value = "buyrecord/free")
public class FreeUserAccountController extends BaseController {

    @Autowired
    private IFreeUserAccountService freeUserAccountService;

    /**
     * 分页列表
     * @param pageSize
     * @param pageCurrent
     * @param condition
     * @param model
     * @param freeUserAccountCondition
     * @return
     */
    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("freebuyrecord_list")
    public String pageQuery(@RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition, Model model,
            @ModelAttribute FreeUserAccountCondition freeUserAccountCondition) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (StringUtils.isNotEmpty(condition)) {
            freeUserAccountCondition = gson.fromJson(condition, FreeUserAccountCondition.class);
        }
        Searchable searchable = Searchable.newSearchable();
        searchable.addSearchFilters(freeUserAccountCondition.getAndFilters());
        searchable.setPage(pageCurrent, pageSize);
        searchable.addSort(new Sort(Sort.Direction.ASC, "expireTime"));
        model.addAttribute("condition", gson.toJson(freeUserAccountCondition));
        model.addAttribute("page", freeUserAccountService.findAll(searchable));
        setTotalFund(model, freeUserAccountCondition);
        return "buyrecord/free/list";
    }

    /**
     * 根据起始时间 查询总购买金额
     * @param model
     * @param freeUserAccountCondition
     */
    public void setTotalFund(Model model, FreeUserAccountCondition freeUserAccountCondition) {
        model.addAttribute("totalFund", freeUserAccountService.findTotalFund(freeUserAccountCondition.getStartTimeLong(),
                freeUserAccountCondition.getEndTimeLong()));
    }

    /**
     * 导出excel
     * @param condition
     * @param freeUserAccountCondition
     * @param request
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "exportExcel/{condition}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("freebuyrecord_list")
    public String exportExcel(
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute FreeUserAccountCondition freeUserAccountCondition,
            HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String path = request.getSession().getServletContext().getRealPath("/static/download");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (StringUtils.isNotEmpty(condition)) {
            freeUserAccountCondition = gson.fromJson(condition, FreeUserAccountCondition.class);
        }
        Map<String, Object> filter = freeUserAccountCondition.getSqlFilter();
        List<Object[]> list = freeUserAccountService.findExportData(filter);
        String fileName = freeUserAccountService.exportExcel(list, path);
        redirectAttributes.addFlashAttribute("fileName", fileName);
        return "redirect:/buyrecord/free/list";
    }
}