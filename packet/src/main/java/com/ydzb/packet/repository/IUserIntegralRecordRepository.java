package com.ydzb.packet.repository;

import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.packet.entity.UserIntegralRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 * 积分记录
 */
public interface IUserIntegralRecordRepository extends IBaseRepository<UserIntegralRecord, Long> {

    @Query("select su from UserIntegralRecord as su where su.user.id = :userId and su.linkType = :linkType ")
    public UserIntegralRecord findUserIntegralRecordByUserIdAndLinkType(@Param("userId") Long userId, @Param("linkType") Integer linkType);


}
