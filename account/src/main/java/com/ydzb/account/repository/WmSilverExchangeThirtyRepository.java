package com.ydzb.account.repository;

import com.ydzb.account.entity.WmSilverExchangeThirty;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * 30亿活动兑换repository
 */
@Repository
public class WmSilverExchangeThirtyRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 查询兑换所有产品的人数
     * @param startTime
     * @param endTime
     * @return
     */
    public Integer queryAllExchangeUserCount(Long startTime, Long endTime) {

        String sql = " SELECT COUNT(DISTINCT userId) FROM WmSilverExchangeThirty " +
                " WHERE created >= " + startTime + " AND created < " + endTime;
        List<Long> resultList = entityManager.createQuery(sql).getResultList();
        return (resultList != null && !resultList.isEmpty())? resultList.get(0) == null? null: resultList.get(0).intValue(): null;
    }

    /**
     * 查询兑换实物产品的人数
     * @param startTime
     * @param endTime
     * @return
     */
    public Integer queryGoodsExchangeUserCount(Long startTime, Long endTime) {
        String sql = " SELECT COUNT(DISTINCT userId) FROM WmSilverExchangeThirty " +
                " WHERE created >= " + startTime + " AND created < " + endTime +
                " AND (level is null or (level >= " + WmSilverExchangeThirty.LEVEL_GOODSMIN + " AND level <= " + WmSilverExchangeThirty.LEVEL_GOODSMAX + "))";
        List<Long> resultList = entityManager.createQuery(sql).getResultList();
        return (resultList != null && !resultList.isEmpty())? resultList.get(0) == null? null: resultList.get(0).intValue(): null;
    }
}