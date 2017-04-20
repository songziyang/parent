package com.ydzb.packet.repository;

import com.ydzb.packet.entity.ActivityGuoqing;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by sy on 2016/10/12.
 */
@Repository
public class ActivityGuoqingRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public ActivityGuoqing queryByUser(Long userId, LockModeType lockType) {

        if (lockType == null || lockType == LockModeType.NONE) {
            String ql = " FROM ActivityGuoqing WHERE user.id = " + userId;
            List<ActivityGuoqing> resultList = entityManager.createQuery(ql, ActivityGuoqing.class).getResultList();
            return (resultList != null && !resultList.isEmpty())? resultList.get(0): null;
        }

        String ql = " SELECT id FROM ActivityGuoqing WHERE user.id = " + userId;
        List<Long> resultList = entityManager.createQuery(ql).getResultList();
        if (resultList != null && !resultList.isEmpty()) {
            Long id = resultList.get(0);
            return entityManager.find(ActivityGuoqing.class, id, lockType);
        }
        return null;
    }

    public ActivityGuoqing createOrUpdate(ActivityGuoqing entity) {
        if (entity == null) return null;
        if (entity.getId() != null) return entityManager.merge(entity);
        entityManager.persist(entity);
        return entity;
    }
}