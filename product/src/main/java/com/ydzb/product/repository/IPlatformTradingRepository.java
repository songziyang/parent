package com.ydzb.product.repository;

import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.product.entity.PlatformTrading;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 平台交易统计repository
 * @author sy
 */
public interface IPlatformTradingRepository extends IBaseRepository<PlatformTrading, Long> {

    /**
     * 根据类型以及操作起始日期查找平台交易统计记录
     * @param type
     * @param startTime
     * @param endTime
     * @return
     */
    @Query(" FROM PlatformTrading WHERE type = :type AND operationTime BETWEEN :startTime AND :endTime ")
    public List<PlatformTrading> findOnes(@Param("type")Byte type, @Param("startTime") Long startTime, @Param("endTime")Long endTime);
}