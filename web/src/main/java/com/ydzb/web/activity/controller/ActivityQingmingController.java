package com.ydzb.web.activity.controller;

import com.ydzb.core.controller.BaseController;
import com.ydzb.product.service.IActivityQingmingService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 清明活动controller
 */
@Controller
@RequestMapping("activity/qingming")
public class ActivityQingmingController extends BaseController {

    @Autowired
    private IActivityQingmingService activityQingmingService;

    /**
     * 增加抽奖次数
     * @param userId 用户id
     * @return 结果状态
     *         max:无法继续添加
     *         其余的是添加之后的次数
     */
    @ResponseBody
    @RequestMapping(value = "addSignNumber/{userId}",
            method = { RequestMethod.GET, RequestMethod.POST },
            produces = "application/json; charset=utf-8")
    @RequiresPermissions("user_actqingming")
    public String addSignNumber(@PathVariable Long userId) {
        return activityQingmingService.addSignNum(userId);
    }
}