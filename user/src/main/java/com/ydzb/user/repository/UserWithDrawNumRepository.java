package com.ydzb.user.repository;

import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class UserWithDrawNumRepository {

    @PersistenceContext
    private EntityManager manager;

    public List<Object[]> findExportData(Map<String, Object> filter) {

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT user.username, user.mobile, user.real_name, withdrawNum.count_num," +
                " withdrawNum.withdraw_time, withdrawNum.recharge_num, withdrawNum.recharge_time " +
                " FROM vi_user_withdraw_num AS withdrawNum" +
                " JOIN wm_user_users AS user ON withdrawNum.user_id = user.id" +
                " WHERE 1= 1");

        if (filter != null) {
            String username = (String) filter.get("username");
            if (StringUtils.isNotEmpty(username)) {
                sql.append(" AND user.username = '" + username + "'");
            }

            String mobile = (String) filter.get("mobile");
            if (StringUtils.isNotEmpty(mobile)) {
                sql.append(" AND user.mobile = '" + mobile + "'");
            }

            Integer startWithDrawNum = (Integer) filter.get("startWithDrawNum");
            Integer endWithDrawNum = (Integer) filter.get("endWithDrawNum");
            if (startWithDrawNum != null && endWithDrawNum != null) {
                sql.append(" AND withdrawNum.count_num >= " + startWithDrawNum);
                sql.append(" AND withdrawNum.count_num <= " + endWithDrawNum);
            }

            Integer startRechargeNum = (Integer) filter.get("startRechargeNum");
            Integer endRechargeNum = (Integer) filter.get("endRechargeNum");
            if (startRechargeNum != null && endRechargeNum != null) {
                sql.append(" AND withdrawNum.recharge_num >= " + startRechargeNum);
                sql.append(" AND withdrawNum.recharge_num <= " + endRechargeNum);
            }

            Integer redname = (Integer) filter.get("redname");
            if (redname != null) {

                if (redname == 1) {
                    sql.append(" AND user.remark IS NOT NULL");
                }

                if (redname == 2) {
                    sql.append(" AND user.remark IS NULL");
                }
            }

            String orderSort = (String) filter.get("orderSort");
            Integer orderNumber = (Integer) filter.get("orderNumber");


            if (orderNumber != null) {
                switch (orderNumber) {
                    case 1:
                        if ("desc".equals(orderSort)) {
                            sql.append(" ORDER BY withdrawNum.count_num DESC");
                        } else {
                            sql.append(" ORDER BY withdrawNum.count_num ASC");
                        }
                        break;
                    case 2:
                        if ("desc".equals(orderSort)) {
                            sql.append(" ORDER BY withdrawNum.recharge_num DESC");
                        } else {
                            sql.append(" ORDER BY withdrawNum.recharge_num ASC");
                        }
                        break;
                    case 3:
                        if ("desc".equals(orderSort)) {
                            sql.append(" ORDER BY withdrawNum.withdraw_time DESC");
                        }  else {
                            sql.append(" ORDER BY withdrawNum.withdraw_time ASC");
                        }
                        break;
                    case 4:
                        if ("desc".equals(orderSort)) {
                            sql.append(" ORDER BY withdrawNum.recharge_time DESC");
                        }  else {
                            sql.append(" ORDER BY withdrawNum.recharge_time ASC");
                        }
                        break;
                }
            } else {
                sql.append(" ORDER BY withdrawNum.created DESC");
            }
        } else {
            sql.append(" ORDER BY withdrawNum.created DESC");
        }

        Query query = manager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return new ArrayList<Object[]>();
        }
        return query.getResultList();
    }
}
