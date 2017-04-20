package com.ydzb.user.repository;

import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.user.entity.UserIncome;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 用户收益repository
 * @author sy
 */
public interface IUserIncomeRepository extends IBaseRepository<UserIncome, Long> {

    /**
     * 根据用户id查询用户收益
     * @param userId
     * @return
     */
    @Query(" FROM UserIncome WHERE user.id = :userId ")
    public UserIncome queryByUser(@Param("userId") Long userId);
}


