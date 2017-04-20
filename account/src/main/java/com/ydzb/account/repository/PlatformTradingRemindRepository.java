package com.ydzb.account.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;


@Repository
public class PlatformTradingRemindRepository {

    @PersistenceContext
    private EntityManager manager;


    /**
     * 充值统计
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public BigDecimal findRechargeAllFund(Long startDate, Long endDate) {
        String sql = "SELECT sum(wur.money) as money  FROM  wm_user_recharge as wur  where  wur.status = 1 and wur.recharge_time  >=  " + startDate + " and wur.recharge_time <=  " + endDate;
        System.out.println(sql);
        Query query = manager.createNativeQuery(sql);
        if (query != null) {
            Object obj = query.getSingleResult();
            if (obj != null) return (BigDecimal) obj;
        }
        return BigDecimal.ZERO;
    }


    /**
     * 提现统计
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public BigDecimal findWithDrawAllFund(Long startDate, Long endDate) {
        String sql = "SELECT SUM(wuw.advance_money) as advance_money  FROM wm_user_withdraw as wuw where  wuw.status = 4  and  wuw.application_time  >=  " + startDate + " and wuw.application_time <=  " + endDate;
        System.out.println(sql);
        Query query = manager.createNativeQuery(sql);
        if (query != null) {
            Object obj = query.getSingleResult();
            if (obj != null) return (BigDecimal) obj;
        }
        return BigDecimal.ZERO;
    }


    public EntityManager getManager() {
        return manager;
    }

    public void setManager(EntityManager manager) {
        this.manager = manager;
    }

}
