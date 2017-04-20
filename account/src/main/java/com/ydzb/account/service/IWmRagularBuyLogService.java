package com.ydzb.account.service;

import com.ydzb.account.entity.WmRagularBuyLog;

import java.math.BigDecimal;

/**
 * 定存宝购买日志service接口
 */
public interface IWmRagularBuyLogService {

    /**
     * 创建
     * @param productId 产品id
     * @param accountId 定存宝账户id
     * @param userId 用户id
     * @param buyFund 购买金额
     * @param buyCopies 购买份数
     * @param apr 利率
     * @param grandApr 加息利率
     * @param vipApr vip利率
     * @param buyTime 购买时间
     * @param device 设备
     * @return
     */
    WmRagularBuyLog createOne(Long productId, Long accountId, Long userId, BigDecimal buyFund, Integer buyCopies,
        BigDecimal apr, BigDecimal grandApr, BigDecimal vipApr, Long buyTime, Integer device);

}