package com.ydzb.account.service;

import com.ydzb.account.entity.WmActivityGuoqingWinning;

import java.math.BigDecimal;

/**
 * 国庆活动统计service接口
 */
public interface IWmActivityguoqingWinningService {

    /**
     * 累加投资金额
     * @param activityGuoqingWinning 国庆活动统计
     * @param fund 复投金额
     * @return
     */
    WmActivityGuoqingWinning cumulateInvestMoney(WmActivityGuoqingWinning activityGuoqingWinning, BigDecimal fund);

    /**
     * 初始化
     * @return
     */
    WmActivityGuoqingWinning init();
}