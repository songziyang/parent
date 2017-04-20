package com.ydzb.user.repository;


import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.user.entity.MinusAmount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 活期线下扣除金额
 */
public interface IMinusAmountRepository extends IBaseRepository<MinusAmount, Long> {

    @Query("select sum(ma.fund) from MinusAmount as ma  ")
    public Integer findSumMinusAmount();

}
