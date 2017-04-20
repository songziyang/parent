package com.ydzb.account.repository;

import com.ydzb.account.entity.WmRedpacketUser;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

/**
 * 用户红包repository
 */
@Repository
public class WmRedpacketUserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 创建或更新
     * @param entity
     * @return
     */
    public WmRedpacketUser createOrUpdate(WmRedpacketUser entity) {
        if (entity == null) return null;
        if (entity.getId() != null) return entityManager.merge(entity);
        entityManager.persist(entity);
        return entity;
    }

    /**
     * 根据id和锁类型查询
     * @param id
     * @param lockType
     * @return
     */
    public WmRedpacketUser findOne(Long id, LockModeType lockType) {
        if (lockType == null) lockType = LockModeType.NONE;
        return entityManager.find(WmRedpacketUser.class, id, lockType);
    }
}