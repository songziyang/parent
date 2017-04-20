package com.ydzb.account.repository;

import com.ydzb.account.entity.WmActivityOctoberRedpacket;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 金秋十月红包repository
 */
@Repository
public class WmActivityOctoberRedpacketRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 创建或保存
     * @param entity
     * @return
     */
    public WmActivityOctoberRedpacket createOrUpdate(WmActivityOctoberRedpacket entity) {
        if (entity == null) return null;
        if (entity.getId() != null) return entityManager.merge(entity);
        entityManager.persist(entity);
        return entity;
    }
}