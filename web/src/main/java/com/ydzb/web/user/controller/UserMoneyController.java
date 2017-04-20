package com.ydzb.web.user.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ydzb.core.controller.BaseController;
import com.ydzb.core.entity.search.Searchable;
import com.ydzb.product.service.ICurrentQueueService;
import com.ydzb.user.entity.User;
import com.ydzb.user.service.IUserFundBlokedLogService;
import com.ydzb.user.service.IUserFundRecordService;
import com.ydzb.user.service.IUserService;
import com.ydzb.user.service.IVipGradeService;
import com.ydzb.web.user.condition.UserFundRecordCondition;
import com.ydzb.web.user.condition.UserMoneyCondition;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "userinfo/usermoneyinfo")
public class UserMoneyController extends BaseController {


    @Autowired
    private IUserService userService;

    @Autowired
    private IUserFundRecordService userFundRecordService;

    @Autowired
    private IVipGradeService vipGradeService;

    @Autowired
    private ICurrentQueueService currentQueueService;

    @Autowired
    private IUserFundBlokedLogService userFundBlokedLogService;


    /**
     * 用户资金列表
     *
     * @param pageSize
     * @param pageCurrent
     * @param userCondition
     * @param model
     * @return
     */
    @RequestMapping(value = "usermoneylist", method = {RequestMethod.GET,
            RequestMethod.POST})
    @RequiresPermissions("usermoney_listinfo")
    public String pageQuery(
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute UserMoneyCondition userCondition, Model model)
            throws Exception {

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();

        if (condition != null && condition.length() > 0) {
            userCondition = gson.fromJson(condition, UserMoneyCondition.class);
        }

        // 创建查询
        Searchable searchable = Searchable.newSearchable();
        // 添加查询条件
        searchable.addSearchFilters(userCondition.getAndFilters());
        // 设置分页参数
        searchable.setPage(pageCurrent, pageSize);
        // 设置排序条件
        addSort(searchable, userCondition);
        model.addAttribute("condition", gson.toJson(userCondition));
        model.addAttribute("page", userService.findAll(searchable));


        model.addAttribute("vipGrades", vipGradeService.findAll(new Sort(Sort.Direction.ASC, "id")));

        return "userinfo/user/listusermoney";
    }


    @RequestMapping(value = "exportExcelFund/{condition}", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("usermoney_exportexcel")
    public String exportExcelFund(
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute UserMoneyCondition userCondition,
            Model model, HttpSession session, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String path = request.getSession().getServletContext().getRealPath("/static/download");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();

        if (condition != null && condition.length() > 0) {
            userCondition = gson.fromJson(condition, UserMoneyCondition.class);
        }
        Map<String, Object> filter = userCondition.getSqlFilters();
        List<Object[]> list = userService.findMoneyExportData(filter);
        String fileName = userService.exportUserMoney(list, path);
        redirectAttributes.addFlashAttribute("fileName", fileName);

        return "redirect:/userinfo/usermoneyinfo/usermoneylist";
    }



    /**
     * 根据用户ID 获取用户账户操作
     *
     * @param id
     * @param model
     * @param pageSize
     * @param pageCurrent
     * @param condition
     * @return
     */
    @RequestMapping(value = "findUserFundRecord/{id}")
    @RequiresPermissions("usermoney_fundrecordlist")
    public String findOperationByUserID(
            @PathVariable(value = "id") Long id,
            Model model,
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE) int pageSize,
            @RequestParam(value = "pageCurrent", defaultValue = PAGE_CURRENT) int pageCurrent,
            @ModelAttribute(value = "condition") String condition,
            @ModelAttribute UserFundRecordCondition userFundRecordCondition) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        if (condition != null && condition.length() > 0) {
            userFundRecordCondition = gson.fromJson(condition,  UserFundRecordCondition.class);
        }
        Searchable searchable = Searchable.newSearchable();
        userFundRecordCondition.setUserId(id);
        searchable.addSearchFilters(userFundRecordCondition.getAndFilters());
        searchable.setPage(pageCurrent, pageSize);
        searchable.addSort(new Sort(Sort.Direction.DESC, "id"));
        model.addAttribute("condition", gson.toJson(userFundRecordCondition));
        model.addAttribute("page", userFundRecordService.findAll(searchable));
        model.addAttribute("user", userService.findOne(id));
        return "userinfo/user/listuseroperated";
    }




    /**
     * 查询用户冻结资金
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "findBlokedlogByUserID/{id}")
    @RequiresPermissions("usermoney_listinfo")
    public String findBlokedlogByUserID( @PathVariable(value = "id") Long id,Model model) {
        model.addAttribute("userId",id);
        model.addAttribute("user",userService.findOne(id));
        model.addAttribute("blokedlogLst",   userFundBlokedLogService.findUserFundBlokedLogByType(id));
        return "userinfo/blocked/money";
    }


    @RequestMapping(value = "findExBlokedlogByUserID/{id}")
    @RequiresPermissions("usermoney_listinfo")
    public String findExBlokedlogByUserID( @PathVariable(value = "id") Long id,Model model) {
        model.addAttribute("userId",id);
        model.addAttribute("user",userService.findOne(id));
        model.addAttribute("blokedlogLst",   userFundBlokedLogService.findExUserFundBlokedLogByType(id));
        return "userinfo/blocked/exmoney";
    }



    //排序
    public void addSort(Searchable searchable, UserMoneyCondition userCondition) {
        if (userCondition.getOrderNumber() != null) {
            switch (userCondition.getOrderNumber()) {
                //资金总额
                case 1:
                    if ("desc".equals(userCondition.getOrderSort())) {
                        searchable.addSort(new Sort(Sort.Direction.DESC, "userMoney.totalFund"));
                    } else {
                        searchable.addSort(new Sort(Sort.Direction.ASC, "userMoney.totalFund"));
                    }
                    break;
                //资金余额
                case 2:
                    if ("desc".equals(userCondition.getOrderSort())) {
                        searchable.addSort(new Sort(Sort.Direction.DESC, "userMoney.usableFund"));
                    } else {
                        searchable.addSort(new Sort(Sort.Direction.ASC, "userMoney.usableFund"));
                    }
                    break;
                //活期宝投资总额
                case 3:
                    if ("desc".equals(userCondition.getOrderSort())) {
                        searchable.addSort(new Sort(Sort.Direction.DESC, "userInvestinfo.allInvestDayloan"));
                    } else {
                        searchable.addSort(new Sort(Sort.Direction.ASC, "userInvestinfo.allInvestDayloan"));
                    }
                    break;
                //定存宝投资总额
                case 4:
                    if ("desc".equals(userCondition.getOrderSort())) {
                        searchable.addSort(new Sort(Sort.Direction.DESC, "userInvestinfo.allInvestDeposit"));
                    } else {
                        searchable.addSort(new Sort(Sort.Direction.ASC, "userInvestinfo.allInvestDeposit"));
                    }
                    break;
                //定存转让金额
                case 5:
                    if ("desc".equals(userCondition.getOrderSort())) {
                        searchable.addSort(new Sort(Sort.Direction.DESC, "userInvestinfo.allInvestTransfer"));
                    } else {
                        searchable.addSort(new Sort(Sort.Direction.ASC, "userInvestinfo.allInvestTransfer"));
                    }
                    break;
                //稳进宝金额
                case 6:
                    if ("desc".equals(userCondition.getOrderSort())) {
                        searchable.addSort(new Sort(Sort.Direction.DESC, "userInvestinfo.allInvestWjb"));
                    } else {
                        searchable.addSort(new Sort(Sort.Direction.ASC, "userInvestinfo.allInvestWjb"));
                    }
                    break;
                //体验金总额
                case 7:
                    if ("desc".equals(userCondition.getOrderSort())) {
                        searchable.addSort(new Sort(Sort.Direction.DESC, "userExMoney.amount"));
                    } else {
                        searchable.addSort(new Sort(Sort.Direction.ASC, "userExMoney.amount"));
                    }
                    break;
                //体验金余额
                case 8:
                    if ("desc".equals(userCondition.getOrderSort())) {
                        searchable.addSort(new Sort(Sort.Direction.DESC, "userExMoney.ableMoney"));
                    } else {
                        searchable.addSort(new Sort(Sort.Direction.ASC, "userExMoney.ableMoney"));
                    }
                    break;
                //资金冻结金额
                case 9:
                    if ("desc".equals(userCondition.getOrderSort())) {
                        searchable.addSort(new Sort(Sort.Direction.DESC, "userMoney.freezeFund"));
                    } else {
                        searchable.addSort(new Sort(Sort.Direction.ASC, "userMoney.freezeFund"));
                    }
                    break;
                case 10:
                    if ("desc".equals(userCondition.getOrderSort())) {
                        searchable.addSort(new Sort(Sort.Direction.DESC, "userExMoney.blockedMoney"));
                    } else {
                        searchable.addSort(new Sort(Sort.Direction.ASC, "userExMoney.blockedMoney"));
                    }
                    break;
                //用户总收益
                case 11:
                    if ("desc".equals(userCondition.getOrderSort())) {
                        searchable.addSort(new Sort(Sort.Direction.DESC, "userIncome.allIncome"));
                    } else {
                        searchable.addSort(new Sort(Sort.Direction.ASC, "userIncome.allIncome"));
                    }
                    break;
                //随心存总投资
                case 12:
                    if ("desc".equals(userCondition.getOrderSort())) {
                        searchable.addSort(new Sort(Sort.Direction.DESC, "userInvestinfo.allInvestFree"));
                    } else {
                        searchable.addSort(new Sort(Sort.Direction.ASC, "userInvestinfo.allInvestFree"));
                    }
                    break;
            }
        } else {
            searchable.addSort(new Sort(Sort.Direction.DESC, "userMoney.totalFund"));
        }

    }





    public IUserService getUserService() {
        return userService;
    }


    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    public IUserFundRecordService getUserFundRecordService() {
        return userFundRecordService;
    }

    public void setUserFundRecordService(IUserFundRecordService userFundRecordService) {
        this.userFundRecordService = userFundRecordService;
    }

    public IVipGradeService getVipGradeService() {
        return vipGradeService;
    }

    public void setVipGradeService(IVipGradeService vipGradeService) {
        this.vipGradeService = vipGradeService;
    }

    public ICurrentQueueService getCurrentQueueService() {
        return currentQueueService;
    }

    public void setCurrentQueueService(ICurrentQueueService currentQueueService) {
        this.currentQueueService = currentQueueService;
    }

    public IUserFundBlokedLogService getUserFundBlokedLogService() {
        return userFundBlokedLogService;
    }

    public void setUserFundBlokedLogService(IUserFundBlokedLogService userFundBlokedLogService) {
        this.userFundBlokedLogService = userFundBlokedLogService;
    }
}
