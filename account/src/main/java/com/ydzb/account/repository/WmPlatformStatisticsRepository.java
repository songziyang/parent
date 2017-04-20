package com.ydzb.account.repository;

import com.ydzb.account.entity.WmPlatformStatistics;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;


@Repository
public class WmPlatformStatisticsRepository {

    @PersistenceContext
    private EntityManager manager;

    /**
     * 保存
     *
     * @param platformStatistics
     */
    public void saveWmPlatformStatistics(WmPlatformStatistics platformStatistics) {
        manager.persist(platformStatistics);
    }

    /**
     * 根据统计时间查询
     *
     * @param totalDate
     * @return
     */
    public List<WmPlatformStatistics> findByTotalDate(final Long totalDate) {
        final String sql = " SELECT * FROM wm_platform_statistics WHERE total_date = " + totalDate;
        final Query query = manager.createNativeQuery(sql, WmPlatformStatistics.class);
        if (query != null) {
            List<WmPlatformStatistics> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return lst;
            }
        }
        return null;
    }


    /**
     * 查询活期购买人数
     *
     * @return
     */
    public BigInteger findCurrents() {
        final String sql = "select count(wuu.id) from wm_user_users as wuu , wm_user_investinfo as wui where wuu.id = wui.user_id and wuu.status <> -1 and wui.all_invest_dayloan >= 100";
        final Query query = manager.createNativeQuery(sql);
        if (query != null) {
            Object obj = query.getSingleResult();
            if (obj != null) {
                return (BigInteger) obj;
            }
        }
        return BigInteger.ZERO;
    }

    /**
     * 查询定存宝购买人数
     *
     * @return
     */
    public BigInteger findagulars() {
        final String sql = "select count(wuu.id) from wm_user_users as wuu , wm_user_investinfo as wui where wuu.id = wui.user_id and wuu.status <> -1 and wui.all_invest_deposit > 0";
        final Query query = manager.createNativeQuery(sql);
        if (query != null) {
            Object obj = query.getSingleResult();
            if (obj != null) {
                return (BigInteger) obj;
            }
        }
        return BigInteger.ZERO;
    }


    /**
     * 查询每日购买活期宝的人数
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public BigInteger findCurrentDailyBuyUsersCount(final Long startTime, final Long endTime) {
        String sql = " SELECT COUNT(DISTINCT user_id) FROM wm_current_traderecord" +
                " WHERE type = 1 AND fund_source <> 2";
        if (startTime != null) {
            sql += " AND buy_time >= " + startTime;
        }
        if (endTime != null) {
            sql += " AND buy_time < " + endTime;
        }
        Query query = manager.createNativeQuery(sql);
        if (query != null) {
            Object obj = query.getSingleResult();
            if (obj != null) {
                return (BigInteger) obj;
            }
        }
        return BigInteger.ZERO;
    }

    /**
     * 查询每日购买定存宝的人数
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public BigInteger findRagularDailyBuyUsersCount(final Long startTime, final Long endTime) {
        String sql = " select COUNT(DISTINCT user_id) from wm_ragular_user_account WHERE 1 = 1";
        if (startTime != null) {
            sql += " AND buy_time >= " + startTime;
        }
        if (endTime != null) {
            sql += " AND buy_time < " + endTime;
        }
        Query query = manager.createNativeQuery(sql);
        if (query == null) {
            return BigInteger.ZERO;
        }
        return (BigInteger) query.getSingleResult();
    }


    /**
     * 查询投资总人数
     *
     * @return
     */
    public BigInteger findZtjrs() {
        final String sql = "select count(wuu.id) from wm_user_users as wuu , wm_user_investinfo as wui where wuu.id = wui.user_id and wuu.status <> -1 and wui.all_invest  >= 100  ";
        final Query query = manager.createNativeQuery(sql);
        if (query != null) {
            Object obj = query.getSingleResult();
            if (obj != null) {
                return (BigInteger) obj;
            }
        }
        return BigInteger.ZERO;
    }


    public EntityManager getManager() {
        return manager;
    }

    public void setManager(EntityManager manager) {
        this.manager = manager;
    }

}
