package com.ydzb.product.repository;

import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.product.entity.FreeUserAccount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

/**
 * 随心存产品记录repository
 */
public interface IFreeUserAccountRepository extends IBaseRepository<FreeUserAccount, Long> {

    /**
     * 根据到期起始时间查询总购买金额
     * @param startDate 开始购买时间
     * @param endDate 结束购买时间
     * @return
     */
    @Query(" SELECT sum(buyFund) FROM FreeUserAccount WHERE expireTime >= :startDate and expireTime < :endDate ")
    BigDecimal findTotalFund(@Param("startDate") Long startDate, @Param("endDate") Long endDate);
}