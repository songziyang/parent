package com.ydzb.account.service;

import com.ydzb.account.entity.WmRedPacketCash;

import java.math.BigDecimal;

/**
 * Created by sy on 2016/6/4.
 */
public interface IWmRedpacketCashService {

    /**
     * 根据触发类型查询
     * @param triggerType
     * @return
     */
    public WmRedPacketCash findByTriggerType(Integer triggerType);

    public WmRedPacketCash createOrUpdate(WmRedPacketCash redPacketCash);

    public WmRedPacketCash createOne(BigDecimal fund, Integer triggerType, Integer userDays,
        Long beginDate, Long endDate, String name);
}