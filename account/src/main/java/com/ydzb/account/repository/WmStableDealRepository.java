package com.ydzb.account.repository;

import com.ydzb.account.entity.WmStableDeal;
import com.ydzb.account.entity.WmUserMoney;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * 基金产品详情repository
 */
@Repository
public class WmStableDealRepository {

    @PersistenceContext
    private EntityManager entityManager;


    /**
     * 保存
     *
     * @param stableDeal
     */
    public void update(WmStableDeal stableDeal) {
        entityManager.merge(stableDeal);
    }

    /**
     * 查询用户到期基金类产品
     *
     * @param userId   用户ID
     * @param stableId 基金产品id
     * @return
     */
    public List<WmStableDeal> findWmStableDeal(Long userId, Long stableId) {
        String sql = " SELECT * FROM wm_stable_deal WHERE user_id = " + userId + " AND stable_id = " + stableId + " AND state = 0 ";
        Query query = entityManager.createNativeQuery(sql, WmStableDeal.class);
        if (query != null) {
            List<WmStableDeal> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return lst;
            }
        }
        return null;
    }

}