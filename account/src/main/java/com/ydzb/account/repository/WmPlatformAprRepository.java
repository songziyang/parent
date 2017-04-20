package com.ydzb.account.repository;

import com.ydzb.account.entity.WmPlatformApr;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Repository
public class WmPlatformAprRepository {

    @PersistenceContext
    private EntityManager manager;


    /**
     *
     * @param startTime
     * @return
     */
    public Object[] findDayloanApr(Long startTime) {
        String sql = "SELECT SUM(income - ROUND ((invest_fund * apr /36500),6)) as all_dayloan_income , SUM(found) as all_dayloan_fund ,ROUND(SUM(income - ROUND ((invest_fund * apr /36500),6))*36500/ SUM(found),2) as dayloan_apr  FROM wm_current_settlement where account_date >= " + startTime;
        Query query = manager.createNativeQuery(sql);
        if (query != null) {
            Object[] obj = (Object[]) query.getSingleResult();
            if (obj != null && obj.length > 0) {
                return obj;
            }
        }
        return null;
    }


    /**
     *
     * @param expireTime
     * @return
     */
    public Object[] findDepositApr(Long expireTime) {
        String sql = "SELECT  SUM(buy_fund) as all_deposit_fund , ROUND(SUM(interest_fund/days),6) as  all_deposit_income , ROUND(SUM(interest_fund/days) * 36500 /  SUM(buy_fund),2)as deposit_apr FROM  wm_ragular_user_account  where  status = 0 or expire_time = " + expireTime;
        Query query = manager.createNativeQuery(sql);
        if (query != null) {
            Object[] obj = (Object[]) query.getSingleResult();
            if (obj != null && obj.length > 0) {
                return obj;
            }
        }
        return null;
    }


    /**
     *
     * @param platformApr
     */
    public void saveWmPlatformApr(WmPlatformApr platformApr) {
        manager.persist(platformApr);
    }

    public EntityManager getManager() {
        return manager;
    }

    public void setManager(EntityManager manager) {
        this.manager = manager;
    }

}
