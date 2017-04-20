package com.ydzb.user.repository;

import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.user.entity.VipSetRecord;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * vip设置记录repository
 */
@Repository
public interface IVipSetRecordRepository extends IBaseRepository<VipSetRecord, Long> {

    /**
     * 更新状态
     * @param userId
     * @param sourceStatus
     * @param targetStatus
     */
    @Modifying
    @Query(" UPDATE VipSetRecord SET status = :targetStatus WHERE status = :sourceStatus AND user.id = :userId ")
    void updateStatus(@Param("userId") Long userId, @Param("sourceStatus") Integer sourceStatus, @Param("targetStatus") Integer targetStatus);
}