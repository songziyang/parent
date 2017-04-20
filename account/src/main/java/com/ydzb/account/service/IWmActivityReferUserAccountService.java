package com.ydzb.account.service;

import com.ydzb.account.entity.WmUserInvestinfo;
import com.ydzb.account.entity.WmUserMoney;

import java.math.BigDecimal;

/**
 */
public interface IWmActivityReferUserAccountService {


    public BigDecimal account(Long userId, Long referUserId);

    public BigDecimal getReferRatio(BigDecimal invest);

    public BigDecimal getInvestExceptExpInvest(WmUserInvestinfo investinfo);
}