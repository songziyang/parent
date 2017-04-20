package com.ydzb.account.service;

import com.ydzb.account.entity.WmPrivilegeOverLog;

import java.math.BigDecimal;

/**
 * 新手特权赎回日志service接口
 */
public interface IWmPrivilegeOverLogService {

    /**
     * 创建
     * @param userId 用户id
     * @param type 类型
     * @param redemptionFund 赎回金额
     * @param redemptionTime 赎回时间
     * @return
     */
    WmPrivilegeOverLog createOne(Long userId, Integer type, BigDecimal redemptionFund, Long redemptionTime);
}