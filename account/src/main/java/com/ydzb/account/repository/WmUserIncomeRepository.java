package com.ydzb.account.repository;

import com.ydzb.account.entity.WmUserIncome;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by sy on 2016/7/25.
 */
@Repository
public class WmUserIncomeRepository extends WmBaseRepository {

    public WmUserIncome findByUser(Long userId, LockModeType lockModeType) {
        if (lockModeType == null || lockModeType == LockModeType.NONE) return findByUser(userId);
        Long id = queryIdByUser(userId);
        if (id == null) return null;
        return entityManager.find(WmUserIncome.class, id, lockModeType);
    }

    public WmUserIncome saveOrUpdate(WmUserIncome entity) {
        if (entity == null) return null;
        if (entity.getId() != null) return entityManager.merge(entity);
        entityManager.persist(entity);
        return entity;
    }

    /**
     * 根据用户id查询id
     * @param userId 用户id
     * @return
     */
    public Long queryIdByUser(Long userId) {
        String jpql = " SELECT id FROM WmUserIncome WHERE userId = " + userId;
        List<Long> resultList = entityManager.createQuery(jpql).getResultList();
        if (resultList != null && !resultList.isEmpty()) {
            return resultList.get(0);
        }
        return null;
    }

    /**
     * 查询用户投资信息
     * @param userId 用户id
     * @return
     */
    public WmUserIncome findByUser(Long userId) {
        String jpql = " FROM WmUserIncome WHERE userId = " + userId;
        Query query = entityManager.createQuery(jpql, WmUserIncome.class);
        if (query != null) {
            List<WmUserIncome> resultSet = query.getResultList();
            if (resultSet != null && !resultSet.isEmpty()) {
                return resultSet.get(0);
            }
        }
        return null;
    }
}