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
 * 定存宝用户交易repository
 *
 * @author
 */
@Repository
public class BeautyUserAccountRepository {

    @PersistenceContext
    private EntityManager manager;


    public List<Object[]> findExportData(Map<String, Object> filter) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT user.username, user.mobile, " +
                " account.buy_fund, account.apr, account.grand_apr, account.interest_fund," +
                " account.status, account.buy_time, account.expire_time, account.vip_apr," +
                " account.predict_income" +
                " FROM wm_beauty_user_account AS account" +
                " JOIN wm_user_users AS user ON account.user_id = user.id" +
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

            Integer status = (Integer) filter.get("status");
            if (status != null && status != -1) {
                sql.append(" AND account.status = " + status);
            }

            Long startDate = (Long) filter.get("startDate");
            if (startDate != null) {
                sql.append(" AND expire_time >= " + startDate);
            }

            Long endDate = (Long) filter.get("endDate");
            if (endDate != null) {
                sql.append(" AND expire_time < " + endDate);
            }

        }
        sql.append(" ORDER BY account.expire_time ASC");
        Query query = manager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return new ArrayList<Object[]>();
        }
        return query.getResultList();
    }

    public EntityManager getManager() {
        return manager;
    }

    public void setManager(EntityManager manager) {
        this.manager = manager;
    }
}
