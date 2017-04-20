package com.ydzb.account.quartz;


import com.ydzb.account.service.IWmProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自动发布定存宝
 */
public class AutoRagularProductInfoQuartz {


    @Autowired
    private IWmProductInfoService productInfoService;

    public void accountJob() {
        try {
            //九点发布定存宝
            productInfoService.autoRagularProductInfo();
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
