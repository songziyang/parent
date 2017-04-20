package com.ydzb.account.service;

import com.ydzb.account.entity.WmCurrentTradeRecored;

import java.math.BigDecimal;

/**
 * 活期宝交易记录service接口
 */
public interface IWmCurrentTradeRecordService {

    /**
     * 创建
     * @param userId 用户id
     * @param names 来源名称
     * @param fund 购买资金
     * @param buyTime 操作时间
     * @param type 类型
     * @param fundSource 来源
     * @param logId 购买（或其他）日志id
     * @return
     */
    WmCurrentTradeRecored createOne(Long userId, String names, BigDecimal fund, Long buyTime, Integer type, Integer fundSource, Long logId);
}
