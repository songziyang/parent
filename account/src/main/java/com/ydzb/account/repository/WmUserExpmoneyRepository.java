package com.ydzb.account.repository;

import com.ydzb.account.entity.WmUserExMoney;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * 用户体验金repository
 */
@Repository
public class WmUserExpmoneyRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 保存或更新
     * @param userExMoney
     * @return
     */
    public WmUserExMoney saveOrUpdate(WmUserExMoney userExMoney) {

        if (userExMoney == null) return null;
        if (userExMoney.getId() != null) return entityManager.merge(userExMoney);
        entityManager.persist(userExMoney);
        entityManager.refresh(userExMoney);

        return userExMoney;
    }

    /**
     * 根据userId查询
     * @param userId
     * @return
     */
    public WmUserExMoney queryOneByUser(Long userId) {

        String jpql = " FROM WmUserExMoney WHERE userId = " + userId;
        List<WmUserExMoney> resultList = entityManager.createQuery(jpql, WmUserExMoney.class).getResultList();
        if (resultList != null && !resultList.isEmpty()) {
            return resultList.get(0);
        }

        return null;
    }

    /**
     * 根据userId查询
     * @param id
     * @param lockType
     * @return
     */
    public WmUserExMoney queryOneById(Long id, LockModeType lockType) {

        String jpql = " FROM WmUserExMoney WHERE id = " + id;
        List<WmUserExMoney> resultList = entityManager.createQuery(jpql, WmUserExMoney.class).setLockMode(lockType).getResultList();
        if (resultList != null && !resultList.isEmpty()) {
            return resultList.get(0);
        }

        return null;
    }


    /**
     * 根据userId查询Id
     * @param userId
     * @return
     */
    public Long queryIdByUser(Long userId) {

        String jpql = " SELECT id FROM WmUserExMoney WHERE userId = " + userId;
        List<Long> resultList = entityManager.createQuery(jpql).getResultList();
        if (resultList != null && !resultList.isEmpty()) {
            return resultList.get(0);
        }

        return null;
    }
}