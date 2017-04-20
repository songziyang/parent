package com.ydzb.account.repository;

import com.ydzb.account.entity.WmActivityGuoqing;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.List;

/**
 */
@Repository
public class WmActivityGuoqingRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 根据用户查询id
     * @param userId
     * @return
     */
    public Long queryIdByUser(Long userId) {
        String ql = " SELECT id FROM WmActivityGuoqing WHERE userId = " + userId;
        List<Long> resultList = entityManager.createQuery(ql).getResultList();
        return (resultList != null && !resultList.isEmpty())? resultList.get(0): null;
    }

    /**
     * 根据用户查询
     * @param userId
     * @return
     */
    public WmActivityGuoqing queryByUser(Long userId, LockModeType lockType) {

        if (lockType == null || lockType == LockModeType.NONE) {
            String ql = " FROM WmActivityGuoqing WHERE userId = " + userId;
            List<WmActivityGuoqing> resultList = entityManager.createQuery(ql).getResultList();
            return (resultList != null && !resultList.isEmpty())? resultList.get(0): null;
        }
        Long id = queryIdByUser(userId);
        return id == null? null: entityManager.find(WmActivityGuoqing.class, id, lockType);
    }

    /**
     * 创建或更新
     * @param wmActivityGuoqing
     * @return
     */
    public WmActivityGuoqing createOrUpdate(WmActivityGuoqing wmActivityGuoqing) {
        if (wmActivityGuoqing == null) return null;
        if (wmActivityGuoqing.getId() != null) return entityManager.merge(wmActivityGuoqing);
        entityManager.persist(wmActivityGuoqing);
        return wmActivityGuoqing;
    }

    /**
     * 查询有福包的用户国庆信息
     * @return
     */
    public List<Object[]> queryOnesHavingLuckbag() {
        String ql = " SELECT id, userId, usableFund FROM WmActivityGuoqing WHERE usableFund > 0 ";
        return entityManager.createQuery(ql).getResultList();
    }

    public WmActivityGuoqing queryById(Long id, LockModeType lockType) {
        if (lockType == null) lockType = LockModeType.NONE;
        return entityManager.find(WmActivityGuoqing.class, id, lockType);
    }
}