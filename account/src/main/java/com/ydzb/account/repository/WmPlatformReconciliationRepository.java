package com.ydzb.account.repository;

import com.ydzb.account.entity.WmPlatformBalanceRemind;
import com.ydzb.account.entity.WmPlatformReconciliation;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;


@Repository
public class WmPlatformReconciliationRepository {

    @PersistenceContext
    private EntityManager manager;


    /**
     * 保存对账信息
     *
     * @param wmPlatformReconciliation
     */
    public void saveWmPlatformReconciliation(WmPlatformReconciliation wmPlatformReconciliation) {
        manager.persist(wmPlatformReconciliation);
    }


    /**
     * 查询平台总资金
     *
     * @return
     */
    public BigDecimal findPlatformFund() {
        String sql = " SELECT SUM(total_fund)  FROM wm_user_money ";
        Query query = manager.createNativeQuery(sql.toString());
        if (query != null) {
            Object obj = query.getSingleResult();
            if (obj != null) return (BigDecimal) obj;
        }
        return BigDecimal.ZERO;
    }


    /**
     * 查询平台总收益
     *
     * @return
     */
    public BigDecimal findPlatformIncome() {
        String sql = " SELECT SUM(all_income)  FROM wm_user_income ";
        Query query = manager.createNativeQuery(sql.toString());
        if (query != null) {
            Object obj = query.getSingleResult();
            if (obj != null) return (BigDecimal) obj;
        }
        return BigDecimal.ZERO;
    }


    /**
     * 查询平台提醒记录
     *
     * @param type
     * @param opdate
     * @return
     */
    public WmPlatformBalanceRemind findWmPlatformBalanceRemind(Integer type, Long opdate) {
        String sql = "select wm from WmPlatformBalanceRemind as wm where wm.type = " + type + " and wm.opdate = " + opdate;
        TypedQuery<WmPlatformBalanceRemind> query = manager.createQuery(sql, WmPlatformBalanceRemind.class);
        if (query != null) {
            List<WmPlatformBalanceRemind> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return lst.get(0);
            }
        }
        return null;
    }


    /**
     * 保存提醒记录
     *
     * @param platformBalanceRemind
     */
    public void saveWmPlatformBalanceRemind(WmPlatformBalanceRemind platformBalanceRemind) {
        manager.persist(platformBalanceRemind);
    }

    public EntityManager getManager() {
        return manager;
    }

    public void setManager(EntityManager manager) {
        this.manager = manager;
    }

}
