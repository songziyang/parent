package com.ydzb.packet.repository;

import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import com.ydzb.core.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 体验进交易repository
 */
@Repository
public class ExpMoneyRecordRepository {

    @PersistenceContext
    private EntityManager manager;

    public List<Object[]> findExportData(Map<String, Object> filter) {

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT user.username, user.mobile, record.fundflow, record.type," +
                " record.fund, record.balance, record.record_time" +
                " FROM wm_user_expmoney_record AS record" +
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
                sql.append(" AND record.record_time >= " + startDate);
            }

            Long endDate = (Long) filter.get("endDate");
            if (endDate != null) {
                sql.append(" AND record.record_time < " + endDate);
            }

            Byte type = (Byte) filter.get("type");
            if (type != null) {
                sql.append(" AND record.type = " + type);
            }
        }

        sql.append(" ORDER BY record.record_time DESC");
        Query query = manager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return new ArrayList<Object[]>();
        }
        return query.getResultList();
    }
}
