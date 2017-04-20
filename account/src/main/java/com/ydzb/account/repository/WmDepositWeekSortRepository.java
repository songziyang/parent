package com.ydzb.account.repository;


import com.ydzb.account.entity.WmDepositWeekSort;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * 结构化产品排行榜
 */
@Repository
public class WmDepositWeekSortRepository {

    @PersistenceContext
    private EntityManager manager;

    /**
     * 查询定存结构化产品排行榜
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    public List<Object[]> findDepositWeekSort(Long startDate, Long endDate) {
        //String sql = "SELECT sum(wufr.fund) as tfund  , wufr.user_id as user_id  FROM  wm_user_fund_record as wufr where wufr.fund_type   = 6 and wufr.type   =  0  and  wufr.record_time >= " + startDate + " and  wufr.record_time < " + endDate + " and locate('活期宝',wufr.fundflow) = 0 group by wufr.user_id order by tfund desc, MAX(wufr.record_time)  asc ";

        String sql = "  SELECT invest.user_id, SUM(invest.buy_fund) as fund FROM( " +
                "       SELECT ragular.user_id AS user_id, ragular.buy_fund AS buy_fund, ragular.buy_time AS buy_time " +
                "       FROM wm_ragular_user_account AS ragular " +
                "       WHERE  ragular.status <> 3 AND  ragular.buy_time >= " + startDate +
                "       AND ragular.buy_time <  " + endDate +
                "       UNION ALL SELECT free.user_id AS user_id, free.buy_fund AS buy_fund, free.buy_time AS buy_time " +
                "       FROM wm_free_user_account AS free " +
                "       WHERE free.buy_time >= " + startDate + " AND free.buy_time <  " + endDate +
                "       UNION ALL SELECT zhuan.user_id AS user_id, zhuan.copies AS buy_fund, zhuan.btime AS buy_time " +
                "       FROM wm_structure_deal AS zhuan " +
                "       WHERE zhuan.btime >= " + startDate + " AND zhuan.btime <   " + endDate +
                "       UNION ALL SELECT beauty.user_id AS user_id ,beauty.buy_fund AS buy_fund, beauty.buy_time AS buy_time " +
                "       FROM wm_beauty_user_account AS beauty " +
                "       WHERE beauty.buy_time >= " + startDate + " AND beauty.buy_time < " + endDate + "  ) AS invest " +
                "       GROUP BY invest.user_id ORDER BY SUM(invest.buy_fund) DESC, MAX(invest.buy_time) ASC ";

        System.out.println(sql);

        Query query = manager.createNativeQuery(sql);
        if (query != null) {
            return query.getResultList();
        }
        return new ArrayList<>();
    }


    /**
     * 保存
     *
     * @param depositWeekSort
     */
    public void saveWmDepositWeekSort(WmDepositWeekSort depositWeekSort) {
        manager.persist(depositWeekSort);
    }

    public EntityManager getManager() {
        return manager;
    }

    public void setManager(EntityManager manager) {
        this.manager = manager;
    }

}
