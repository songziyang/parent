package com.ydzb.packet.repository;

import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.packet.entity.SilverUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 * 银币活动
 */
public interface ISilverUserRepository extends IBaseRepository<SilverUser, Long> {


    @Query("select su from SilverUser as su where su.userId = :userId ")
    public SilverUser querySilverUserByUserId(@Param("userId") Long userId);


}
