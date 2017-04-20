package com.ydzb.account.service;

import com.ydzb.account.entity.WmCmsCreditorCanmatchTotal;

import java.math.BigDecimal;

/**
 * 债券总数service接口
 */
public interface IWmCmsCreditorCanmatchTotalService {

    /**
     * 增加总债券购买
     * @param fund 金额
     * @return
     */
    WmCmsCreditorCanmatchTotal increaseBuy(BigDecimal fund) throws Exception;

    /**
     * 增加总债券赎回
     * @param fund 金额
     * @return
     */
    WmCmsCreditorCanmatchTotal increaseRedeem(BigDecimal fund) throws Exception;
}
