package com.ydzb.account.service;

import com.ydzb.account.entity.WmUserFundInLog;
import com.ydzb.account.entity.WmUserMoney;

import java.math.BigDecimal;

/**
 * Created by sy on 2016/6/28.
 */
public interface IWmUserFundInLogService {

    WmUserFundInLog createOne(Long userId, WmUserMoney wmUserMoney, Integer type, BigDecimal fund, Long linkId);

    WmUserFundInLog saveOrUpdate(WmUserFundInLog entity);

    /**
     * 创建
     * @param userId 用户id
     * @param type 类型
     * @param receiptsTime 到账时间
     * @param fund 所得本金
     * @param incomeInterest 所得利息
     * @param usableFund 用户可用余额
     * @param ramark 备注
     * @param linkId 外链id
     * @return
     */
    WmUserFundInLog createOne(Long userId, Integer type, Long receiptsTime, BigDecimal fund, BigDecimal incomeInterest, BigDecimal usableFund, String ramark, Long linkId);
}
