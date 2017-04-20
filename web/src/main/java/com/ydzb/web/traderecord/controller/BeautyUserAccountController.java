package com.ydzb.web.traderecord.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.product.service.IBeautyUserAccountService;
import com.ydzb.web.traderecord.condition.BeautyUserAccountCondition;
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

@Controller
@RequestMapping(value = "traderecord/beauty")
public class BeautyUserAccountController extends BaseController {


    @Autowired
    private IBeautyUserAccountService beautyUserAccountService;

    @RequestMapping(value = "listBeautyUserAccount", method = {
            RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("beautyuseraccount_list")
    public String pageQuery(
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute BeautyUserAccountCondition beautyUserAccountCondition,
            Model model) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (condition != null && condition.length() > 0) {
            beautyUserAccountCondition = gson.fromJson(condition, BeautyUserAccountCondition.class);
        }
        Searchable searchable = Searchable.newSearchable();
        searchable.addSearchFilters(beautyUserAccountCondition.getAndFilters());
        searchable.setPage(pageCurrent, pageSize);
        searchable.addSort(new Sort(Sort.Direction.ASC, "expireTime"));
        model.addAttribute("condition", gson.toJson(beautyUserAccountCondition));
        model.addAttribute("page", beautyUserAccountService.findAll(searchable));
        return "traderecord/beautyuser/list";
    }


    @RequestMapping(value = "exportEx/{condition}", method = {
            RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("beautyuseraccount_list")
    public String exportExcel(
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute BeautyUserAccountCondition beautyUserAccountCondition,
            HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String path = request.getSession().getServletContext().getRealPath("/static/download");

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        if (condition != null && condition.length() > 0) {
            beautyUserAccountCondition = gson.fromJson(condition, BeautyUserAccountCondition.class);
        }
        Map<String, Object> filter = beautyUserAccountCondition.getSqlFilter();
        List<Object[]> list = beautyUserAccountService.findExportData(filter);
        String fileName = beautyUserAccountService.exportExcele(list, path);
        redirectAttributes.addFlashAttribute("fileName", fileName);
        return "redirect:/traderecord/beauty/listBeautyUserAccount";
    }


    public IBeautyUserAccountService getBeautyUserAccountService() {
        return beautyUserAccountService;
    }

    public void setBeautyUserAccountService(IBeautyUserAccountService beautyUserAccountService) {
        this.beautyUserAccountService = beautyUserAccountService;
    }
}
