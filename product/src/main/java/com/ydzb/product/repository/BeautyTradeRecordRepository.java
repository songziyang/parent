package com.ydzb.product.repository;

import com.ydzb.core.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 定存宝交易repository
 */
@Repository
public class BeautyTradeRecordRepository {

    @PersistenceContext
    private EntityManager manager;

    public List<Object[]> findExportData(Map<String, Object> filter) {

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT user.username, user.mobile, record.days, record.names," +
                " record.type, record.fund, record.buy_time" +
                " FROM wm_beauty_traderecord AS record" +
                " LEFT JOIN wm_user_users AS user ON user.id = record.user_id" +
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

            String startDate = (String) filter.get("startDate");
            if (startDate != null) {
                sql.append(" AND record.buy_time >= " + DateUtil.getSystemTimeDay(startDate));
            }

            String endDate = (String) filter.get("endDate");
            if (endDate != null) {
                sql.append(" AND record.buy_time < " + DateUtil.getSystemTimeDay(DateUtil.addDay(endDate)));
            }

            String type = (String) filter.get("type");
            if (StringUtils.isNotEmpty(type)) {
                sql.append(" AND record.type = " + type);
            }

            String days = (String) filter.get("days");
            if (StringUtils.isNotEmpty(days)) {
                sql.append(" AND record.days = " + days);
            }
        }
        sql.append(" ORDER BY record.buy_time DESC");
        Query query = manager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return new ArrayList<>();
        }
        return query.getResultList();
    }
}
