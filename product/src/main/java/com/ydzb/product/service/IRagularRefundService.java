package com.ydzb.product.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.product.entity.RagularRefund;

import java.math.BigDecimal;

/**
 * 定存宝还息service接口
 * @author sy
 */
public interface IRagularRefundService extends IBaseService<RagularRefund, Long> {

    /**
     * 根据还息状态(state)以及对应产品的类型(type)计算收益
     * @param type
     * @param state
     * @return
     */
    public BigDecimal getTotalRevenue(long[] type, byte state);

    /**
     * 根据还息状态(state)以及对应产品的类型(type)计算收益
     * @param type
     * @param state
     * @return
     */
    public BigDecimal getTotalRevenue(Long type, byte state);

    /**
     * 根据定存产品类型(type)、还息状态(state)、结算起始日期查询到期收益
     * @param type
     * @param state
     * @param startTime
     * @param endTime
     * @param mustExistTime 是否必须存在起始时间
     * @return
     */
    public BigDecimal getClosingRevenue(long[] type, Byte state, Long startTime, Long endTime, boolean mustExistTime);
    /**
     * 根据定存产品类型(type)、还息状态(state)、结算起始日期查询到期收益
     * @param type
     * @param state
     * @param startTime
     * @param endTime
     * @param mustExistTime 是否必须存在起始时间
     * @return
     */
    public BigDecimal getClosingRevenue(Long type, Byte state, Long startTime, Long endTime, boolean mustExistTime);
}