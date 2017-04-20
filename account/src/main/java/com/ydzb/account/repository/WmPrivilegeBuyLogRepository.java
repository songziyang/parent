package com.ydzb.account.repository;

import com.ydzb.account.entity.WmPrivilegeBuyLog;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 新手特权购买日志repository
 */
@Repository
public class WmPrivilegeBuyLogRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 创建或更新
     * @param entity
     * @return
     */
    public WmPrivilegeBuyLog createOrUpdate(WmPrivilegeBuyLog entity) {
        if (entity == null) return null;
        if (entity.getId() != null) entityManager.merge(entity);
        entityManager.persist(entity);
        return entity;
    }
}