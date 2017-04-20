package com.ydzb.product.repository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

/**
 * 转转赚交易记录repository
 */
@Repository
public class StructureDealRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Object[]> findExportData(Map<String, Object> filter) {

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT structure.name, users.username, users.mobile, deal.copies, deal.btime, deal.close_date, deal.apr, deal.state" +
                " FROM wm_structure_deal AS deal" +
                " LEFT JOIN wm_user_users AS users ON users.id = deal.user_id" +
                " LEFT JOIN wm_structure AS structure ON structure.id = deal.structure_id" +
                " WHERE 1 = 1");
        if (filter != null) {

            Long structureId = (Long) filter.get("structureId");
            if (structureId != null) {
                sql.append(" AND structure.id = " + structureId);
            }

            String username = (String) filter.get("username");
            if (StringUtils.isNotEmpty(username)) {
                sql.append(" AND users.username LIKE '%" + username + "%'");
            }

            String mobile = (String) filter.get("mobile");
            if (StringUtils.isNotEmpty(mobile)) {
                sql.append(" AND users.mobile LIKE '%" + mobile + "%'");
            }

            Long startDate = (Long) filter.get("startDate");
            if (startDate != null) {
                sql.append(" AND deal.close_date >= " + startDate);
            }

            Long endDate = (Long) filter.get("endDate");
            if (endDate != null) {
                sql.append(" AND deal.close_date < " + endDate);
            }

        }
        sql.append(" ORDER BY deal.btime DESC");
        return entityManager.createNativeQuery(sql.toString()).getResultList();
    }
}