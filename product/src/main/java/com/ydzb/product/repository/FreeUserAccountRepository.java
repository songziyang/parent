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
 * Created by sy on 2016/10/2.
 */
@Repository
public class FreeUserAccountRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Object[]> findExportData(Map<String, Object> filter) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT user.username, user.mobile, product.name, account.days," +
                " account.buy_fund, account.apr, account.grand_apr, account.vip_apr," +
                " account.interest_fund, account.predict_income, account.status, account.buy_type, account.expire_time" +
                " FROM wm_free_user_account AS account" +
                " LEFT JOIN wm_user_users AS user ON account.user_id = user.id" +
                " LEFT JOIN wm_product_info AS product ON product.id = account.product_id" +
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

            String status = (String) filter.get("status");
            if (StringUtils.isNotEmpty(status)) {
                sql.append(" AND account.status = " + status);
            }

            String startDate = (String) filter.get("startDate");
            if (StringUtils.isNotEmpty(startDate)) {
                sql.append(" AND expire_time >= " + DateUtil.getSystemTimeDay(startDate));
            }

            String endDate = (String) filter.get("endDate");
            if (StringUtils.isNotEmpty(endDate)) {
                sql.append(" AND expire_time < " + DateUtil.getSystemTimeDay(DateUtil.addDay(endDate)));
            }

            String startDays = (String) filter.get("startDays");
            if (StringUtils.isNotEmpty(startDays)) {
                sql.append(" AND account.days >= " + startDays);
            }

            String endDays = (String) filter.get("endDays");
            if (StringUtils.isNotEmpty(endDays)) {
                sql.append(" AND account.days < " + endDays);
            }
        }
        sql.append(" ORDER BY account.expire_time ASC");
        Query query = entityManager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return new ArrayList<>();
        }
        return query.getResultList();
    }
}