package com.ydzb.account.repository;

import com.ydzb.account.entity.WmPrivilegeOverLog;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 新手特权赎回日志repository
 */
@Repository
public class WmPrivilegeOverLogRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 创建或更新
     * @param entity
     * @return
     */
    public WmPrivilegeOverLog createOrUpdate(WmPrivilegeOverLog entity) {
        if (entity == null) return null;
        if (!entity.isNew()) return entityManager.merge(entity);
        entityManager.persist(entity);
        return entity;
    }
}