package com.ydzb.account.service;

import com.ydzb.account.entity.WmPrivilegeSettlement;

import java.math.BigDecimal;

/**
 * 新手特权每日结算service接口
 */
public interface IWmPrivilegeSettlementService {

    /**
     * 创建
     * @param userId 用户Id
     * @param productId 产品id
     * @param fund 总资金
     * @param apr 利率
     * @param income 收益
     * @param accountDate 结算时间
     * @return
     */
    WmPrivilegeSettlement createOne(Long userId, Long productId, BigDecimal fund, BigDecimal apr, BigDecimal income, Long accountDate);
}
