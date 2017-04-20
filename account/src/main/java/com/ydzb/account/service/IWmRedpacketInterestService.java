package com.ydzb.account.service;

import com.ydzb.account.entity.WmRedPacketInterest;

import java.math.BigDecimal;

/**
 * 定存红包service接口
 */
public interface IWmRedpacketInterestService {

    /**
     * 根据触发类型查询
     * @param triggerType 触发类型
     * @return
     */
    WmRedPacketInterest queryByTriggerType(Integer triggerType);

    /**
     * 创建
     * @param name 红包名称
     * @param productType 产品类别
     * @param triggerType 触发类型
     * @param beginDate 活动开始时间
     * @param endDate 活动结束时间
     * @param redpacketType 红包类型
     * @param useDays 使用天数
     * @param giveValue 赠送值
     * @param investDays 投资天数
     * @return
     */
    WmRedPacketInterest createOne(String name, Long productType, Integer triggerType, Long beginDate, Long endDate,
        Integer redpacketType, Integer useDays, BigDecimal giveValue, Integer investDays);
}
