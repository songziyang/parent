package com.ydzb.web.platform.controller;

import com.alibaba.druid.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.product.service.IPlatformFundRecordService;
import com.ydzb.product.service.IPlatformUserService;
import com.ydzb.product.service.IVPlatformRecordService;
import com.ydzb.web.platform.condition.PlatformFundRecordCondition;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;


/**
 * 平台统计controller
 */
@Controller
@RequestMapping("platform/platformuser")
public class PlatformUserController extends BaseController {

    @Autowired
    private IPlatformUserService platformUserService;

    @Autowired
    private IVPlatformRecordService platformRecordService;

    /**
     * 列表
     * @param model
     * @return
     */
    @RequestMapping(value = "list", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("platformuser_list")
    public String pageQuery(@RequestParam(defaultValue = PAGE_SIZE) int pageSize,
                            @RequestParam(defaultValue = PAGE_CURRENT) int pageCurrent,
                            @ModelAttribute("condition") String condition, Model model,
                            @ModelAttribute("platformFundRecordCondition") PlatformFundRecordCondition platformFundRecordCondition) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (!StringUtils.isEmpty(condition)) {
            platformFundRecordCondition = gson.fromJson(condition, PlatformFundRecordCondition.class);
        }
        //创建查询
        Searchable searchable = Searchable.newSearchable();
        //添加查询条件
        searchable.addSearchFilters(platformFundRecordCondition.getAndFilters());
        //设置排序条件
        searchable.addSort(new Sort(Sort.Direction.DESC, "optime"));
        //设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        model.addAttribute("condition", gson.toJson(platformFundRecordCondition));
        model.addAttribute("page", platformRecordService.findAll(searchable));
        model.addAttribute("platformUser", platformUserService.queryLastOne());
        return "product/platformuser/list";
    }


    /**
     * 充值
     * @param platformUserId 充值用户id
     * @param fund 充值金额
     * @param session
     * @return
     */
    @RequestMapping(value = "recharge", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("platformuser_recharge")
    public String recharge(Long platformUserId, BigDecimal fund, String desc, HttpSession session) {
        try {
            String msg = platformUserService.recharge(platformUserId, fund, desc);
            if ("success".equals(msg)) {
                session.setAttribute("message", "充值成功");
            } else {
                session.setAttribute("error", msg);
            }
        } catch (Exception e) {
            session.setAttribute("error", "充值失败");
        }
        return "redirect:/platform/platformuser/list";
    }

    /**
     * 购买债权
     * @param platformUserId 充值用户id
     * @param fund 购买金额
     * @param session
     * @return
     */
    @RequestMapping(value = "buyDebt", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("platformuser_buydebt")
    public String buyDebt(Long platformUserId, BigDecimal fund, String desc, HttpSession session) {
        try {
            String msg = platformUserService.buyDebt(platformUserId, fund, desc);
            if ("success".equals(msg)) {
                session.setAttribute("message", "债权购买成功");
            } else {
                session.setAttribute("error", msg);
            }
        } catch (Exception e) {
            session.setAttribute("error", "债权购买失败");
        }
        return "redirect:/platform/platformuser/list";
    }

    /**
     * 售出债权
     * @param platformUserId 充值用户id
     * @param fund 售出份额
     * @param session
     * @return
     */
    @RequestMapping(value = "sellDebt", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("platformuser_selldebt")
    public String sellDebt(Long platformUserId, BigDecimal fund, String desc, HttpSession session) {
        try {
            String msg = platformUserService.sellDebt(platformUserId, fund, desc);
            if ("success".equals(msg)) {
                session.setAttribute("message", "债权售出成功");
            } else {
                session.setAttribute("error", msg);
            }
        } catch (Exception e) {
            session.setAttribute("error", "债权售出失败");
        }
        return "redirect:/platform/platformuser/list";
    }


    public IPlatformUserService getPlatformUserService() {
        return platformUserService;
    }

    public void setPlatformUserService(IPlatformUserService platformUserService) {
        this.platformUserService = platformUserService;
    }

    public IVPlatformRecordService getPlatformRecordService() {
        return platformRecordService;
    }

    public void setPlatformRecordService(IVPlatformRecordService platformRecordService) {
        this.platformRecordService = platformRecordService;
    }
}