package com.ydzb.product.repository;

import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.product.entity.CurrentRate;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 活期宝利率对照-repository
 */
public interface ICurrentRateRepository extends IBaseRepository<CurrentRate, Long> {

    /**
     * 根据id更改状态
     * @param currentRateId
     * @param status
     */
    @Modifying
    @Query(" UPDATE CurrentRate SET status = :status WHERE id = :currentRateId ")
    public void updateStatus(@Param("currentRateId") Long currentRateId, @Param("status") Byte status);

    /**
     * 根据状态更改状态
     * @param status
     * @param sourceState
     */
    @Modifying
    @Query(" UPDATE CurrentRate SET status = :status WHERE status = :sourceState ")
    public void updateStatus(@Param("status") Byte status, @Param("sourceState") Byte sourceState);

    /**
     * 根据状态和时间类型更改状态
     * @param timeType
     * @param status
     * @param sourceState
     */
    @Modifying
    @Query(" UPDATE CurrentRate SET status = :status WHERE status = :sourceState AND timeType = :timeType")
    public void updateStatus(@Param("timeType") Byte timeType, @Param("status") Byte status, @Param("sourceState") Byte sourceState);

    /**
     * 根据状态和时间类型，更改不是相同id的实体状态
     * @param rateId
     * @param timeType
     * @param status
     * @param sourceState
     */
    @Modifying
    @Query(" UPDATE CurrentRate SET status = :status WHERE status = :sourceState AND timeType = :timeType AND id <>:rateId")
    public void updateStatus(@Param("rateId") Long rateId, @Param("timeType") Byte timeType, @Param("status") Byte status, @Param("sourceState") Byte sourceState);

    /**
     * 根据时间查询活期宝利率
     * @param currentDate
     * @param status
     * @return
     */
    @Query(" FROM CurrentRate WHERE currentDate = :currentDate AND status = :status ")
    public CurrentRate queryOne(@Param("currentDate") Long currentDate, @Param("status") Byte status);
}
