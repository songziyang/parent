package com.ydzb.product.repository;

import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.product.entity.CurrentQueue;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

/**
 * 体验金预投repository
 * @author sy
 */
public interface ICurrentQueueRepository extends IBaseRepository<CurrentQueue, Long> {

    /**
     * 查询预投总份数
     * @return
     */
    @Query(" SELECT SUM(prepayCopies) FROM CurrentQueue ")
    public BigDecimal querySumCopies();

    @Query(" SELECT SUM(prepayCopies) FROM CurrentQueue WHERE enqueueTime >= :startTime AND enqueueTime < :endTime ")
    public BigDecimal querySumCopies(@Param("startTime") Long startTime, @Param("endTime") Long endTime);
}
