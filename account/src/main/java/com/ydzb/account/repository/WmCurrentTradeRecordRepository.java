package com.ydzb.account.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;

/**
 * 活期宝交易记录结算
 */
@Repository
public class WmCurrentTradeRecordRepository extends WmBaseRepository {

    /**
     * 查询每日购买活期宝的人数
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public BigInteger findDailyBuyUsersCount(final Long startTime, final Long endTime) {
        String sql = " SELECT COUNT(DISTINCT user_id) FROM wm_current_traderecord" +
                " WHERE type = 1 AND fund_source <> 2";
        if (startTime != null) {
            sql += " AND buy_time >= " + startTime;
        }
        if (endTime != null) {
            sql += " AND buy_time < " + endTime;
        }
        Query query = entityManager.createNativeQuery(sql);
        if (query != null) {
            Object obj = query.getSingleResult();
            if (obj != null) {
                return (BigInteger) obj;
            }
        }
        return BigInteger.ZERO;
    }
}