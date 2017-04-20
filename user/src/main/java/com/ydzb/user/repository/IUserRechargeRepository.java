package com.ydzb.user.repository;

import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.user.entity.UserRecharge;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface IUserRechargeRepository extends IBaseRepository<UserRecharge, Long> {

    @Query("select sum(ur.money) from UserRecharge as ur where ur.status = 1 and rechargeTime >= :startTime  and rechargeTime <= :endTime ")
    public BigDecimal findSumRecharge(@Param("startTime") Long startTime, @Param("endTime") Long endTime);


    @Query("select max(ur.id) from UserRecharge as ur where ur.status = 1 and ur.user.id = :userId")
    public Long findMaxId(@Param("userId") Long userId);


}


