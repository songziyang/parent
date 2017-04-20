package com.ydzb.account.repository;

import com.ydzb.account.entity.WmUserSilverThirty;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * 30亿活动用户银多币
 */
@Repository
public class WmUserSilverThirtyRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 保存或更改
     * @param wmUserSilverThirty
     * @return
     */
    public WmUserSilverThirty saveOrUpdate(WmUserSilverThirty wmUserSilverThirty) {
        if (wmUserSilverThirty == null) return null;
        if (wmUserSilverThirty.getId() != null) return entityManager.merge(wmUserSilverThirty);
        entityManager.persist(wmUserSilverThirty);
        entityManager.refresh(wmUserSilverThirty);
        return wmUserSilverThirty;
    }

    /**
     * 根据用户查询
     * @param userId
     * @return
     */
    public WmUserSilverThirty queryByUser(Long userId) {
        String jpql = " FROM WmUserSilverThirty WHERE userId = " + userId;
        List<WmUserSilverThirty> resultList = entityManager.createQuery(jpql, WmUserSilverThirty.class).getResultList();
        return (resultList != null && !resultList.isEmpty())? resultList.get(0): null;
    }

    /**
     * 根据用户查询Id
     * @param userId
     * @return
     */
    public Long queryIdByUser(Long userId) {
        String jpql = " SELECT id FROM WmUserSilverThirty WHERE userId = " + userId;
        List<Long> resultList = entityManager.createQuery(jpql).getResultList();
        return (resultList != null && !resultList.isEmpty())? resultList.get(0): null;
    }

    /**
     * 根据id查询
     * @param id
     * @param lockType
     * @return
     */
    public WmUserSilverThirty queryById(Long id, LockModeType lockType) {
        if (lockType == null) lockType = LockModeType.NONE;
        String jpql = " FROM WmUserSilverThirty WHERE id = " + id;
        List<WmUserSilverThirty> resultList = entityManager.createQuery(jpql, WmUserSilverThirty.class).setLockMode(lockType).getResultList();
        return (resultList != null && !resultList.isEmpty())? resultList.get(0): null;
    }
}