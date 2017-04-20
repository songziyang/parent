package com.ydzb.account.repository;

import com.ydzb.account.entity.WmActivityEleven;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * 双十一活动repository
 */
@Repository
public class WmActivityDoubleElevenRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 查询投资榜前50的用户
     * 类别包括:
     * 定存宝(不是购买债权转让得)
     * 定存宝复投(8号凌晨也算)
     * 转转赚
     * 随心存
     *
     * @param normalStartTime 正常活动开始时间
     * @param rebuyStartTime  复投开始时间
     * @param endTime         活动结束时间
     * @return
     */
    public List<Object[]> queryTop50Users(Long normalStartTime, Long rebuyStartTime, Long endTime) {
        String sql = " SELECT invest.user_id, SUM(invest.buy_fund)" +
                " FROM" +
                "(" +
                " SELECT ragular.user_id AS user_id, ragular.buy_fund AS buy_fund, ragular.buy_time AS buy_time" +
                " FROM wm_ragular_user_account AS ragular" +
                " WHERE ragular.buy_time >= " + normalStartTime +
                " AND ragular.buy_time < " + endTime +
                " AND ragular. STATUS = 0" +
                " AND ragular.trans_count = 0" +
                " AND ragular.buy_type = 0" +
                " UNION ALL" +
                " SELECT rebuy_ragular.user_id AS user_id, rebuy_ragular.buy_fund AS buy_fund, rebuy_ragular.buy_time AS buy_time" +
                " FROM wm_ragular_user_account AS rebuy_ragular" +
                " WHERE rebuy_ragular.buy_time >= " + rebuyStartTime +
                " AND rebuy_ragular.buy_time < " + endTime +
                " AND rebuy_ragular.buy_type = 1" +
                " UNION ALL" +
                " SELECT free.user_id AS user_id, free.buy_fund AS buy_fund, free.buy_time AS buy_time" +
                " FROM wm_free_user_account AS free" +
                " WHERE free.buy_time >= " + normalStartTime +
                " AND free.buy_time < " + endTime +
                " UNION ALL" +
                " SELECT zhuan.user_id AS user_id, zhuan.copies AS buy_fund, zhuan.btime AS buy_time" +
                " FROM wm_structure_deal AS zhuan" +
                " WHERE zhuan.btime >= " + normalStartTime +
                " AND zhuan.btime < " + endTime +
                " AND zhuan.state = 0" +
                " UNION ALL" +
                " SELECT beauty.user_id AS user_id ,beauty.buy_fund AS buy_fund, beauty.buy_time AS buy_time " +
                " FROM wm_beauty_user_account AS beauty " +
                " WHERE beauty.buy_time >=  " + normalStartTime +
                " AND beauty.buy_time <  " + endTime +
                " AND beauty.status = 0" +
                " ) AS invest" +
                " GROUP BY invest.user_id" +
                " ORDER BY SUM(invest.buy_fund) DESC, MAX(invest.buy_time) ASC" +
                " LIMIT 0, 50";
        return entityManager.createNativeQuery(sql).getResultList();
    }


    public void saveWmActivityEleven(WmActivityEleven activityEleven) {
        entityManager.persist(activityEleven);
    }


}