package com.ydzb.web.withdraw.controller;

import com.ydzb.core.controller.BaseController;
import com.ydzb.withdraw.entity.ManualBalance;
import com.ydzb.withdraw.service.IPayManualRequestService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping(value = "userwithdraw/manualbalance")
public class ManualBalanceController extends BaseController {

    @Autowired
    private IPayManualRequestService payManualRequestService;


    @RequestMapping(value = "listManualBalance", method = {RequestMethod.GET, RequestMethod.POST})
    @RequiresPermissions("manualbalance_list")
    public String pageQuery(Model model, HttpSession httpSession) throws Exception {
        ManualBalance manualBalance = payManualRequestService.queryAccountBalance();
        if (manualBalance != null) {
            httpSession.setAttribute("manualBalance", manualBalance);
        } else {
            manualBalance = (ManualBalance) httpSession.getAttribute("manualBalance");
        }
        model.addAttribute("manualBalance", manualBalance);
        return "userwithdraw/balance/list";
    }

    public IPayManualRequestService getPayManualRequestService() {
        return payManualRequestService;
    }

    public void setPayManualRequestService(IPayManualRequestService payManualRequestService) {
        this.payManualRequestService = payManualRequestService;
    }
}
