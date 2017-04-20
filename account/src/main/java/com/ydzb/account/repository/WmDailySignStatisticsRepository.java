package com.ydzb.account.repository;

import com.ydzb.account.entity.WmDailySignStatistics;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by sy on 2016/6/29.
 */
@Repository
public class WmDailySignStatisticsRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public WmDailySignStatistics saveOrUpdate(WmDailySignStatistics wmDailySignStatistics) {
        if (wmDailySignStatistics == null) return null;
        if (wmDailySignStatistics.getId() != null) return entityManager.merge(wmDailySignStatistics);
        entityManager.persist(wmDailySignStatistics);
        entityManager.refresh(wmDailySignStatistics);
        return wmDailySignStatistics;
    }
}
