package com.ydzb.web.traderecord.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.packet.service.IUserRedPacketService;
import com.ydzb.web.traderecord.condition.RedPacketRecordCondition;
import org.apache.shiro.authz.annotation.Logical;
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
 * 红包操作日志控制层
 *
 * @author
 */
@Controller
@RequestMapping("traderecord/redpacket")
public class RedPacketRecordController extends BaseController {

    @Autowired
    private IUserRedPacketService userRedPacketService;

    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions(value = { "usermoney_listinfo", "redpacketrecord_list" }, logical = Logical.OR)
    public String pageQuery(
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute RedPacketRecordCondition redPacketRecordCondition,
            Model model) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (condition != null && condition.length() > 0) {
            redPacketRecordCondition = gson.fromJson(condition, RedPacketRecordCondition.class);
        }

        Searchable searchable = Searchable.newSearchable();
        searchable.addSearchFilters(redPacketRecordCondition.getAndFilters());
        searchable.setPage(pageCurrent, pageSize);
        searchable.addSort(new Sort(Sort.Direction.DESC, "created"));
        model.addAttribute("condition", gson.toJson(redPacketRecordCondition));
        model.addAttribute("page", userRedPacketService.findAll(searchable));
        return "traderecord/redpacket/list";
    }

    /**
     * 导出excel
     * @param condition
     * @param redPacketRecordCondition
     * @param session
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "exportExcel/{condition}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions(value = { "usermoney_listinfo", "redpacketrecord_list" }, logical = Logical.OR)
    public String exportExcel( @PathVariable(value = "condition") String condition, RedPacketRecordCondition redPacketRecordCondition,
                HttpSession session, RedirectAttributes redirectAttributes) {
        String logoRealPathDir = session.getServletContext().getRealPath("/static/download");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (condition != null && condition.length() > 0) {
            redPacketRecordCondition = gson.fromJson(condition, RedPacketRecordCondition.class);
        }
        Map<String, Object> filters = redPacketRecordCondition.getSqlFilter();
        List<Object[]> list = userRedPacketService.findExportData(filters);
        String fileName = userRedPacketService.exportExcel(list, logoRealPathDir);
        redirectAttributes.addFlashAttribute("fileName", fileName);
        //session.setAttribute("message", "导出成功");
        return "redirect:/traderecord/redpacket/list";
    }
}