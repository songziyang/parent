package com.ydzb.web.message.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.message.service.IMessageService;
import com.ydzb.sms.entity.Message;
import com.ydzb.web.message.condition.MessageCondition;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "infoMessage/message")
public class MessageController extends BaseController {

    @Autowired
    private IMessageService messageService;

    @RequestMapping(value = "listMessage", method = {RequestMethod.GET,
            RequestMethod.POST})
    @RequiresPermissions("message_list")
    public String pageQuery(
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute MessageCondition messageCondition, Model model) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        if (condition != null && condition.length() > 0) {
            messageCondition = gson.fromJson(condition,
                    MessageCondition.class);
        }
        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        // 添加查询条件
        searchable.addSearchFilters(messageCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        // 设置排序条件
        searchable.addSort(new Sort(Direction.DESC, "created"));
        model.addAttribute("condition", gson.toJson(messageCondition));
        model.addAttribute("page", messageService.findAll(searchable));
        return "infoMessage/message/list";
    }

    // 添加
    @RequestMapping(value = "createMessage", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("message_create")
    public String createMessage(Model model) {
        return "infoMessage/message/edit";
    }

    // 编辑
    @RequestMapping(value = "editMessage/{id}", method = {RequestMethod.GET,
            RequestMethod.POST})
    @RequiresPermissions("message_edit")
    public String editAdmin(@PathVariable("id") Long id, Model model) {
        model.addAttribute("messages", messageService.findOne(id));
        return "infoMessage/message/edit";
    }

    // 删除
    @RequestMapping(value = "deleteMessage/{id}", method = {RequestMethod.GET,
            RequestMethod.POST})
    @RequiresPermissions("message_del")
    public String delete(@PathVariable("id") Long id, HttpSession session)
            throws Exception {
        messageService.delete(id);
        session.setAttribute("message", "删除成功");
        return "redirect:/infoMessage/message/listMessage";
    }

    // 批量删除
    @RequestMapping(value = "deleteMessages", method = {RequestMethod.GET,
            RequestMethod.POST})
    @RequiresPermissions("message_del")
    public String deleteMessages(Long[] ids, HttpSession session)
            throws Exception {
        messageService.delete(ids);
        session.setAttribute("message", "删除成功");
        return "redirect:/infoMessage/message/listMessage";
    }

    // 保存
    @RequestMapping(value = "saveMessage", method = RequestMethod.POST)
    @RequiresPermissions(value = {"message_create", "message_edit"}, logical = Logical.OR)
    public String save(String username, Message messages, HttpSession session,
                       Model model) throws Exception {

        String result = messageService.saveMessage(messages);
        if (StringUtils.isEmpty(result)) {
            session.setAttribute("message", SAVE_SUCCESS);
            return "redirect:/infoMessage/message/listMessage";
        } else {
            session.setAttribute("error", result);
            model.addAttribute("messages", messages);
            return "infoMessage/message/edit";
        }
    }

    public IMessageService getMessageService() {
        return messageService;
    }

    public void setMessageService(IMessageService messageService) {
        this.messageService = messageService;
    }

}
