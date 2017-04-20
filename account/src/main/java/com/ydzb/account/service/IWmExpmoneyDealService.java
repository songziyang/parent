package com.ydzb.account.service;


import com.ydzb.account.entity.WmExpmoneyDeal;

import java.math.BigDecimal;

public interface IWmExpmoneyDealService {

    /**
     * 结算体验金
     * @param userId  用户ID
     * @throws Exception
     */
    public void accountExpmoneyDeal(Long userId) throws Exception;

    WmExpmoneyDeal createOne(Long userId, BigDecimal fund, Integer type);
}
