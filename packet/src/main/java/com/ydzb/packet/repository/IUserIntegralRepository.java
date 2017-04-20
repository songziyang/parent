package com.ydzb.packet.repository;

import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.user.entity.UserIntegral;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 */
public interface IUserIntegralRepository extends IBaseRepository<UserIntegral, Long> {

    @Query(" FROM UserIntegral WHERE user.id = :userId ")
    public UserIntegral queryByUserId(@Param("userId") Long userId);
}