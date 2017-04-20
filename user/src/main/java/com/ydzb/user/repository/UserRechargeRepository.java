package com.ydzb.user.repository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/30.
 */
@Repository
public class UserRechargeRepository {

    @PersistenceContext
    private EntityManager manager;

    /**
     * 更新用户体验金
     * @param userIds
     * @param fund
     * @return
     */
    public String updateUserMoney(List<BigInteger> userIds, BigDecimal fund) {
        if(userIds == null || userIds.isEmpty() || fund == null) {
            return null;
        }
        StringBuffer sql = new StringBuffer();
        sql.append(" UPDATE wm_user_expmoney" +
                " SET amount = amount + " + fund + "," +
                " able_money = able_money + " + fund +
                " WHERE user_id IN (");
        for (int i = 0; i < userIds.size(); i ++) {
            if (i == userIds.size() - 1) {
                sql.append(userIds.get(i));
            } else {
                sql.append(userIds.get(i) + ",");
            }
        }
        sql.append(")");
        Query query = manager.createNativeQuery(sql.toString());
        query.executeUpdate();
        return "ok";
    }


    /**
     * 查询导出excel所需要的数据
     * @return
     */
    public List<Object> findExportData(Map<String, Object> filters) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT user.username, user.mobile, recharge.order_number, recharge.money," +
                " recharge.recharge_time, recharge.serial_number, recharge.recharge_succeed_time, recharge.rechargetype, recharge.onlines, user.real_name" +
                " FROM wm_user_recharge AS recharge" +
                " LEFT JOIN wm_user_users AS user ON recharge.user_id = user.id" +
                " WHERE recharge.status = 1");
        Long startTime = (Long) filters.get("startTime");
        Long endTime = (Long) filters.get("endTime");
        if (startTime != null) {
            sql.append(" AND recharge.recharge_time >= " + startTime);
        }

        if(endTime != null) {
            sql.append(" AND recharge.recharge_time <= " + endTime);
        }
        sql.append(" ORDER BY recharge.id DESC ");
        Query query = manager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return new ArrayList<Object>();
        }
        return query.getResultList();
    }

    /**
     * 查询充值详细导出excel数据
     * @param filters
     * @return
     */
    public List<Object[]> findExportDetailData(Map<String, Object> filters) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT user.username, user.mobile, info.all_recharge, info.all_withdraw" +
                " FROM wm_user_info AS info" +
                " LEFT JOIN wm_user_users AS user ON info.user_id = user.id" +
                " WHERE info.all_recharge > 0");

        if (filters != null) {
            String username = (String) filters.get("username");
            if (StringUtils.isNotEmpty(username)) {
                sql.append(" AND user.username = '" + username + "'");
            }

            String mobile = (String) filters.get("mobile");
            if (StringUtils.isNotEmpty(mobile)) {
                sql.append(" AND user.mobile = '" + mobile + "'");
            }
        }

        sql.append(" ORDER BY info.id DESC");
        Query query = manager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return new ArrayList<Object[]>();
        }
        return query.getResultList();
    }

    /**
     * 查询导出充值状态excel
     * @param filter
     * @return
     */
    public List<Object[]> findExportResponseData(Map<String, Object> filter) {

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT user.username, user.mobile, recharge.order_number, recharge.recharge_time," +
                " recharge.status, recharge.money, recharge.recharge_succeed_time, recharge.rechargetype, recharge.onlines, user.real_name" +
                " FROM wm_user_recharge AS recharge" +
                " JOIN wm_user_users AS user ON user.id = recharge.user_id" +
                " WHERE recharge.recharge_time IS NOT null");

        if (filter != null) {
            String username = (String) filter.get("username");
            if (StringUtils.isNotEmpty(username)) {
                sql.append(" AND user.username LIKE '%" + username + "%'");
            }

            String mobile = (String) filter.get("mobile");
            if (StringUtils.isNotEmpty(mobile)) {
                sql.append(" AND user.mobile LIKE '%" + mobile + "%'");
            }

            String orderNumber = (String) filter.get("orderNumber");
            if (StringUtils.isNotEmpty(orderNumber)) {
                sql.append(" AND recharge.order_number LIKE '%" + orderNumber + "%'");
            }

            Integer rechargetype = (Integer) filter.get("rechargetype");
            if (rechargetype != null) {
                sql.append(" AND rechargetype =" + rechargetype);
            }
        }
        sql.append(" ORDER BY recharge.id DESC");
        Query query = manager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return new ArrayList<Object[]>();
        }
        return query.getResultList();
    }
}
