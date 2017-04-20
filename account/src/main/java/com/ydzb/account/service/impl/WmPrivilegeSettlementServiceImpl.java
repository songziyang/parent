package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmPrivilegeSettlement;
import com.ydzb.account.repository.WmPrivilegeSettlementRepository;
import com.ydzb.account.service.IWmPrivilegeSettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 新手特权每日结算service实现
 */
@Service
public class WmPrivilegeSettlementServiceImpl implements IWmPrivilegeSettlementService {

    @Autowired
    private WmPrivilegeSettlementRepository privilegeSettlementRepository;

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
    @Override
    public WmPrivilegeSettlement createOne(Long userId, Long productId, BigDecimal fund, BigDecimal apr, BigDecimal income, Long accountDate) {
        WmPrivilegeSettlement settlement = new WmPrivilegeSettlement();
        settlement.setUserId(userId);
        settlement.setProductId(productId);
        settlement.setFund(fund);
        settlement.setApr(apr);
        settlement.setIncome(income);
        settlement.setAccountDate(accountDate);
        return privilegeSettlementRepository.createOrUpdate(settlement);
    }
}