package com.ydzb.product.repository;

import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.product.entity.CurrentRate;
import com.ydzb.product.entity.RagularRate;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 定存利率repository
 */
public interface IRagularRateRepository extends IBaseRepository<RagularRate, Long> {

    /**
     * 根据id更改状态
     * @param rateId 定存宝利率id
     * @param status
     */
    @Modifying
    @Query(" UPDATE RagularRate SET status = :status WHERE id = :rateId ")
    public void updateStatus(@Param("rateId") Long rateId, @Param("status") Byte status);

    /**
     * 根据状态更改状态
     * @param status 目标状态
     * @param sourceState 原状态
     */
    @Modifying
    @Query(" UPDATE RagularRate SET status = :status WHERE status = :sourceState ")
    public void updateStatus(@Param("status") Byte status, @Param("sourceState") Byte sourceState);

    /**
     * 根据状态和时间类型更改状态
     * @param days 天数
     * @param status 目标状态
     * @param sourceState 原状态
     */
    @Modifying
    @Query(" UPDATE RagularRate SET status = :status WHERE status = :sourceState AND days = :days")
    public void updateStatus(@Param("days") Integer days, @Param("status") Byte status, @Param("sourceState") Byte sourceState);

    /**
     * 根据状态和时间类型，更改不是相同id的实体状态
     * @param rateId 定存宝利率id
     * @param days 天数
     * @param status 目标状态
     * @param sourceState 原状态
     */
    @Modifying
    @Query(" UPDATE RagularRate SET status = :status WHERE status = :sourceState AND days = :days AND id <> :rateId")
    public void updateStatus(@Param("rateId") Long rateId, @Param("days") Integer days, @Param("status") Byte status, @Param("sourceState") Byte sourceState);
}