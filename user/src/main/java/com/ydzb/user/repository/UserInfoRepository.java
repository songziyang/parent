package com.ydzb.user.repository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/7 0007.
 */
@Repository
public class UserInfoRepository {
    @PersistenceContext
    private EntityManager manager;


    /**
     * 查询导出用户信息数据
     *
     * @param filter
     * @return
     */
    public List<Object[]> findExportData(Map<String, Object> filter) {

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT user.username, user.mobile, user.referral_mobile, invest.all_invest, user.user_type," +
                " grade.grade_name, user.account_type, user.user_source, integral.total_integral," +
                " integral.integral, user.created, user.id_card" +
                " FROM wm_user_users AS user" +
                " JOIN wm_user_money AS money ON money.user_id = user.id" +
                " JOIN wm_user_investinfo AS invest ON invest.user_id = user.id" +
                " JOIN wm_vip_grade AS grade ON user.user_leve = grade.id" +
                " LEFT JOIN wm_user_integral AS integral ON integral.user_id = user.id " +
                " WHERE user.status <> -1");

        if (filter != null) {
            String username = (String) filter.get("username");
            if (StringUtils.isNotEmpty(username)) {
                sql.append(" AND user.username LIKE '%" + username + "%'");
            }
        }
        sql.append(" ORDER BY user.created DESC");
        Query query = manager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return new ArrayList<Object[]>();
        }
        return query.getResultList();
    }
}
