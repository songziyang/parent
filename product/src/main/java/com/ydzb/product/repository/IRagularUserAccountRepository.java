package com.ydzb.product.repository;


import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.product.entity.RagularUserAccount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface IRagularUserAccountRepository extends IBaseRepository<RagularUserAccount, Long> {


    @Query("select sum(rua.buyFund) from RagularUserAccount as rua where rua.expireTime >= :startDate and rua.expireTime < :endDate ")
    public BigDecimal findTotalFund(@Param("startDate") Long startDate, @Param("endDate") Long endDate);


}


