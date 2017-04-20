package com.ydzb.product.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;


@Repository
public class DepositTypeExpireRepository {

    @PersistenceContext
    private EntityManager entityManager;


    /**
     * 定存宝
     *
     * @param expireTime 到期日期
     * @return
     */
    public BigDecimal findRagularByExpireTime(Long expireTime) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT IFNULL(SUM(buy_fund),0) FROM  wm_ragular_user_account  where ( status = 0 or status = 2 ) and  expire_time = ");
        sql.append(expireTime);
        Query query = entityManager.createNativeQuery(sql.toString());
        if (query != null) {
            List<Object> data = query.getResultList();
            if (data != null && !data.isEmpty() && data.get(0) != null) {
                return (BigDecimal) data.get(0);
            }
        }
        return BigDecimal.ZERO;
    }


    /**
     * 随心存
     *
     * @param expireTime 到期日期
     * @return
     */
    public BigDecimal findFreeByExpireTime(Long expireTime) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT IFNULL(SUM(buy_fund),0)  FROM  wm_free_user_account   where ( status = 0 or status = 2 ) and  expire_time =  ");
        sql.append(expireTime);
        Query query = entityManager.createNativeQuery(sql.toString());
        if (query != null) {
            List<Object> data = query.getResultList();
            if (data != null && !data.isEmpty() && data.get(0) != null) {
                return (BigDecimal) data.get(0);
            }
        }
        return BigDecimal.ZERO;
    }


    /**
     * 美利金融
     *
     * @param expireTime 到期日期
     * @return
     */
    public BigDecimal findBeautyByExpireTime(Long expireTime) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT IFNULL(SUM(buy_fund),0)  FROM  wm_beauty_user_account    where ( status = 0 or status = 2 ) and product_id  = 99999  and  expire_time = ");
        sql.append(expireTime);
        Query query = entityManager.createNativeQuery(sql.toString());
        if (query != null) {
            List<Object> data = query.getResultList();
            if (data != null && !data.isEmpty() && data.get(0) != null) {
                return (BigDecimal) data.get(0);
            }
        }
        return BigDecimal.ZERO;
    }


    /**
     * 买单侠
     *
     * @param expireTime 到期日期
     * @return
     */
    public BigDecimal findMdxByExpireTime(Long expireTime) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT IFNULL(SUM(buy_fund),0)  FROM  wm_beauty_user_account    where ( status = 0 or status = 2 ) and product_id  <> 99999  and  expire_time = ");
        sql.append(expireTime);
        Query query = entityManager.createNativeQuery(sql.toString());
        if (query != null) {
            List<Object> data = query.getResultList();
            if (data != null && !data.isEmpty() && data.get(0) != null) {
                return (BigDecimal) data.get(0);
            }
        }
        return BigDecimal.ZERO;
    }


    /**
     * 转转赚
     *
     * @param expireTime 到期日期
     * @return
     */
    public BigDecimal findStructureByExpireTime(Long expireTime) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT IFNULL(SUM(copies),0)  FROM wm_structure_deal  where  state = 0 and  close_date  =  ");
        sql.append(expireTime);
        Query query = entityManager.createNativeQuery(sql.toString());
        if (query != null) {
            List<Object> data = query.getResultList();
            if (data != null && !data.isEmpty() && data.get(0) != null) {
                return (BigDecimal) data.get(0);
            }
        }
        return BigDecimal.ZERO;
    }


    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}