package com.ydzb.product.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.product.entity.RagularRate;

/**
 * 定存宝利率service接口
 */
public interface IRagularRateService extends IBaseService<RagularRate, Long> {

    /**
     * 保存
     * @param rate 定存宝利率
     * @return
     */
    public String saveOne(RagularRate rate);

    /**
     * 更改状态
     * @param days 天数
     * @param status 目标状态
     * @param sourceStatus 原状态
     */
    public void updateStatus(Integer days, Byte status, Byte sourceStatus) throws Exception;

    /**
     * 更改状态
     * @param rateId 利率id
     * @param days 天数
     * @param status 目标状态
     * @param sourceStatus 原状态
     */
    public void updateStatus(Long rateId, Integer days, Byte status, Byte sourceStatus) throws Exception;
}