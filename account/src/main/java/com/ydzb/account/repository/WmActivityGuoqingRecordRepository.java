package com.ydzb.account.repository;

import com.ydzb.account.entity.WmActivityGuoqingRecord;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 国庆活动记录repository
 */
@Repository
public class WmActivityGuoqingRecordRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 创建或更新
     * @param entity
     * @return
     */
    public WmActivityGuoqingRecord createOrUpdate(WmActivityGuoqingRecord entity) {
        if (entity == null) return null;
        if (entity.getId() != null) return entityManager.merge(entity);
        entityManager.persist(entity);
        return entity;
    }
}