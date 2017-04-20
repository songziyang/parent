package com.ydzb.product.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.product.entity.CurrentQueue;

import java.math.BigDecimal;

/**
 * 活期宝排队service接口
 * @author sy
 */
public interface ICurrentQueueService extends IBaseService<CurrentQueue, Long> {

    /**
     * 查询预投总份数
     * @return
     */
    public BigDecimal querySumCopies();

    public BigDecimal querySumCopies(Long startTime, Long endTime);

    public BigDecimal queryTodaySumCopies();
}
