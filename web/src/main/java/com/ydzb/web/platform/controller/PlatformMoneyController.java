package com.ydzb.web.platform.controller;

import com.alibaba.druid.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.product.service.ICurrentQueueService;
import com.ydzb.product.service.IPlatformAprService;
import com.ydzb.product.service.IPlatformUserService;
import com.ydzb.user.service.IMinusAmountService;
import com.ydzb.user.service.IUserService;
import com.ydzb.web.platform.condition.PlatformAprCondition;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 平台资金管理
 *
 * @author sy
 */
@Controller
@RequestMapping("platform/money")
public class PlatformMoneyController extends BaseController {


    @Autowired
    private IUserService userService;

    @Autowired
    private ICurrentQueueService currentQueueService;

    @Autowired
    private IMinusAmountService minusAmountService;

    @Autowired
    private IPlatformAprService platformAprService;

    @Autowired
    private IPlatformUserService platformUserService;



    @RequestMapping(value = "listPlatformMoney", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("platformmoney_list")
    public String pageQuery(Model model) {

        model.addAttribute("hqbje", userService.findHqbje());
        model.addAttribute("hqbrs", userService.findHqbrs());
        model.addAttribute("xsbje", userService.findXsbje());
        model.addAttribute("xsbrs", userService.findXsbrs());
        model.addAttribute("dcbje", userService.findDcbje());
        model.addAttribute("dcbrs", userService.findDcbrs());
        model.addAttribute("hqbtyj", userService.findHqbtyj());
        model.addAttribute("hqbrmb", userService.findHqbrmb());
        model.addAttribute("currentPrepay", currentQueueService.querySumCopies());
        model.addAttribute("srdzje", userService.findSrdz());
        model.addAttribute("hqbkcje", minusAmountService.findSumMinusAmount());
        model.addAttribute("ztjrs", userService.findZtjrs());
        model.addAttribute("zhuanUserCount", userService.findZhuanUserCount());
        model.addAttribute("zhuanInvestFund", userService.findZhuanUserInvestFund());
        model.addAttribute("freeUserCount", userService.findFreeUserCount());
        model.addAttribute("freeInvestFund", userService.findFreeUserInvestFund());
        model.addAttribute("mljrUserCount", userService.findMljrUserCount());
        model.addAttribute("mljrInvestFund", userService.findMljrUserInvestFund());
        model.addAttribute("ydzbzlzh", platformUserService.queryLastOne().getAllInvest());

        return "product/platformmoney/platformrmoney";
    }





    @RequestMapping(value = "listPlatformApr", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("platforapr_list")
    public String pageQuery(@RequestParam(defaultValue = PAGE_SIZE) int pageSize,
                            @RequestParam(defaultValue = PAGE_CURRENT) int pageCurrent,
                            String condition, PlatformAprCondition platformAprCondition, Model model) {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        if (!StringUtils.isEmpty(condition)) {
            platformAprCondition = gson.fromJson(condition, PlatformAprCondition.class);
        }
        //创建查询
        Searchable searchable = Searchable.newSearchable();
        //添加查询条件
        searchable.addSearchFilters(platformAprCondition.getAndFilters());
        //设置排序条件
        searchable.addSort(new Sort(Sort.Direction.DESC, "created"));
        //设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        model.addAttribute("condition", gson.toJson(platformAprCondition));
        model.addAttribute("page", platformAprService.findAll(searchable));

        return "product/platformmoney/platformapr";
    }




    public IUserService getUserService() {
        return userService;
    }

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    public ICurrentQueueService getCurrentQueueService() {
        return currentQueueService;
    }

    public void setCurrentQueueService(ICurrentQueueService currentQueueService) {
        this.currentQueueService = currentQueueService;
    }

    public IMinusAmountService getMinusAmountService() {
        return minusAmountService;
    }

    public void setMinusAmountService(IMinusAmountService minusAmountService) {
        this.minusAmountService = minusAmountService;
    }

    public IPlatformAprService getPlatformAprService() {
        return platformAprService;
    }

    public void setPlatformAprService(IPlatformAprService platformAprService) {
        this.platformAprService = platformAprService;
    }

    public IPlatformUserService getPlatformUserService() {
        return platformUserService;
    }

    public void setPlatformUserService(IPlatformUserService platformUserService) {
        this.platformUserService = platformUserService;
    }
}
