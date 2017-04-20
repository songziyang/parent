package com.ydzb.account.service;

import com.ydzb.account.entity.WmPrivilegeBuyLog;

import java.math.BigDecimal;

/**
 * 新手特权购买日志service接口
 */
public interface IWmPrivilegeBuyLogService {

    /**
     * 创建
     * @param productId 产品id
     * @param userId 用户id
     * @param buyFund 购买金额
     * @param buyCopies 购买份数
     * @param apr 利率
     * @param buyTime 购买时间
     * @param device
     * @return
     */
    WmPrivilegeBuyLog createOne(Long productId, Long userId, BigDecimal buyFund, Integer buyCopies, BigDecimal apr, Long buyTime, Integer device);
}