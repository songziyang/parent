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
 * 新手宝已购记录repository
 */
@Repository
public class PrivilegeUserAccountRepository {

    @PersistenceContext
    private EntityManager manager;

    /**
     * 查询导出excel的数据
     * @param filters
     * @return
     */
    public List<Object[]> findExportData(Map<String, Object> filters) {

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT user.username, user.mobile, account.all_fund, account.profit,account.wait_amount" +
                " FROM wm_privilege_user_account AS account" +
                " JOIN wm_user_users AS user ON user.id = account.user_id" +
                " WHERE user.status <> -1" +
                " AND account.all_fund > 0 ");

        if (filters != null) {
            String username = (String) filters.get("username");
            if (!StringUtils.isEmpty(username)) {
                sql.append(" AND user.username LIKE '%" + username + "%'");
            }
           /* Long startDate = (Long) filters.get("startDate");
            if (startDate != null) {
                sql.append(" AND account.buy_time >= " + startDate);
            }

            Long endDate = (Long) filters.get("endDate");
            if (endDate != null) {
                sql.append(" AND account.buy_time < " + endDate);
            }*/
            String mobile = (String) filters.get("mobile");
            if (!StringUtils.isEmpty(mobile)) {
                sql.append(" AND user.mobile LIKE '%" + mobile + "%'");
            }
        }

        sql.append(" ORDER BY account.all_fund DESC");

        Query query = manager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return new ArrayList<Object[]>();
        }
        return query.getResultList();
    }
}
