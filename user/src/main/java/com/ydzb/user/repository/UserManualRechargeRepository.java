package com.ydzb.user.repository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class UserManualRechargeRepository {

    @PersistenceContext
    private EntityManager manager;

    public List<Object[]> findExportData(Map<String, Object> filter) {

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT user.username, user.mobile, recharge.fund, recharge.usable_fund," +
                " admin.realname, recharge.operate_time, recharge.remark" +
                " FROM wm_manual_recharge AS recharge" +
                " JOIN wm_user_users AS user ON recharge.user_id = user.id" +
                " JOIN wm_sys_admin AS admin ON admin.id = recharge.admin_id" +
                " WHERE 1= 1");

        if (filter != null) {
            String username = (String) filter.get("username");
            if (StringUtils.isNotEmpty(username)) {
                sql.append(" AND user.username LIKE '%" + username + "%'");
            }

            String mobile = (String) filter.get("mobile");
            if (StringUtils.isNotEmpty(mobile)) {
                sql.append(" AND user.mobile LIKE '%" + mobile + "%'");
            }
        }
        sql.append(" ORDER BY recharge.operate_time DESC");
        Query query = manager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return new ArrayList<Object[]>();
        }
        return query.getResultList();
    }
}
