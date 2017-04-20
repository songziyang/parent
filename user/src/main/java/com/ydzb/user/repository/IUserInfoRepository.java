package com.ydzb.user.repository;

import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.user.entity.UserInfo;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.math.BigDecimal;

public interface IUserInfoRepository extends IBaseRepository<UserInfo, Long> {


    @Query("select sum(ur.allRecharge) from UserInfo as ur where  ur.allRecharge > 0 ")
    public BigDecimal findRechargeAllFund();


    @Query("select count(ur) from UserInfo as ur where  ur.allRecharge > 0 ")
    public Integer findRechargePersons();

    @Query(" FROM UserInfo where user.id = :userId")
    public UserInfo findByUserId(@Param("userId") Long userId);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(" FROM UserInfo where id = :id")
    public UserInfo findUserInfoById(@Param("id") Long id);

}


