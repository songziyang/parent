package com.ydzb.web.user.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.user.service.IChannelManagerService;
import com.ydzb.web.user.condition.ChannelManagerCondition;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping(value = "userinfo/channelmanager")
public class ChannelManagerController extends BaseController {

    @Autowired
    private IChannelManagerService channelManagerService;

    @RequestMapping(value = "listChannelManager", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("channelmanager_list")
    public String listPopularize(
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute ChannelManagerCondition channelManagerCondition,
            Model model) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        if (condition != null && condition.length() > 0) {
            channelManagerCondition = gson.fromJson(condition, ChannelManagerCondition.class);
        }

        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        // 添加查询条件
        searchable.addSearchFilters(channelManagerCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        // 设置排序条件
        searchable.addSort(new Sort(Sort.Direction.DESC, "created"));
        model.addAttribute("condition", gson.toJson(channelManagerCondition));
        model.addAttribute("page", channelManagerService.findAll(searchable));
        return "userinfo/channelmanager/list";
    }

    // 授权
    @RequestMapping(value = "channelmanager/{type}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("channelmanager_edit")
    public String authorization(@PathVariable("type") Long type, Model model) {
        model.addAttribute("type", type);
        return "userinfo/channelmanager/author";
    }


    @RequestMapping(value = "saveAuthorization", method = RequestMethod.POST)
    @RequiresPermissions("channelmanager_edit")
    public String saveAuthorization(Integer type, String[] limitinfos, String promptInfo, HttpSession session) throws Exception {
        channelManagerService.saveAuthorization(type, limitinfos,promptInfo);
        session.setAttribute("message", "保存成功");
        return "redirect:/userinfo/channelmanager/listChannelManager";
    }

    // 删除
    @RequestMapping(value = "deleteChannel/{id}", method = {RequestMethod.GET,
            RequestMethod.POST})
    @RequiresPermissions("channelmanager_del")
    public String deleteChannel(@PathVariable("id") Long id, HttpSession session) throws Exception {
        channelManagerService.updateChannelManager(id);
        session.setAttribute("message", "解除成功");
        return "redirect:/userinfo/channelmanager/listChannelManager";
    }


    public IChannelManagerService getChannelManagerService() {
        return channelManagerService;
    }

    public void setChannelManagerService(IChannelManagerService channelManagerService) {
        this.channelManagerService = channelManagerService;
    }
}