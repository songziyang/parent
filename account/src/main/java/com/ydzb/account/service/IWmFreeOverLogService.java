package com.ydzb.account.service;

import com.ydzb.account.entity.WmFreeOverLog;

import java.math.BigDecimal;

/**
 * 随心存到期日志service接口
 */
public interface IWmFreeOverLogService {

    /**
     * 创建
     * @param userId 用户id
     * @param accountId 随心存账户id
     * @param type 类型
     * @param fund 金额
     * @param logTime 记录时间
     * @param grandFund 其他活动赠与资金
     * @return
     */
    WmFreeOverLog createOne(Long userId, Long accountId, Integer type, BigDecimal fund,
            Long logTime, BigDecimal grandFund) throws Exception;
}