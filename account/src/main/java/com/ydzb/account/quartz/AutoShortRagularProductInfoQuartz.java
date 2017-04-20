package com.ydzb.account.quartz;


import com.ydzb.account.service.IWmProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自动发布半月标
 */
public class AutoShortRagularProductInfoQuartz {


    @Autowired
    private IWmProductInfoService productInfoService;

    public void accountJob() {
        try {
            //自动发布半月标
            productInfoService.autoShortRagularProductInfo();
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
