package com.ydzb.product.service;


import com.ydzb.core.service.IBaseService;
import com.ydzb.product.entity.SelfRevenue;
import com.ydzb.product.entity.SelfTradeLog;

import java.math.BigDecimal;

/**
 * 自主交易还款service接口
 * @author sy
 */
public interface ISelfRevenueService extends IBaseService<SelfRevenue, Long> {

    /**
     * 保存自主交易还款
     * @param selfTradeLog 自主交易
     * @param revenue 收益
     * @param remark 描述
     * @param finalDeal 是否是最后一笔还款
     */
    public void saveOne(SelfTradeLog selfTradeLog, BigDecimal revenue, String remark, Byte finalDeal);

    /**
     * 保存自主交易还款
     * @param selfTradeId 自主交易id
     * @param revenue 收益
     * @param remark 描述
     * @param finalDeal 是否是最后一笔还款
     */
	public void saveOne(Long selfTradeId, BigDecimal revenue, String remark, Byte finalDeal);

    /**
     * 审核还款
     * @param revenueId
     * @param status
     */
    public void approve(Long revenueId, Byte status);
}
