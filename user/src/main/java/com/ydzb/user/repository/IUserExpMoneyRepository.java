package com.ydzb.user.repository;

import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.user.entity.UserExMoney;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 用户体验金repository接口
 * @author sy
 */
public interface IUserExpMoneyRepository extends IBaseRepository<UserExMoney, Long> {

    /**
     * 根据用户id查找用户体验金
     * @param userId
     * @return
     */
    @Query(" FROM UserExMoney WHERE user.id = :userId ")
    public UserExMoney findOneByUserId(@Param("userId") Long userId);
}