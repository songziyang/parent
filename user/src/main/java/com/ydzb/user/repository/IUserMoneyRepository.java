package com.ydzb.user.repository;

import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.user.entity.UserMoney;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;

public interface IUserMoneyRepository extends IBaseRepository<UserMoney, Long> {


    /**
     * 根据用户id获得用户资金
     *
     * @param userId
     * @return
     */
    @Query(" FROM UserMoney WHERE user.id = :userId ")
    public UserMoney findUserMoney(@Param("userId") Long userId);

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(" FROM UserMoney WHERE id= :id ")
    public UserMoney findUserMoneyById(@Param("id") Long id);

}


