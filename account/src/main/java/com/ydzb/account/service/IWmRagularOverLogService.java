package com.ydzb.account.service;

import com.ydzb.account.entity.WmRagularOverLog;

import java.math.BigDecimal;

/**
 * 定存宝到期日志service接口
 */
public interface IWmRagularOverLogService {

    /**
     * 创建
     * @param userId 用户id
     * @param accountId 用户定存产品记录id
     * @param type 类别：0-产品到期，1-产品转让
     * @param fund 金额
     * @param logTime 记录时间(带时分秒)
     * @param grandFund 其他活动赠与资金
     * @return
     */
    WmRagularOverLog createOne(Long userId, Long accountId, Integer type, BigDecimal fund, Long logTime, BigDecimal grandFund);
}