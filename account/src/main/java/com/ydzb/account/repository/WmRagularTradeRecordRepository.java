package com.ydzb.account.repository;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 定存交易记录repository
 */
@Repository
public class WmRagularTradeRecordRepository extends WmBaseRepository {

    /**
     * 根据起始时间查询定存宝交易记录id以及购买金额
     * 复投类型
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    public List<Object[]> queryRagularInfoBetweenTime(Long startTime, Long endTime) {

        String jpql = " SELECT userId, SUM(fund), days FROM WmRagularTradeRecored WHERE type = 3" +
                " AND buyTime >= " + startTime + " AND buyTime < " + endTime;
        List<Object[]> resultList = entityManager.createQuery(jpql).getResultList();
        return resultList == null? new ArrayList<Object[]>(): resultList;
    }
}