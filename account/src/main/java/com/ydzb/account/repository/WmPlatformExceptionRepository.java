package com.ydzb.account.repository;

import com.ydzb.account.entity.WmPlatformException;
import com.ydzb.account.entity.WmUserFundBlokedLog;
import com.ydzb.account.entity.WmUserFundInLog;
import com.ydzb.account.entity.WmUserFundOutLog;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


@Repository
public class WmPlatformExceptionRepository {

    @PersistenceContext
    private EntityManager manager;


    /**
     * 查询进账异常记录
     *
     * @param type
     * @return
     */
    public List<WmUserFundInLog> findWmUserFundInLogByType(Integer type) {
        String sql = "SELECT * FROM  wm_user_fund_inlog  WHERE type  = " + type + " AND  receipts_time  > 1459094400 GROUP BY link_id ,fund  HAVING COUNT(id) > 1 ";
        Query query = manager.createNativeQuery(sql, WmUserFundInLog.class);
        if (query != null) {
            List<WmUserFundInLog> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return lst;
            }
        }
        return null;
    }


    /**
     * 查询出账异常记录
     *
     * @param type
     * @return
     */
    public List<WmUserFundOutLog> findWmUserFundOutLogByType(Integer type) {
        String sql = "SELECT * FROM wm_user_fund_outlog WHERE type  = " + type + " AND  out_time  > 1459094400 GROUP BY link_id ,out_fund HAVING COUNT(id) > 1 ";
        Query query = manager.createNativeQuery(sql, WmUserFundOutLog.class);
        if (query != null) {
            List<WmUserFundOutLog> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return lst;
            }
        }
        return null;
    }


    /**
     * 查询冻结异常记录
     *
     * @param type
     * @return
     */
    public List<WmUserFundBlokedLog> findWmUserFundBlokedLogByType(Integer type) {
        String sql = "SELECT * FROM wm_user_fund_blokedlog where type  = 1 AND  link_type = " + type + " AND operation_time > 1459094400 GROUP BY link_id ,fund   HAVING COUNT(id) > 1 ";
        Query query = manager.createNativeQuery(sql, WmUserFundBlokedLog.class);
        if (query != null) {
            List<WmUserFundBlokedLog> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return lst;
            }
        }
        return null;
    }


    /**
     * 查询该异常是否已经做记录
     *
     * @param userId
     * @param linkId
     * @param type
     * @return
     */
    public List<WmPlatformException> findWmPlatformException(Long userId, Long linkId, String type) {
        String sql = "SELECT * FROM wm_platform_exception where user_id = " + userId + " AND link_id = " + linkId + "  AND type ='" + type + "'";
        Query query = manager.createNativeQuery(sql, WmPlatformException.class);
        if (query != null) {
            List<WmPlatformException> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return lst;
            }
        }
        return null;
    }


    /**
     * 保存异常记录
     *
     * @param platformException
     */
    public void saveWmPlatformException(WmPlatformException platformException) {
        manager.persist(platformException);
    }


    public EntityManager getManager() {
        return manager;
    }

    public void setManager(EntityManager manager) {
        this.manager = manager;
    }

}
