package com.ydzb.user.repository;

import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.user.entity.ViRechargePenny;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 小额充值repository
 */
public interface IViRechargePennyRepository extends IBaseRepository<ViRechargePenny, Long> {

    @Query(" SELECT COUNT(DISTINCT user.id) FROM ViRechargePenny WHERE rechargeTime >= :startTime AND rechargeTime < :endTime ")
    Integer queryUserCount(@Param("startTime") Long startTime, @Param("endTime") Long endTime);

    @Query(" SELECT COUNT(DISTINCT user.id) FROM ViRechargePenny")
    Integer queryUserCount();
}