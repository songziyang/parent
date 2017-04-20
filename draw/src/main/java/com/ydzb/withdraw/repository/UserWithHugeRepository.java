package com.ydzb.withdraw.repository;

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
 */
@Repository
public class UserWithHugeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 获得导出excel所需数据
     *
     * @param filters
     * @return
     */
    public List<Object[]> findExportData(Map<String, Object> filters) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT users.username, users.mobile, withhuge.with_money, withhuge.able_money, withhuge.optime " +
                " FROM wm_user_withhuge AS withhuge" +
                " LEFT JOIN wm_user_users AS users ON withhuge.user_id = users.id WHERE 1 = 1");

        if (filters != null) {
            Integer status = (Integer) filters.get("status");
            if (status != null) {
                sql.append(" AND withhuge.status = " + status);
            }

            String mobile = (String) filters.get("mobile");
            if (StringUtils.isNotEmpty(mobile)) {
                sql.append(" AND users.mobile like '%" + mobile + "%'");
            }

            String username = (String) filters.get("username");
            if (StringUtils.isNotEmpty(username)) {
                sql.append(" AND users.username like '%" + username + "%'");
            }

            String startTime = (String) filters.get("startTime");
            if (StringUtils.isNotEmpty(startTime)) {
                sql.append(" AND withhuge.optime >= " + DateUtil.getSystemTimeDay(startTime));
            }

            String endTime = (String) filters.get("endTime");
            if (StringUtils.isNotEmpty(endTime)) {
                sql.append(" AND withhuge.optime < " + (DateUtil.getSystemTimeDay(endTime) + 24 * 3600));
            }
        }

        sql.append(" ORDER BY withhuge.optime DESC");
        Query query = entityManager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return new ArrayList<>();
        }
        return query.getResultList();
    }

    public void deleteWithHuge(Long id) {
        String sql = " UPDATE wm_user_withhuge SET status = 1 WHERE link_id = " + id + " AND status = 0 ";
        entityManager.createNativeQuery(sql).executeUpdate();
    }
}