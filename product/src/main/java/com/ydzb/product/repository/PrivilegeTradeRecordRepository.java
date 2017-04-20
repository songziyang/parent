package com.ydzb.product.repository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 新手宝交易记录repository
 */
@Repository
public class PrivilegeTradeRecordRepository {

    @PersistenceContext
    private EntityManager manager;

    public List<Object[]> findExportData(Map<String, Object> filter) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT user.username, user.mobile, record.names, record.type," +
                " record.fund_source, record.fund, record.buy_time" +
                " FROM wm_privilege_traderecord AS record" +
                " JOIN wm_user_users AS user ON record.user_id = user.id" +
                " WHERE 1 = 1");

        if (filter != null) {
            String username = (String) filter.get("username");
            if (StringUtils.isNotEmpty(username)) {
                sql.append(" AND user.username = '" + username + "'");
            }

            String mobile = (String) filter.get("mobile");
            if (StringUtils.isNotEmpty(mobile)) {
                sql.append(" AND user.mobile = '" + mobile + "'");
            }

            Long startDate = (Long) filter.get("startDate");
            if (startDate != null) {
                sql.append(" AND record.buy_time >= " + startDate);
            }

            Long endDate = (Long) filter.get("endDate");
            if (endDate != null) {
                sql.append(" AND record.buy_time < " + endDate);
            }

            Integer type = (Integer) filter.get("type");
            if (type != null) {
                sql.append(" AND record.type = " + type);
            }

            Integer fundSource = (Integer) filter.get("fundSource");
            if (fundSource != null) {
                sql.append(" AND record.fund_source = " + fundSource);
            }
        }
        sql.append(" ORDER BY buy_time DESC");
        Query query = manager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return new ArrayList<Object[]>();
        }
        return query.getResultList();
    }
}
