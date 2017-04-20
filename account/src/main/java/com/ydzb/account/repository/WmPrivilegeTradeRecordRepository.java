package com.ydzb.account.repository;

import com.ydzb.account.entity.WmPrivilegeTradeRecord;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 新手特权交易记录repository
 */
@Repository
public class WmPrivilegeTradeRecordRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 创建或更新
     * @param entity
     * @return
     */
    public WmPrivilegeTradeRecord createOrUpdate(WmPrivilegeTradeRecord entity) {
        if (entity == null) return null;
        if (entity.getId() != null) return entityManager.merge(entity);
        entityManager.persist(entity);
        return entity;
    }
}
