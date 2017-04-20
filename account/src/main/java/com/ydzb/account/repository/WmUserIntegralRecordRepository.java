package com.ydzb.account.repository;

import com.ydzb.account.entity.WmUserIntegralRecord;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 用户积分记录repository
 */
@Repository
public class WmUserIntegralRecordRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 创建或更新
     * @param entity
     * @return
     */
    public WmUserIntegralRecord createOrUpdate(WmUserIntegralRecord entity) {
        if (entity == null) return null;
        if (entity.getId() != null) return entityManager.merge(entity);
        entityManager.persist(entity);
        return entity;
    }
}