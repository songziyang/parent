package com.ydzb.web.traderecord.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.product.entity.RagularUserAccount;
import com.ydzb.product.service.IRagularUserAccountService;
import com.ydzb.web.traderecord.condition.RegularUserAccountCondition;
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
@RequestMapping(value = "traderecord/ragular")
public class RagularUserAccountController extends BaseController {


    @Autowired
    private IRagularUserAccountService ragularUserAccountService;

    @RequestMapping(value = "listRagular", method = {
            RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("ragular_list")
    public String pageQuery(
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute RegularUserAccountCondition regularUserAccountCondition,
            Model model) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (condition != null && condition.length() > 0) {
            regularUserAccountCondition = gson.fromJson(condition, RegularUserAccountCondition.class);
        }
        Searchable searchable = Searchable.newSearchable();
        searchable.addSearchFilters(regularUserAccountCondition.getAndFilters());
        searchable.setPage(pageCurrent, pageSize);
        searchable.addSort(new Sort(Sort.Direction.ASC, "expireTime"));
        model.addAttribute("condition", gson.toJson(regularUserAccountCondition));
        model.addAttribute("page", ragularUserAccountService.findAll(searchable));
        setTotalFund(model, regularUserAccountCondition);
        return "traderecord/ragular/list";
    }


    public void setTotalFund(Model model, RegularUserAccountCondition regularUserAccountCondition) {
        model.addAttribute("totalFund", ragularUserAccountService.
                findTotalFund(regularUserAccountCondition.getStartTimeLong(),
                        regularUserAccountCondition.getEndTimeLong()));

    }

    @RequestMapping(value = "exportExcel/{condition}", method = {
            RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("ragular_list")
    public String exportExcel(
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute RegularUserAccountCondition regularUserAccountCondition,
            Model model, HttpSession session, HttpServletRequest request,
            RedirectAttributes redirectAttributes) {
        String path = request.getSession().getServletContext()
                .getRealPath("/static/download");

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        if (condition != null && condition.length() > 0) {
            regularUserAccountCondition = gson.fromJson(condition, RegularUserAccountCondition.class);
        }
        Map<String, Object> filter = regularUserAccountCondition.getSqlFilter();
        List<Object[]> list = ragularUserAccountService.findExportData(filter);
        String fileName = ragularUserAccountService.exportExcele(list, path);
        redirectAttributes.addFlashAttribute("fileName", fileName);
        return "redirect:/traderecord/ragular/listRagular";
    }


    public IRagularUserAccountService getRagularUserAccountService() {
        return ragularUserAccountService;
    }

    public void setRagularUserAccountService(IRagularUserAccountService ragularUserAccountService) {
        this.ragularUserAccountService = ragularUserAccountService;
    }
}
