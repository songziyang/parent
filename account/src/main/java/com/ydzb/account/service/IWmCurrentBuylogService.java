package com.ydzb.account.service;

import com.ydzb.account.entity.WmCurrentBuylog;

import java.math.BigDecimal;

/**
 * 活期宝购买日志service接口
 */
public interface IWmCurrentBuylogService {

    /**
     * 创建
     * @param productId 产品id
     * @param userId 用户id
     * @param buyFund 购买资金
     * @param expFund 体验金
     * @param buyCopies 购买份数
     * @param apr 利率
     * @param grandApr 加息利率
     * @param buyTime 购买时间
     * @param device 类型
     * @return
     */
    WmCurrentBuylog createOne(Long productId, Long userId, BigDecimal buyFund, BigDecimal expFund,
            Integer buyCopies, BigDecimal apr, BigDecimal grandApr, Long buyTime, Integer device);
}
