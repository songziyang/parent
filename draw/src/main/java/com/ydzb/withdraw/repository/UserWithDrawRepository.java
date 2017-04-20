package com.ydzb.withdraw.repository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户提现repository
 */
@Repository(value = "userWithDrawRepository")
public class UserWithDrawRepository {

    @PersistenceContext
    private EntityManager manager;

    /**
     * 获得导出excel所需数据
     * @param filters
     * @return
     */
    public List<Object[]> findExportData(Map<String, Object> filters) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT withdraw.realname AS rname, user.username AS username, user.mobile AS usermobile, withdraw.withdraw_money AS usermoney," +
                " withdraw.advance_money AS advmoney, withdraw.suc_amt, withdraw.application_time," +
                " withdraw.audit_time, withdraw.transfer_time, withdraw.pay_time, withdraw.status AS wstt, withdraw.draw_auto , withdraw.withdrawtype " +
                " FROM wm_user_withdraw AS withdraw" +
                " JOIN wm_user_users AS user ON withdraw.user_id = user.id" +
                " WHERE 1 = 1");

        if (filters != null) {
            Integer status = (Integer) filters.get("status");
            if (status != null) {
                sql.append(" AND withdraw.status = " + status);
            }


            Integer drawAuto = (Integer) filters.get("drawAuto");
            if (drawAuto != null) {
                sql.append(" AND withdraw.draw_auto = " + drawAuto);
            }

            Integer withdrawtype = (Integer) filters.get("withdrawtype");
            if (withdrawtype != null) {
                sql.append(" AND withdraw.withdrawtype = " + withdrawtype);
            }

            String realname = (String) filters.get("realname");
            if (StringUtils.isNotEmpty(realname)) {
                sql.append(" AND withdraw.realname = '" + realname + "'");
            }

            String mobile = (String) filters.get("mobile");
            if (StringUtils.isNotEmpty(mobile)) {
                sql.append(" AND user.mobile = '" + mobile + "'");
            }

            String username = (String) filters.get("username");
            if (StringUtils.isNotEmpty(username)) {
                sql.append(" AND user.username = '" + username + "'");
            }

            Long applicationTimeStart = (Long) filters.get("applicationTimeStart");
            if (applicationTimeStart != null) {
                sql.append(" AND withdraw.application_time >= " + applicationTimeStart);
            }

            Long applicationTimeEnd = (Long) filters.get("applicationTimeEnd");
            if (applicationTimeEnd != null) {
                sql.append(" AND withdraw.application_time < " + applicationTimeEnd);
            }

            Long payTimeStart = (Long) filters.get("payTimeStart");
            if (payTimeStart != null) {
                sql.append(" AND withdraw.pay_time >= " + payTimeStart);
            }

            Long payTimeEnd = (Long) filters.get("payTimeEnd");
            if (payTimeEnd != null) {
                sql.append(" AND withdraw.pay_time < " + payTimeEnd);
            }

            Boolean poundage = (Boolean) filters.get("poundage");
            if (poundage != null && poundage) {
                sql.append(" AND withdraw.withdraw_money > withdraw.advance_money");
            }
        }

        sql.append(" ORDER BY withdraw.application_time DESC");
        Query query = manager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return new ArrayList<Object[]>();
        }
        return query.getResultList();
    }

    /**
     * 查询总提现金额以及打款金额
     * @param filters
     * @return
     */
    public Object[] findSumMoney(Map<String, Object> filters) {
        StringBuffer sql = new StringBuffer(" SELECT IFNULL(sum(withdraw_money),0), IFNULL(sum(advance_money),0) FROM wm_user_withdraw AS withdraw" +
                " JOIN wm_user_users AS user ON user.id = withdraw.user_id WHERE 1 = 1");
        if (filters != null) {
            Boolean poundage = (Boolean) filters.get("poundage");
            Integer status = (Integer) filters.get("status");
            sql.append(" AND withdraw.status = " + status);

            Integer drawAuto = (Integer) filters.get("drawAuto");
            if (drawAuto != null) {
                sql.append(" AND withdraw.draw_auto = " + drawAuto);
            }

            Integer withdrawtype = (Integer) filters.get("withdrawtype");
            if (withdrawtype != null) {
                sql.append(" AND withdraw.withdrawtype = " + withdrawtype);
            }

            String realname = (String) filters.get("realname");
            if (StringUtils.isNotEmpty(realname)) {
                sql.append(" AND withdraw.realname = '" + realname + "'");
            }

            String mobile = (String) filters.get("mobile");
            if (StringUtils.isNotEmpty(mobile)) {
                sql.append(" AND user.mobile = '" + mobile + "'");
            }

            String username = (String) filters.get("username");
            if (StringUtils.isNotEmpty(username)) {
                sql.append(" AND user.username = '" + username + "'");
            }

            Long payTimeStart = (Long) filters.get("payTimeStart");
            if (payTimeStart != null) {
                sql.append(" AND withdraw.pay_time >= " + payTimeStart);
            }

            Long payTimeEnd = (Long) filters.get("payTimeEnd");
            if (payTimeEnd != null) {
                sql.append(" AND withdraw.pay_time < " + payTimeEnd);
            }

            if (poundage != null && poundage) {
                sql.append(" AND withdraw.withdraw_money > withdraw.advance_money");
            }
        }
        Query query = manager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null) {
            return new Object[]{0, 0};
        }
        return (Object[]) query.getSingleResult();
    }


    public EntityManager getManager() {
        return manager;
    }

    public void setManager(EntityManager manager) {
        this.manager = manager;
    }
}