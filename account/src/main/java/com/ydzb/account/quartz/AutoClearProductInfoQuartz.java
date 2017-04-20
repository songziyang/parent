package com.ydzb.account.quartz;


import com.ydzb.account.service.IWmProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 产品信息23:00 自动清零
 */
public class AutoClearProductInfoQuartz {


    @Autowired
    private IWmProductInfoService productInfoService;

    public void accountJob() {
        try {
            productInfoService.autoClearProductInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public IWmProductInfoService getProductInfoService() {
        return productInfoService;
    }

    public void setProductInfoService(IWmProductInfoService productInfoService) {
        this.productInfoService = productInfoService;
    }
}
