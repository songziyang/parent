package com.ydzb.product.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.product.entity.VRagularUserAccount;

import java.math.BigDecimal;

/**
 * 用户定存宝记录service接口
 *
 * @author sy
 */
public interface IVRagularUserAccountService extends IBaseService<VRagularUserAccount, Long> {

    /**
     * 查询三个月定存总额
     *
     * @return
     */
    BigDecimal queryThreeMonthFund();

    /**
     * 查询六个月定存总额
     *
     * @return
     */
    BigDecimal querySixMonthFund();

    /**
     * 查询十二个月定存总额
     *
     * @return
     */
    BigDecimal queryTwelveMonthFund();


    /**
     * 查询一个月定存总额
     *
     * @return
     */
    BigDecimal queryOneMonthFund();

}
