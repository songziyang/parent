package com.ydzb.account.service;

import com.ydzb.account.entity.WmPrivilegeTradeRecord;

import java.math.BigDecimal;

/**
 * 新手特权交易记录service接口
 */
public interface IWmPrivilegeTradeRecordService {


    /**
     * 创建
     * @param userId 用户id
     * @param names 来源名称
     * @param fund 金额
     * @param buyTime 购买时间
     * @param type 类型
     * @param fundSource 资金来源
     * @param logId 日志id
     * @return
     */
    WmPrivilegeTradeRecord createOne(Long userId, String names, BigDecimal fund, Long buyTime, Integer type, Integer fundSource, Long logId);
}