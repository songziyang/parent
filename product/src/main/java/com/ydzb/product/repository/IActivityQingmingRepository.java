package com.ydzb.product.repository;

import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.product.entity.ActivityQingming;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 清明活动repository
 */
public interface IActivityQingmingRepository extends IBaseRepository<ActivityQingming, Long> {

    /**
     * 根据用户id查询用户清明活动记录
     * @param userId 用户id
     */
    @Query(" FROM ActivityQingming WHERE user.id = :userId ")
    public ActivityQingming findByUser(@Param("userId") Long userId);
}