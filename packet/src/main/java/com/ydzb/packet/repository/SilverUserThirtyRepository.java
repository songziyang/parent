package com.ydzb.packet.repository;

import com.ydzb.packet.entity.SilverUserThirty;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * 30亿活动用户银多币repository
 */
@Repository
public class SilverUserThirtyRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 根据Id查询
     * @param id
     * @param lockType
     * @return
     */
    public SilverUserThirty queryById(Long id, LockModeType lockType) {

        if (lockType == null) lockType = LockModeType.NONE;
        String jpql = " FROM " + SilverUserThirty.class.getSimpleName() + " WHERE id = " + id;
        List<SilverUserThirty> resultList = entityManager.createQuery(jpql, SilverUserThirty.class).setLockMode(lockType).getResultList();
        return (resultList != null && !resultList.isEmpty())? resultList.get(0): null;
    }
}
