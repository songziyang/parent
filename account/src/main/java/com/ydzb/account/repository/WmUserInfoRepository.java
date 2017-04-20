package com.ydzb.account.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.List;

/**
 * 用户信息
 */
@Repository
public class WmUserInfoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public BigInteger queryDailySignCount(Long startTime, Long endTime) {

        String sql = " SELECT COUNT(id) FROM wm_user_info WHERE sign_time >= " + startTime + " AND sign_time < " + endTime;
        List<BigInteger> resultList = entityManager.createNativeQuery(sql).getResultList();
        return (resultList != null && !resultList.isEmpty())? resultList.get(0): BigInteger.ZERO;
    }
}