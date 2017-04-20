package com.ydzb.user.repository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户vip变更repository
 */
@Repository
public class UserVipChangeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 查询导出excel数据
     * @param filter
     * @return
     */
    public List<Object[]> findExportData(Map<String, Object> filter) {
        String sql = " SELECT users.username, users.mobile, oldVipGrade.grade_name AS gname1, newVipGrade.grade_name AS gname2, vipchange.c_type, vipchange.o_type, vipchange.optime" +
                " FROM wm_user_vip_change AS vipchange" +
                " JOIN wm_user_users AS users ON users.id = vipchange.user_id" +
                " LEFT JOIN wm_vip_grade AS oldVipGrade ON oldVipGrade.id = vipchange.old_grade_id" +
                " LEFT JOIN wm_vip_grade AS newVipGrade ON newVipGrade.id = vipchange.new_grade_id" +
                " WHERE 1 = 1";
        if (filter != null) {
            String username = (String) filter.get("username");
            if (StringUtils.isNotEmpty(username)) {
                sql += " AND users.username = '" + username + "'";
            }

            String mobile = (String) filter.get("mobile");
            if (StringUtils.isNotEmpty(mobile)) {
                sql += " AND users.mobile = '" + mobile + "'";
            }

            String cType = (String) filter.get("cType");
            if (StringUtils.isNotEmpty(cType)) {
                sql += " AND vipchange.c_type = " + Integer.valueOf(cType);
            }

            String oType = (String) filter.get("oType");
            if (StringUtils.isNotEmpty(oType)) {
                sql += " AND vipchange.o_type = " + Integer.valueOf(oType);
            }


            Long startDate = (Long) filter.get("startDate");
            if (startDate != null) {
                sql += " AND vipchange.optime >= " + startDate;
            }

            Long endDate = (Long) filter.get("endDate");
            if (endDate != null) {
                sql += " AND vipchange.optime < " + endDate;
            }
        }
        sql += " ORDER BY vipchange.id ";
        Query query = entityManager.createNativeQuery(sql);
        if (query == null) {
            return new ArrayList<>();
        }
        List<Object[]> resultSet = query.getResultList();
        if (resultSet == null) {
            return new ArrayList<>();
        }
        return resultSet;
    }
}