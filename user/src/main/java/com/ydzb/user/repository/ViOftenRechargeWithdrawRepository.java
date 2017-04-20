package com.ydzb.user.repository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 当/次日提现repository
 */
@Repository
public class ViOftenRechargeWithdrawRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 查询导出excel数据
     * @param filter
     * @return
     */
    public List<Object[]> findExportData(Map<String, Object> filter) {

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT user.username, user.mobile AS u_mobile, user.real_name, often.recharge_money," +
                " often.recharge_time, often.withdraw_money, often.withdraw_time" +
                " FROM vi_often_recharge_withdraw AS often " +
                " LEFT JOIN wm_user_users AS user ON often.user_id = user.id" +
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

            String realName = (String) filter.get("realName");
            if (StringUtils.isNotEmpty(mobile)) {
                sql.append(" AND user.real_name = '" + realName + "'");
            }

            String startRechargeDays = (String) filter.get("startRechargeDays");
            if (StringUtils.isNotEmpty(startRechargeDays)) {
                sql.append(" AND often.recharge_time >= " + startRechargeDays);
            }

            String endRechargeDays = (String) filter.get("endRechargeDays");
            if (StringUtils.isNotEmpty(endRechargeDays)) {
                sql.append(" AND often.recharge_time < " + endRechargeDays);
            }

            String startWithdrawDays = (String) filter.get("startWithdrawDays");
            if (StringUtils.isNotEmpty(startWithdrawDays)) {
                sql.append(" AND often.withdraw_time >= " + startWithdrawDays);
            }

            String endWithdrawDays = (String) filter.get("endWithdrawDays");
            if (StringUtils.isNotEmpty(endWithdrawDays)) {
                sql.append(" AND often.withdraw_time < " + endWithdrawDays);
            }
        }
        sql.append(" ORDER BY often.withdraw_time DESC");

        List<Object[]> resultList = entityManager.createNativeQuery(sql.toString()).getResultList();
        if (resultList == null || resultList.isEmpty()) {
            return new ArrayList<>();
        }
        return resultList;
    }
}