package com.ydzb.account.repository;

import com.ydzb.account.entity.WmThirtyLotteryStatistics;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 30亿活动抽奖统计repository
 */
@Repository
public class WmThirtyLotteryStatisticsRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public WmThirtyLotteryStatistics saveOrUpdate(WmThirtyLotteryStatistics eneity) {
        if (eneity == null) return null;
        if (eneity.getId() != null) return entityManager.merge(eneity);
        entityManager.persist(eneity);
        entityManager.refresh(eneity);
        return eneity;
    }
}