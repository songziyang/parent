package com.ydzb.packet.repository;

import com.ydzb.packet.entity.SilverExchangeThirty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Repository
public class SilverExchangeThirtyRepository {

    @PersistenceContext
    private EntityManager manager;

    public List<Object[]> findExportData(Map<String, Object> filter) {

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT user.username, user.mobile AS u_mobile, silver.type, silver.status, silver.product_name, silver.realname," +
                " silver.mobile, silver.address, silver.remark, silver.created, silver.sended" +
                " FROM wm_silver_exchange_thirty AS silver" +
                " JOIN wm_user_users AS user ON silver.user_id = user.id" +
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

            Integer category = filter.get("category") == null? 1: (Integer) filter.get("category");
            if (category.equals(1)) {
                String type=(String)filter.get("type");
                if (StringUtils.isNotEmpty(type)) {
                    sql.append(" AND silver.type = "+new Integer(type));
                }
                Integer status = (Integer) filter.get("status");
                if (status == null) {
                    status = 1;
                }
                sql.append(" AND silver.status = " + status);
                sql.append(" AND (silver.level is null OR" +
                        " (silver.level >= " + SilverExchangeThirty.LEVEL_GOODSMIN + " AND silver.level <= " + SilverExchangeThirty.LEVEL_GOODSMAX + "))");
            } else if (category.equals(2)) {
                sql.append(" AND (silver.level < " + SilverExchangeThirty.LEVEL_GOODSMIN + " OR silver.level > " + SilverExchangeThirty.LEVEL_GOODSMAX + ")");
            }
        }
        sql.append(" ORDER BY silver.created DESC");

        Query query = manager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return new ArrayList<>();
        }
        return query.getResultList();
    }
}
