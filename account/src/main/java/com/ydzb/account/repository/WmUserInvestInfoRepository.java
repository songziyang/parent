package com.ydzb.account.repository;

import com.ydzb.account.entity.WmUserInvestinfo;
import com.ydzb.account.entity.WmUserMoney;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * 用户投资信息repository
 */
@Repository
public class WmUserInvestInfoRepository extends WmBaseRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 查询用户投资信息
     * @param userId 用户id
     * @return
     */
    public WmUserInvestinfo findByUser(Long userId) {
        String jpql = " FROM WmUserInvestinfo WHERE userId = " + userId;
        Query query = entityManager.createQuery(jpql, WmUserInvestinfo.class);
        if (query != null) {
            List<WmUserInvestinfo> resultSet = query.getResultList();
            if (resultSet != null && !resultSet.isEmpty()) {
                return resultSet.get(0);
            }
        }
        return null;
    }

    /**
     * 根据用户id查询userMoneyId
     * @param userId 用户id
     * @return
     */
    public Long queryIdByUser(Long userId) {
        String jpql = " SELECT id FROM WmUserInvestinfo WHERE userId = " + userId;
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
    public WmUserInvestinfo findByUser(Long userId, LockModeType lockType) {
        if (lockType == null || lockType == LockModeType.NONE) return findByUser(userId);
        Long id = queryIdByUser(userId);
        if (id == null) return null;
        return entityManager.find(WmUserInvestinfo.class, id, lockType);
    }

    public WmUserInvestinfo saveOrUpdate(WmUserInvestinfo entity) {
        if (entity == null) return null;
        if (entity.getId() != null) return entityManager.merge(entity);
        entityManager.persist(entity);
        return entity;
    }
}