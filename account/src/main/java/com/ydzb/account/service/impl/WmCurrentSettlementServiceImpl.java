package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmCurrentSettlement;
import com.ydzb.account.repository.WmCurrentSettlementRepository;
import com.ydzb.account.service.IWmCurrentSettlementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 活期宝每日结算service实现
 */
@Service
public class WmCurrentSettlementServiceImpl implements IWmCurrentSettlementService {

    @Autowired
    private WmCurrentSettlementRepository currentSettlementRepository;

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
    @Override
    public WmCurrentSettlement createOne(Long userId, Long productId, BigDecimal fund, BigDecimal investFund,
                BigDecimal apr, BigDecimal dayApr, BigDecimal monthApr, BigDecimal vipApr, BigDecimal income, Long accountDate, Long created) {
        WmCurrentSettlement settlement = new WmCurrentSettlement();
        settlement.setUserId(userId);
        settlement.setProductId(productId);
        settlement.setFund(fund);
        settlement.setInvestFund(investFund);
        settlement.setApr(apr);
        settlement.setDaypr(dayApr);
        settlement.setMonthApr(monthApr);
        settlement.setVipApr(vipApr);
        settlement.setIncome(income);
        settlement.setAccountDate(accountDate);
        settlement.setCreated(created);
        return (WmCurrentSettlement) currentSettlementRepository.createOrUpdate(settlement);
    }
}