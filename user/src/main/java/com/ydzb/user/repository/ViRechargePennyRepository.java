package com.ydzb.user.repository;

import com.ydzb.core.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 小额充值repository
 */
@Repository
public class ViRechargePennyRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 查询导出excel数据
     * @param filter
     * @return
     */
    public List<Object[]> findExportData(Map<String, Object> filter) {

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT user.username, user.mobile AS u_mobile, user.real_name, recharge.serial_number," +
                " recharge.money, recharge.recharge_time, recharge.rechargetype, recharge.onlines FROM vi_recharge_penny AS recharge " +
                " LEFT JOIN wm_user_users AS user ON recharge.user_id = user.id" +
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

            String startMoney = (String) filter.get("startMoney");
            if (StringUtils.isNotEmpty(startMoney)) {
                sql.append(" AND recharge.money >= " + startMoney);
            }

            String endMoney = (String) filter.get("endMoney");
            if (StringUtils.isNotEmpty(endMoney)) {
                sql.append(" AND recharge.money <= " + endMoney);
            }

            String startDays = (String) filter.get("startDays");
            if (StringUtils.isNotEmpty(startDays)) {
                sql.append(" AND recharge.recharge_time >= " + DateUtil.getSystemTimeDay(startDays));
            }

            String endDays = (String) filter.get("endDays");
            if (StringUtils.isNotEmpty(endDays)) {
                sql.append(" AND recharge.recharge_time < " + DateUtil.getSystemTimeDay(DateUtil.addDay(endDays)));
            }

            Object rechargetype = filter.get("rechargetype");
            if (rechargetype != null) {
                if ((Integer) rechargetype == 1) {
                    sql.append(" AND recharge.rechargetype = " + (Integer) rechargetype);
                }
                else {
                    sql.append(" AND recharge.rechargetype <> 1 ");
                }
            }

            Object onlines = filter.get("onlines");
            if (onlines != null) {
                if ((Integer) onlines == 1) {
                    sql.append(" AND recharge.onlines = " + (Integer) onlines);
                }
                else {
                    sql.append(" AND recharge.onlines <> 1 ");
                }
            }
        }
        sql.append(" ORDER BY recharge.recharge_time DESC");

        List<Object[]> resultList = entityManager.createNativeQuery(sql.toString()).getResultList();
        if (resultList == null || resultList.isEmpty()) {
            return new ArrayList<>();
        }
        return resultList;
    }
}