package com.ydzb.packet.repository;

import com.ydzb.core.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 30亿活动兑换repository
 */
@Repository
public class Activity3BillionExchangeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 查询导出excel数据
     * @param filter
     * @return
     */
    public List<Object[]> findExportData(Map<String, Object> filter) {

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT user.username, user.mobile AS u_mobile, exchange.type, exchange.realname," +
                " exchange.mobile, exchange.addr, exchange.status, exchange.created, exchange.operation_time" +
                " FROM wm_activity_thirty_exchange AS exchange" +
                " JOIN wm_user_users AS user ON exchange.user_id = user.id" +
                " WHERE 1 = 1");

        if (filter != null) {
            String username = (String) filter.get("username");
            if (StringUtils.isNotEmpty(username)) {
                sql.append(" AND user.username LIKE '%" + username + "%'");
            }

            String mobile = (String) filter.get("mobile");
            if (StringUtils.isNotEmpty(mobile)) {
                sql.append(" AND user.mobile LIKE '%" + mobile + "%'");
            }

            String type = (String) filter.get("type");
            if (StringUtils.isNotEmpty(type)) {
                sql.append(" AND exchange.type = " + Integer.valueOf(type));
            }

            String status = (String) filter.get("status");
            if (StringUtils.isEmpty(status)) {
                status = "1";
            }

            String startDate = (String) filter.get("startDate");
            if (StringUtils.isNotEmpty(startDate)) {
                sql.append(" AND exchange.created >= " + DateUtil.getSystemTimeDay(startDate));
            }

            String endDate = (String) filter.get("endDate");
            if (StringUtils.isNotEmpty(endDate)) {
                sql.append(" AND exchange.created < " + DateUtil.getSystemTimeDay(DateUtil.addDay(endDate)));
            }

            sql.append(" AND exchange.status = " + Integer.valueOf(status));
        }
        sql.append(" ORDER BY exchange.created DESC");

        List<Object[]> resultList = entityManager.createNativeQuery(sql.toString()).getResultList();
        if (resultList == null || resultList.isEmpty()) {
            return new ArrayList<>();
        }
        return resultList;
    }
}
