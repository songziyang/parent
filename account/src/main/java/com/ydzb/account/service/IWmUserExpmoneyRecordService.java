package com.ydzb.account.service;

import com.ydzb.account.entity.WmUserExMoney;

import java.math.BigDecimal;

/**
 * 用户体验金记录service接口
 */
public interface IWmUserExpmoneyRecordService {

    /**
     * 创建
     * @param wmUserExMoney 用户体验金
     * @param fundflow 来源去向
     * @param fund 金额
     * @param inLogId 入账日志id
     * @param type 体验金类型
     */
    void createOne(WmUserExMoney wmUserExMoney, String fundflow, BigDecimal fund, Long inLogId, Integer type);
}
