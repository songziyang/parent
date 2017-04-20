package com.ydzb.product.repository;

import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
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
 * Created by sy on 2016/9/26.
 */
@Repository
public class FreeTradeRecordRepository {

    @PersistenceContext
    private EntityManager manager;

    public List<Object[]> findExportData(Map<String, Object> filter) {

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT user.username, user.mobile, record.days, record.names," +
                " record.type, record.fund, record.buy_time FROM wm_free_traderecord AS record " +
                " LEFT JOIN wm_user_users AS user ON user.id = record.user_id " +
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
            if (StringUtils.isNotEmpty(startDate)) {
                sql.append(" AND record.buy_time >= " + DateUtil.getSystemTimeDay(startDate));
            }

            String endDate = (String) filter.get("endDate");
            if (StringUtils.isNotEmpty(endDate)) {
                sql.append(" AND record.buy_time < " + DateUtil.getSystemTimeDay(DateUtil.addDay(endDate)));
            }

            String startDays = (String) filter.get("startDays");
            if (StringUtils.isNotEmpty(startDays)) {
                sql.append(" AND record.days >= " + startDays);
            }

            String endDays = (String) filter.get("endDays");
            if (StringUtils.isNotEmpty(endDays)) {
                sql.append(" AND record.days < " + endDays);
            }

            String type = (String) filter.get("type");
            if (StringUtils.isNotEmpty(type)) {
                sql.append(" AND record.type = " + type);
            }
        }
        sql.append(" ORDER BY record.id DESC");
        Query query = manager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return new ArrayList<>();
        }
        return query.getResultList();
    }
}