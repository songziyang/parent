package com.ydzb.account.service;


public interface IWmProductInfoService {

    /**
     * 自动发布活期宝
     * @throws Exception
     */
    void autoCurrentProductInfo() throws Exception;


    /**
     * 自动发布半月宝
     *
     * @throws Exception
     */
    void autoShortRagularProductInfo() throws Exception;


    /**
     * 定存宝自动发布
     *
     * @throws Exception
     */
    void autoRagularProductInfo() throws Exception;


    /**
     * 产品自动清零
     *
     * @throws Exception
     */
    void autoClearProductInfo() throws Exception;
}
