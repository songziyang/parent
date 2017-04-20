package com.ydzb.account.repository;

import com.ydzb.account.entity.WmCurrentSettlement;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * 活期宝结算记录repository
 */
@Repository
public class WmCurrentSettlementRepository extends WmBaseRepository {

    /**
     * 根据用户id以及结算时间段查询
     *
     * @param userId    用户id
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    public WmCurrentSettlement findByUserAndTime(Long userId, Long startTime, Long endTime) {
        String sql = " SELECT * FROM wm_current_settlement WHERE user_id = " + userId +
                " AND account_date >= " + startTime + " AND account_date < " + endTime + " ORDER  BY id desc ";
        Query query = entityManager.createNativeQuery(sql, WmCurrentSettlement.class);
        if (query != null) {
            List<WmCurrentSettlement> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return lst.get(0);
            }
        }
        return null;
    }
}