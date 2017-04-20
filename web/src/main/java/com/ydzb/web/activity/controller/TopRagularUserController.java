package com.ydzb.web.activity.controller;

import com.ydzb.core.controller.BaseController;
import com.ydzb.product.service.IRagularUserAccountService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


 /**
  * 定存排行活动（2016年1月）
  */
@Controller
@RequestMapping("activity/topRagularUser")
public class TopRagularUserController extends BaseController {

    @Autowired
    private IRagularUserAccountService ragularUserAccountService;

    @RequestMapping(value = "list", method = { RequestMethod.GET, RequestMethod.POST })
    @RequiresPermissions("topragular_list")
    public String topRagularUserList(Model model) {
        model.addAttribute("page", ragularUserAccountService.findTopRagularUser());
        return "activity/topragularuser/list";
    }
}
