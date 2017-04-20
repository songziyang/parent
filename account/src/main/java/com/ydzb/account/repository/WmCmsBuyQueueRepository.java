package com.ydzb.account.repository;

import com.ydzb.account.entity.WmCmsBuyQueue;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 用户购买队列表repository
 */
@Repository
public class WmCmsBuyQueueRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 创建或保存
     * @param entity
     * @return
     */
    public WmCmsBuyQueue createOrUpdate(WmCmsBuyQueue entity) {
        if (entity == null) return null;
        if (entity.getId() != null) return entityManager.merge(entity);
        entityManager.persist(entity);
        return entity;
    }
}