package com.ydzb.account.repository;

import com.ydzb.account.entity.WmUserIncomeRecord;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by sy on 2016/7/25.
 */
@Repository
public class WmUserIncomeRecordRepository extends WmBaseRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public WmUserIncomeRecord saveOrUpdate(WmUserIncomeRecord entity) {
        if (entity == null) return null;
        if (entity.getId() != null) return entityManager.merge(entity);
        entityManager.persist(entity);
        return entity;
    }
}