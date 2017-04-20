package com.ydzb.user.repository;

import org.apache.commons.lang3.StringUtils;
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
 * 用户收益记录repository
 */
@Repository
public class UserIncomeRecordRepository {

    @PersistenceContext
    private EntityManager manager;

    public List<Object[]> findExportData(Map<String, Object> filter) {

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT user.username, user.mobile, income.income, income.name, income.ptype, income.optime" +
                " FROM wm_user_income_record AS income" +
                " JOIN wm_user_users AS user ON user.id = income.user_id" +
                " WHERE user.status = 0");

        if (filter != null) {
            String username = (String) filter.get("username");
            if (StringUtils.isNotEmpty(username)) {
                sql.append(" AND user.username = '" + username + "'");
            }

            String mobile = (String) filter.get("mobile");
            if (StringUtils.isNotEmpty(mobile)) {
                sql.append(" AND user.mobile = '" + mobile + "'");
            }

            Byte ptype = (Byte) filter.get("ptype");
            if (ptype != null && !"".equals(ptype)) {
                sql.append(" AND income.ptype = " + ptype);
            }

            Long startDate = (Long) filter.get("startDate");
            if (startDate != null) {
                sql.append(" AND income.optime >= " + startDate);
            }

            Long endDate = (Long) filter.get("endDate");
            if (endDate != null) {
                sql.append(" AND income.optime < " + endDate);
            }
        }
        Query query = manager.createNativeQuery(sql.toString());
        List<Object[]> resultSet = query.getResultList();
        if (query == null || resultSet == null || resultSet.isEmpty()) {
            return new ArrayList<>();
        }
        return resultSet;
    }

    /**
     * 根据操作时间段查询总收益
     * @param startTime 操作时间-开始
     * @param endTime 操作时间-结束
     * @return
     */
    public BigDecimal findSumIncome(Long startTime, Long endTime) {
        String sql = " SELECT SUM(income) FROM wm_user_income_record WHERE 1 = 1";
        if (startTime != null) {
            sql += " AND optime >= " + startTime;
        }
        if (endTime != null) {
            sql += " AND optime < " + endTime;
        }
        Query query = manager.createNativeQuery(sql);
        List<Object[]> resultSet = query.getResultList();
        if (query == null || resultSet == null || resultSet.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return (BigDecimal) query.getSingleResult();
    }

    /**
     * 查询每日收益
     * @param filter
     * @param pageCurrent 当前页数(从0开始)
     * @param pageSize 每页显示条数
     * @return
     */
    public Page<Object[]> findDailyIncome(Map<String, Object> filter, int pageCurrent, int pageSize) {
        String sql = " SELECT SUM(income), FROM_UNIXTIME(optime, '%Y-%m-%d') as datestr FROM wm_user_income_record WHERE 1 = 1 ";
        if (filter != null) {
            Long startDate = (Long) filter.get("startDate");

            if (startDate != null) {
                sql += " AND optime >= " + startDate;
            }

            Long endDate = (Long) filter.get("endDate");
            if (endDate != null) {
                sql += " AND optime < " + endDate;
            }
        }
        sql += " GROUP BY datestr ORDER BY datestr DESC ";
        Query query = manager.createNativeQuery(sql);
        List<Object[]> resultSet = query.setMaxResults(pageSize).setFirstResult(pageCurrent * pageSize).getResultList();
        if (query == null || resultSet == null) {
            return new PageImpl(new ArrayList<>());
        }
        int count = findDailyIncomeCount(filter);   //查询总条数
        return new PageImpl<>(resultSet, new PageRequest(pageCurrent, pageSize), count);
    }

    /**
     * 查询每日收益数量
     * @param filter
     * @return
     */
    public Integer findDailyIncomeCount(Map<String, Object> filter) {
        String sql = " SELECT COUNT(daily.income) FROM (SELECT COUNT(income) AS income FROM wm_user_income_record WHERE 1 = 1";
        if (filter != null) {
            Long startDate = (Long) filter.get("startDate");
            if (startDate != null) {
                sql += " AND optime >= " + startDate;
            }

            Long endDate = (Long) filter.get("endDate");
            if (endDate != null) {
                sql += " AND optime < " + endDate;
            }
        }
        sql += " GROUP BY FROM_UNIXTIME(optime, '%Y-%m-%d')) AS daily  ";
        Query query = manager.createNativeQuery(sql);
        List<Object[]> resultSet = query.getResultList();
        if (query == null || resultSet == null || resultSet.isEmpty()) {
            return 0;
        }
        return ((BigInteger) query.getSingleResult()).intValue();
    }

    /**
     * 查询每日收益导出excel数据
     * @param filter
     * @return
     */
    public List<Object[]> findDailyIncomeExportData(Map<String, Object> filter) {
        String sql = " SELECT SUM(income), FROM_UNIXTIME(optime, '%Y-%m-%d') as datestr FROM wm_user_income_record WHERE 1 = 1 ";
        if (filter != null) {
            Long startDate = (Long) filter.get("startDate");
            Long endDate = (Long) filter.get("endDate");

            if (startDate == null && endDate == null) {
                return new ArrayList<>();
            }
            if (startDate != null) {
                sql += " AND optime >= " + startDate;
            }

            if (endDate != null) {
                sql += " AND optime < " + endDate;
            }
        }
        sql += " GROUP BY datestr ORDER BY datestr DESC ";
        Query query = manager.createNativeQuery(sql);
        List<Object[]> resultSet = query.getResultList();
        if (query == null || resultSet == null || resultSet.isEmpty()) {
            return new ArrayList<>();
        }
        return resultSet;
    }
}