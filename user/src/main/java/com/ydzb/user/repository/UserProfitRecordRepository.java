package com.ydzb.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户赠与红包收益记录repository
 */
@Repository
public class UserProfitRecordRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 查询每日用户额外收益
     * @param filter
     * @return
     */
    public Page<Object[]> findDailyFund(Map<String, Object> filter, int pageCurrent, int pageSize) {
        String sql = " SELECT SUM(fund), FROM_UNIXTIME(record_date, '%Y-%m-%d') as datestr FROM wm_user_func_grand_profit_record WHERE 1 = 1 ";
        if (filter != null) {
            Long startDate = (Long) filter.get("startDate");
            if (startDate != null) {
                sql += " AND record_date >= " + startDate;
            }

            Long endDate = (Long) filter.get("endDate");
            if (endDate != null) {
                sql += " AND record_date < " + endDate;
            }
        }
        sql += " GROUP BY datestr ORDER BY datestr DESC ";
        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> resultSet = query.setMaxResults(pageSize).setFirstResult(pageCurrent * pageSize).getResultList();
        if (query == null || resultSet == null) {
            return new PageImpl(new ArrayList<>());
        }
        int count = findDailyFundCount(filter);   //查询总条数
        return new PageImpl<>(resultSet, new PageRequest(pageCurrent, pageSize), count);
    }

    /**
     * 查询每日用户额外收益数量
     * @param filter
     * @return
     */
    public Integer findDailyFundCount(Map<String, Object> filter) {
        String sql = " SELECT COUNT(daily.fund) FROM (SELECT COUNT(fund) AS fund FROM wm_user_func_grand_profit_record WHERE 1 = 1";
        if (filter != null) {
            Long startDate = (Long) filter.get("startDate");
            if (startDate != null) {
                sql += " AND record_date >= " + startDate;
            }

            Long endDate = (Long) filter.get("endDate");
            if (endDate != null) {
                sql += " AND record_date < " + endDate;
            }
        }
        sql += " GROUP BY FROM_UNIXTIME(record_date, '%Y-%m-%d')) AS daily ";
        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> resultSet = query.getResultList();
        if (query == null || resultSet == null || resultSet.isEmpty()) {
            return 0;
        }
        return ((BigInteger) query.getSingleResult()).intValue();
    }

    /**
     * 根据记录日期段查询总收益
     * @param startTime 操作时间-开始
     * @param endTime 操作时间-结束
     * @return
     */
    public BigDecimal findSumIncome(Long startTime, Long endTime) {
        String sql = " SELECT SUM(fund) FROM wm_user_func_grand_profit_record WHERE 1 = 1";
        if (startTime != null) {
            sql += " AND record_date >= " + startTime;
        }
        if (endTime != null) {
            sql += " AND record_date < " + endTime;
        }
        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> resultSet = query.getResultList();
        if (query == null || resultSet == null || resultSet.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return (BigDecimal) query.getSingleResult();
    }

    /**
     * 查询每日额外收益导出excel数据
     * @param filter
     * @return
     */
    public List<Object[]> findDailyProfitRecordExportData(Map<String, Object> filter) {
        String sql = " SELECT SUM(fund), FROM_UNIXTIME(record_date, '%Y-%m-%d') as datestr FROM wm_user_func_grand_profit_record WHERE 1 = 1 ";
        if (filter != null) {
            Long startDate = (Long) filter.get("startDate");
            Long endDate = (Long) filter.get("endDate");

            if (startDate == null && endDate == null) {
                return new ArrayList<>();
            }

            if (startDate != null) {
                sql += " AND record_date >= " + startDate;
            }

            if (endDate != null) {
                sql += " AND record_date < " + endDate;
            }
        }
        sql += " GROUP BY datestr ORDER BY datestr DESC ";
        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> resultSet = query.getResultList();
        if (query == null || resultSet == null || resultSet.isEmpty()) {
            return new ArrayList<>();
        }
        return resultSet;
    }
}