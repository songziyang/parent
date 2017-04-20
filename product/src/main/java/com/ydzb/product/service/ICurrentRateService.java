package com.ydzb.product.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.product.entity.CurrentRate;

/**
 * 活期宝利率service接口
 * @author sy
 */
public interface ICurrentRateService extends IBaseService<CurrentRate, Long> {

    /**
     * 保存
     * @param rate
     * @param dCurrentDate
     * @return
     */
    public String saveOne(CurrentRate rate, String dCurrentDate);

    /**
     * 根据id更改状态
     * @param currentRateId
     * @param status
     */
    public void updateStatus(Long currentRateId, Byte status);

    /**
     * 根据状态更改状态
     * @param status
     * @param sourceStatus
     */
    public void updateStatus(Byte status, Byte sourceStatus);

    /**
     * 根据时间类型和状态更改状态
     * @param timeType
     * @param status
     * @param sourceStatus
     */
    public void updateStatus(Byte timeType, Byte status, Byte sourceStatus);

    /**
     * 根据时间类型和状态、更改除了rateId以外的其他实体状态
     * @param rateId
     * @param timeType
     * @param status
     * @param sourceStatus
     */
    public void updateStatus(Long rateId, Byte timeType, Byte status, Byte sourceStatus);

    /**
     * 停用
     * @param currentRateId
     */
    public void disable(Long currentRateId);
}
