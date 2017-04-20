package com.ydzb.web.activity.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.packet.service.IRewardRecordService;
import com.ydzb.web.activity.condition.RewardRecordCondition;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 周年中奖记录
 */
@Controller
@RequestMapping("activity/rewardrecord")
public class RewardRecordController extends BaseController {

    @Autowired
    private IRewardRecordService rewardRecordService;

    /**
     * 列表
     * @param pageSize
     * @param pageCurrent
     * @param condition
     * @param rewardCondition
     * @param model
     * @return
     */
    @RequestMapping(value = "list", method = { RequestMethod.GET,
            RequestMethod.POST })
    @RequiresPermissions("rewardrecord_list")
    public String pageQuery(
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute RewardRecordCondition rewardCondition, Model model) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        if (condition != null && condition.length() > 0) {
            rewardCondition = gson.fromJson(condition, RewardRecordCondition.class);
        }
        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        // 添加查询条件
        searchable.addSearchFilters(rewardCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        // 设置排序条件
        searchable.addSort(new Sort(Sort.Direction.DESC, "created"));
        model.addAttribute("condition", gson.toJson(rewardCondition));
        model.addAttribute("page", rewardRecordService.findAll(searchable));
        return "activity/rewardrecord/list";
    }

    /**
     * 导出excel
     * @param condition
     * @param rewardCondition
     * @param session
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "exportExcel/{condition}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("rewardrecord_list")
    public String exportExcel( @PathVariable(value = "condition") String condition, RewardRecordCondition rewardCondition,
            HttpSession session, RedirectAttributes redirectAttributes) {
        String logoRealPathDir = session.getServletContext().getRealPath("/static/download");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (condition != null && condition.length() > 0) {
            rewardCondition = gson.fromJson(condition, RewardRecordCondition.class);
        }
        Map<String, Object> filters = rewardCondition.getSqlFilter();
        List<Object[]> list = rewardRecordService.findExportData(filters);
        String fileName = rewardRecordService.exportExcel(list, logoRealPathDir);
        redirectAttributes.addFlashAttribute("fileName", fileName);
        //session.setAttribute("message", "导出成功");
        return "redirect:/activity/rewardrecord/list";
    }
}
