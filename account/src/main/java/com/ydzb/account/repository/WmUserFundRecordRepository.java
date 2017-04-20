package com.ydzb.account.repository;

import com.ydzb.account.entity.WmUserFundRecord;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 */
@Repository
public class WmUserFundRecordRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public WmUserFundRecord saveOrUpdate(WmUserFundRecord entity) {
        if (entity == null) return null;
        if (entity.getId() != null) return entityManager.merge(entity);
        entityManager.persist(entity);
        return entity;
    }
}