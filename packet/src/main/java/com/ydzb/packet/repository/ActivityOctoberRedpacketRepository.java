package com.ydzb.packet.repository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 金秋十月红包repository
 */
@Repository
public class ActivityOctoberRedpacketRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 查询导出excel数据
     * @param filter
     * @return
     */
    public List<Object[]> findExportData(Map<String, Object> filter) {

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT user.username, user.mobile, october.buy_fund, october.days," +
                " october.buy_type, october.redpacket_fund" +
                " FROM wm_activity_october_redpacket AS october" +
                " LEFT JOIN wm_user_users AS user" +
                " ON user.id = october.user_id" +
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

            String buyType = (String) filter.get("buyType");
            if (StringUtils.isNotEmpty(buyType)) {
                sql.append(" AND october.buy_type = " + buyType);
            }

            String redpacketFund = (String) filter.get("redpacketFund");
            if (StringUtils.isNotEmpty(redpacketFund)) {
                sql.append(" AND october.redpacket_fund = " + redpacketFund);
            }
        }
        sql.append(" ORDER BY october.created DESC");

        List<Object[]> resultList = entityManager.createNativeQuery(sql.toString()).getResultList();
        if (resultList == null || resultList.isEmpty()) {
            return new ArrayList<>();
        }
        return resultList;
    }
}