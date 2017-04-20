package com.ydzb.web.traderecord.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.product.entity.DepositTypeExpire;
import com.ydzb.product.service.IDepositTypeExpireService;
import com.ydzb.web.traderecord.condition.DepositTypeExpireCondition;
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

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "traderecord/depositexpire")
public class DepositTypeExpireController extends BaseController {

    @Autowired
    private IDepositTypeExpireService depositTypeExpireService;

    @RequestMapping(value = "listDepositExpire", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("depositexpire_list")
    public String pageQueryMatchPlans(
            @RequestParam(value = "pageSize", defaultValue = "15") int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute DepositTypeExpireCondition depositTypeExpireCondition, Model model) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (condition != null && condition.length() > 0) {
            depositTypeExpireCondition = gson.fromJson(condition, DepositTypeExpireCondition.class);
        }

        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        // 添加查询条件
        searchable.addSearchFilters(depositTypeExpireCondition.getAndFilters());
        searchable.setPage(pageCurrent, pageSize);
        // 设置排序条件
        searchable.addSort(new Sort(Direction.DESC, "optime"));

        initData(depositTypeExpireCondition);

        model.addAttribute("condition", gson.toJson(depositTypeExpireCondition));
        model.addAttribute("page", depositTypeExpireService.findAll(searchable));

        return "traderecord/depositexpire/list";
    }

    public void initData(DepositTypeExpireCondition condition) {
        depositTypeExpireService.findDataBetweenDate(condition.getStartDate(), condition.getEndDate(), condition.getType());
    }



    @RequestMapping(value = "exportExcel", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("depositexpire_list")
    public String export(@ModelAttribute DepositTypeExpireCondition depositTypeExpireCondition,
                         HttpSession session, RedirectAttributes redirectAttributes) {
        String logoRealPathDir = session.getServletContext().getRealPath("/static/download");
        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        // 添加查询条件
        searchable.addSearchFilters(depositTypeExpireCondition.getAndFilters());
        // 设置排序条件
        searchable.addSort(new Sort(Direction.DESC, "optime"));
        List<DepositTypeExpire> data = depositTypeExpireService.findAllWithSort(searchable);
        String fileName = depositTypeExpireService.exportExcel(data, logoRealPathDir);
        redirectAttributes.addFlashAttribute("fileName", fileName);

        return "redirect:/traderecord/depositexpire/listDepositExpire";
    }


    public IDepositTypeExpireService getDepositTypeExpireService() {
        return depositTypeExpireService;
    }

    public void setDepositTypeExpireService(IDepositTypeExpireService depositTypeExpireService) {
        this.depositTypeExpireService = depositTypeExpireService;
    }
}
