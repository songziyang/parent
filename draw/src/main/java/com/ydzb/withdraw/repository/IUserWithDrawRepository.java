package com.ydzb.withdraw.repository;

import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.withdraw.entity.UserWithDraw;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.math.BigDecimal;


public interface IUserWithDrawRepository extends IBaseRepository<UserWithDraw, Long> {

    @Query("select sum(uw.withdrawMoney) from UserWithDraw as uw where uw.status = 4 and uw.user.id = :userId ")
    public BigDecimal findWithDrawSum(@Param("userId") Long userId);


    @Query("select max(uw.id) from UserWithDraw as uw where uw.status = 4 and uw.user.id = :userId ")
    public Long findMaxId(@Param("userId") Long userId);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select uw from UserWithDraw as uw where  uw.id = :id ")
    public UserWithDraw findUserWithDrawById(@Param("id") Long id);


}
