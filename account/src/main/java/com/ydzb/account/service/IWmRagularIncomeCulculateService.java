package com.ydzb.account.service;

import com.ydzb.account.entity.WmIncomeEntity;
import com.ydzb.account.entity.WmProductInfo;

import java.math.BigDecimal;

/**
 * 定存收益计算service
 */
public interface IWmRagularIncomeCulculateService {

    /**
     * 计算收益
     * @param userId
     * @param productInfo
     * @param fund
     * @return
     */
    WmIncomeEntity culculateRecastIncome(Long userId, WmProductInfo productInfo, BigDecimal fund) throws Exception;
}