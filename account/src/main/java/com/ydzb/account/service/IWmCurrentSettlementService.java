package com.ydzb.account.service;

import com.ydzb.account.entity.WmCurrentSettlement;

import java.math.BigDecimal;

/**
 * 活期宝每日结算service接口
 */
public interface IWmCurrentSettlementService {

    /**
     * 创建
     * @param userId 用户id
     * @param productId 产品id
     * @param fund 总资金
     * @param investFund 体验金
     * @param apr 利率
     * @param dayApr 日加息
     * @param monthApr 月加息
     * @param vipApr vip加息
     * @param income 收益
     * @param accountDate 结算时间
     * @param created 创建时间
     * @return
     */
    WmCurrentSettlement createOne(Long userId, Long productId, BigDecimal fund, BigDecimal investFund,
        BigDecimal apr, BigDecimal dayApr, BigDecimal monthApr, BigDecimal vipApr, BigDecimal income, Long accountDate, Long created);
}
