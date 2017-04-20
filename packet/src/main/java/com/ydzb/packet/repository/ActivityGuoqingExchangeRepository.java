package com.ydzb.packet.repository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 国庆活动兑换repository
 */
@Repository
public class ActivityGuoqingExchangeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 查询导出excel数据
     *
     * @param filter
     * @return
     */
    public List<Object[]> findExportData(Map<String, Object> filter) {

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT user.username, user.mobile AS u_mobile, exchange.type, exchange.status,  exchange.product_name , exchange.realname, " +
                " exchange.mobile, exchange.addr,  exchange.created" +
                " FROM wm_activity_shiyi_exchange AS exchange" +
                " LEFT JOIN wm_user_users AS user ON exchange.user_id = user.id" +
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

            Integer type = (Integer) filter.get("type");
            if (type != null) {
                sql.append(" AND exchange.type = " + type);
            }

            Integer status = (Integer) filter.get("status");
            if (status == null) {
                status = 1;
            }
            sql.append(" AND exchange.status = " + status);
        }
        sql.append(" ORDER BY exchange.created DESC");

        List<Object[]> resultList = entityManager.createNativeQuery(sql.toString()).getResultList();
        if (resultList == null || resultList.isEmpty()) {
            return new ArrayList<>();
        }
        return resultList;
    }
}
