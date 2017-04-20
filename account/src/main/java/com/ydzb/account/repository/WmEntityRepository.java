package com.ydzb.account.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

/**
 * Created by sy on 2016/8/24.
 */
@Repository
public class WmEntityRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public <T> T update(T entity) {
        return entityManager.merge(entity);
    }

    public <T> T save(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    public <T> T queryById(Long id, Class c) {
        return (T)entityManager.find(c, id);
    }

    public <T> T queryById(Long id, Class c, LockModeType lockType) {
        return (T)entityManager.find(c, id, lockType);
    }
}