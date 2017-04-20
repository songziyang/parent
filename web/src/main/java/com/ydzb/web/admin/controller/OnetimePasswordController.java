package com.ydzb.web.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.admin.entity.Admin;
import com.ydzb.admin.entity.OnetimePassword;
import com.ydzb.admin.service.IAdminService;
import com.ydzb.admin.service.IOnetimePasswordService;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.web.admin.condition.OnetimePasswordCondition;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 数字令牌controller
 */
@Controller
@RequestMapping("admin/onetimepassword")
public class OnetimePasswordController extends BaseController {

    @Autowired
    private IOnetimePasswordService onetimePasswordService;
    @Autowired
    private IAdminService adminService;

    // 查询
    @RequestMapping(value = "list", method = { RequestMethod.GET,
            RequestMethod.POST })
    @RequiresPermissions("onetimepassword_list")
    public String pageQuery(
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute OnetimePasswordCondition onetimePasswordCondition, Model model) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        if (condition != null && condition.length() > 0) {
            onetimePasswordCondition = gson.fromJson(condition, OnetimePasswordCondition.class);
        }

        Searchable searchable = Searchable.newSearchable();
        searchable.addSearchFilters(onetimePasswordCondition.getAndFilters());
        searchable.setPage(pageCurrent, pageSize);
        // 设置排序条件
        searchable.addSort(new Sort(Sort.Direction.DESC, "created"));
        model.addAttribute("condition", gson.toJson(onetimePasswordCondition));
        model.addAttribute("page", onetimePasswordService.findAll(searchable));
        return "admin/onetimepassword/list";
    }

    // 添加
    @RequestMapping(value = "create", method = { RequestMethod.GET, RequestMethod.POST })
    @RequiresPermissions("onetimepassword_create")
    public String create(Model model) {
        return "admin/onetimepassword/edit";
    }

    // 保存
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @RequiresPermissions(value = { "onetimepassword_create", "onetimepassword_edit" }, logical = Logical.OR)
    public String save(HttpSession session, OnetimePassword onetimePassword, Model model) throws Exception {
        String result = onetimePasswordService.saveOne(onetimePassword);
        if ("repeat".equals(result)) {
            session.setAttribute("message", "数字令牌号或密钥重复！");
            if (onetimePassword.getId() != null) {
                model.addAttribute("onetimePassword", onetimePasswordService.findOne(onetimePassword.getId()));
            }
            return "admin/onetimepassword/edit";
        }
        session.setAttribute("message", "保存成功！");
        return "redirect:/admin/onetimepassword/list";
    }

    // 编辑
    @RequestMapping(value = "edit/{number}", method = { RequestMethod.GET,
            RequestMethod.POST })
    @RequiresPermissions("onetimepassword_edit")
    public String editAdmin(@PathVariable Long number, Model model) {
        model.addAttribute("onetimePassword", onetimePasswordService.findOne(number));
        return "admin/onetimepassword/edit";
    }

    /**
     * 查找后台用户
     * @param condition
     * @return
     */
    @RequestMapping(value = "findAdmin", method = { RequestMethod.GET,
            RequestMethod.POST }, produces = "application/json; charset=utf-8")
    @RequiresPermissions(value = { "onetimepassword_create", "onetimepassword_edit" }, logical = Logical.OR)
    @ResponseBody
    public String findAdmin(final String condition) {
        Admin admin = adminService.findAdminByUsername(condition);
        if (admin != null) {
            Map<String, Object> jsonMap = new HashMap<String, Object>();
            jsonMap.put("id", admin.getId());
            jsonMap.put("username", admin.getUsername());
            return JSONObject.toJSONString(jsonMap);
        }
        return null;
    }

    /**
     * 删除
     * @param id
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "delete/{id}", method = { RequestMethod.GET,
            RequestMethod.POST })
    @RequiresPermissions("onetimepassword_del")
    public String delete(@PathVariable Long id, HttpSession session)
            throws Exception {
        onetimePasswordService.deleteOne(id);
        session.setAttribute("message", DELETE_SUCCESS);
        return "redirect:/admin/onetimepassword/list";
    }

    /**
     * 同步
     * @param session
     * @param otpId
     * @param password1
     * @param password2
     * @return
     */
    @RequestMapping(value = "sync", method = {RequestMethod.GET, RequestMethod.POST })
    @RequiresPermissions("onetimepassword_sync")
    public String sync(HttpSession session, Long otpId, String password1, String password2) {
        session.setAttribute("message", onetimePasswordService.sync(otpId, password1, password2));
        return "redirect:/admin/onetimepassword/list";
    }
}