package com.ydzb.account.repository;

import com.ydzb.core.entity.BaseEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

/**
 * 结算用基本repository
 */
@Repository
public class WmBaseRepository<T extends BaseEntity> {

    @PersistenceContext
    protected EntityManager entityManager;

    /**
     * 创建或更新
     * @param entity
     * @return
     */
    public T createOrUpdate(T entity) {
       if (entity == null) return null;
       if (entity.getId() != null) return entityManager.merge(entity);
       entityManager.persist(entity);
       return entity;
    }

    /**
     * 根据主键查询实体
     * @param id 主键
     * @param c 实体的class
     * @param lockType 锁类型
     * @return
     */
    public T queryById(Long id, Class c, LockModeType lockType) {
        if (lockType == null) lockType = LockModeType.NONE;
        return (T)entityManager.find(c, id, lockType);
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}