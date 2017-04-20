package com.ydzb.packet.repository;

import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.packet.entity.SilverUserThirty;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 * 30亿活动用户银多币
 */
public interface ISilverUserThirtyRepository extends IBaseRepository<SilverUserThirty, Long> {

    @Query(" FROM SilverUserThirty where userId = :userId ")
    SilverUserThirty queryByUser(@Param("userId") Long userId);

    @Query(" SELECT id FROM SilverUserThirty where userId = :userId ")
    Long queryIdByUser(@Param("userId") Long userId);
}