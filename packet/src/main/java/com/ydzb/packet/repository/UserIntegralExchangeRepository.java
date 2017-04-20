package com.ydzb.packet.repository;

import com.ydzb.core.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class UserIntegralExchangeRepository {

    @PersistenceContext
    private EntityManager manager;

    public List<Object[]> findExportData(Map<String, Object> filter) {

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT user.username, user.mobile AS u_mobile, integral.type,integral.status, integral.product_name, integral.realname," +
                " integral.mobile, integral.address, integral.remark, integral.created, integral.number, integral.integral" +
                " FROM wm_user_integral_exchange AS integral" +
                " JOIN wm_user_users AS user ON integral.user_id = user.id" +
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
                sql.append(" AND integral.type = " + type);
            }

            Integer status = (Integer) filter.get("status");
            if (status == null) {
                status = 1;
            }

            String startDate = (String) filter.get("startDate");
            if (StringUtils.isNotEmpty(startDate)) {
                sql.append(" AND integral.created >= " + DateUtil.getSystemTimeDay(startDate));
            }

            String endDate = (String) filter.get("endDate");
            if (StringUtils.isNotEmpty(endDate)) {
                sql.append(" AND integral.created < " + DateUtil.getSystemTimeDay(DateUtil.addDay(endDate)));
            }

            sql.append(" AND integral.status = " + status);
        }
        sql.append(" ORDER BY integral.created DESC");

        Query query = manager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return new ArrayList<Object[]>();
        }
        return query.getResultList();
    }
}
