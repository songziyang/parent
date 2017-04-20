package com.ydzb.account.quartz;


import com.ydzb.account.service.IWmProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 活期宝产品自动发布
 */
public class AutoCurrentProductInfoQuartz {


    @Autowired
    private IWmProductInfoService productInfoService;


    /**
     * 上午自动发布活期产品
     */
    public void accountMorningJob() {
        try {

            //自动发布活期宝产品
            productInfoService.autoCurrentProductInfo();

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
