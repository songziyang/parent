package com.ydzb.account.repository;

import com.ydzb.account.entity.WmUserMoney;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

/**
 * 用户资金 repository
 */
@Repository
public class WmUserMoneyRepository extends WmBaseRepository {

    /**
     * 根据用户id查询
     * @param userId 用户id
     * @return
     */
    public WmUserMoney findByUser(Long userId) {
        String jpql = " FROM WmUserMoney WHERE userId = " + userId;
        Query query = entityManager.createQuery(jpql, WmUserMoney.class);
        if (query != null) {
            List<WmUserMoney> resultSet = query.getResultList();
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
        String jpql = " SELECT id FROM WmUserMoney WHERE userId = " + userId;
        List<Long> resultList = entityManager.createQuery(jpql).getResultList();
        if (resultList != null && !resultList.isEmpty()) {
            return resultList.get(0);
        }
        return null;
    }

    /**
     * 根据id查询
     * @param id
     * @param lockType 锁类型
     * @return
     */
    public WmUserMoney queryById(Long id, LockModeType lockType) {
        String jpql = " FROM WmUserMoney WHERE id = " + id;
        List<WmUserMoney> resultList = entityManager.createQuery(jpql).setLockMode(lockType).getResultList();
        if (resultList != null && !resultList.isEmpty()) {
            return resultList.get(0);
        }
        return null;
    }

    /**
     *
     * @param userMoney
     * @return
     */
    public WmUserMoney saveOrUpdate(WmUserMoney userMoney) {
        if (userMoney == null) return null;
        if (userMoney.getId() != null) return entityManager.merge(userMoney);
        entityManager.persist(userMoney);
        return userMoney;
    }

    /**
     * 查询用户资金
     *
     * @param userId 用户ID
     * @return 用户资金
     */
    public WmUserMoney queryByUser(Long userId, LockModeType lockType) {
        if (lockType == null) return findByUser(userId);
        Long id = queryIdByUser(userId);
        if (id == null) return null;
        return entityManager.find(WmUserMoney.class, id, lockType);
    }
}