package com.ydzb.account.repository;

import com.ydzb.account.entity.WmSilverTradeRecordThirty;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * 30亿活动银多币记录repository
 */
@Repository
public class WmSilverTradeRecordThirtyRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 保存或更改
     * @param wmSilverTradeRecordThirty
     * @return
     */
    public WmSilverTradeRecordThirty saveOrUpdate(WmSilverTradeRecordThirty wmSilverTradeRecordThirty) {
        if (wmSilverTradeRecordThirty == null) return null;
        if (wmSilverTradeRecordThirty.getId() != null) return entityManager.merge(wmSilverTradeRecordThirty);
        entityManager.persist(wmSilverTradeRecordThirty);
        entityManager.refresh(wmSilverTradeRecordThirty);
        return wmSilverTradeRecordThirty;
    }

    public Integer queryLotteryUserCount(Long startTime, Long endTime) {

        String jpql = " SELECT COUNT(DISTINCT userId) FROM WmSilverTradeRecordThirty" +
                " WHERE type = " + WmSilverTradeRecordThirty.TYPE_USE +
                " AND fund = 1 AND created >= " + startTime + " AND created < " + endTime;
        List<Long> resultList = entityManager.createQuery(jpql).getResultList();
        return (resultList != null && !resultList.isEmpty())? (resultList.get(0) == null? null: resultList.get(0).intValue()): null;
    }
}