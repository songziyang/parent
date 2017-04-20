package com.ydzb.packet.repository;

import com.ydzb.user.entity.UserIntegral;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class UserIntegralRecordRepository {

    @PersistenceContext
    private EntityManager manager;

    public List<Object[]> findExportData(Map<String, Object> filter) {

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT user.username, user.mobile AS u_mobile, integral.fundflow , integral.type , integral.integral , integral.balance, integral.created" +
                " FROM wm_user_integral_record AS integral" +
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

            Long startDate = (Long) filter.get("startDate");
            if (startDate != null) {
                sql.append(" AND integral.created >= " + startDate);
            }

            Long endDate = (Long) filter.get("endDate");
            if (endDate != null) {
                sql.append(" AND integral.created < " + endDate);
            }


        }
        sql.append(" ORDER BY integral.created DESC");

        Query query = manager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return new ArrayList<Object[]>();
        }
        return query.getResultList();
    }

    /**
     * 更新用户积分
     *
     * @param UserIntegral 用户积分
     */
    public void updateUserIntegral(UserIntegral UserIntegral) {
        manager.merge(UserIntegral);
    }


    /**
     * 保存用户积分
     *
     * @param userIntegral 用户积分
     */
    public void saveUserIntegral(UserIntegral userIntegral) {
        manager.persist(userIntegral);
    }



}
