package com.ydzb.account.service;

import com.ydzb.account.entity.WmIncomeEntity;
import com.ydzb.account.entity.WmProductInfo;
import com.ydzb.account.entity.WmUser;

import java.math.BigDecimal;


/**
 * 活期宝结算
 */
public interface IWmCurrentIncomeCulculateService {

    /**
     * 结算收益
     * @param user
     * @param productInfo
     * @param allFund
     * @return
     */
     WmIncomeEntity culculateIncome(WmUser user, WmProductInfo productInfo, BigDecimal allFund, BigDecimal expFund);
}