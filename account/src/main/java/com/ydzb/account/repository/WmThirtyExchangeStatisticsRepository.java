package com.ydzb.account.repository;

import com.ydzb.account.entity.WmThirtyExchangeStatistics;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 30亿活动兑换统计repository
 */
@Repository
public class WmThirtyExchangeStatisticsRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 保存或更新
     * @param wmThirtyExchangeStatistics
     * @return
     */
    public WmThirtyExchangeStatistics saveOrUpdate(WmThirtyExchangeStatistics wmThirtyExchangeStatistics) {

        if (wmThirtyExchangeStatistics == null) return null;
        if (wmThirtyExchangeStatistics.getId() != null) return entityManager.merge(wmThirtyExchangeStatistics);
        entityManager.persist(wmThirtyExchangeStatistics);
        entityManager.refresh(wmThirtyExchangeStatistics);
        return wmThirtyExchangeStatistics;
    }
}