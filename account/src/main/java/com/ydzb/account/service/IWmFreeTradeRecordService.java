package com.ydzb.account.service;

import com.ydzb.account.entity.WmFreeTradeRecored;

import java.math.BigDecimal;

/**
 * 随心存交易记录service接口
 */
public interface IWmFreeTradeRecordService {

    /**
     * 创建
     * @param names 来源名称
     * @param days 产品天数
     * @param fund 购买资金
     * @param buyTime 购买时间
     * @param type 类型
     * @param userId userId
     * @param fundSource 金额来源
     * @param logId 日志id
     * @return
     * @throws Exception
     */
    WmFreeTradeRecored createOne(String names, Integer days, BigDecimal fund, Long buyTime,
            Integer type, Long userId, Integer fundSource, Long logId) throws Exception;
}