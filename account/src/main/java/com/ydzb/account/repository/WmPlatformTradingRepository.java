package com.ydzb.account.repository;

import com.ydzb.account.entity.WmPlatformTrading;
import com.ydzb.account.entity.WmPlatformTradingDeal;
import com.ydzb.account.entity.WmProductInfo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;


@Repository
public class WmPlatformTradingRepository {

    @PersistenceContext
    private EntityManager manager;


    /**
     * 保存交易统计
     *
     * @param platformTrading
     */
    public Long saveWmPlatformTrading(WmPlatformTrading platformTrading) {
        manager.persist(platformTrading);
        return platformTrading.getId();
    }

    /**
     * 保存平台交易统计详细
     *
     * @param platformTradingDeal
     */
    public void saveWmPlatformTradingDeal(WmPlatformTradingDeal platformTradingDeal) {
        manager.persist(platformTradingDeal);
    }

    /**
     * 充值统计
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public BigDecimal findRechargeAllFund(Long startDate, Long endDate) {
        String sql = "select sum(wufr.fund) from wm_user_fund_record as wufr  where wufr.type = 1 and wufr.fund_type = 0 and wufr.record_time >=  " + startDate + " and wufr.record_time < " + endDate;
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
        String sql = "select sum(wufr.fund) from wm_user_fund_record as wufr  where wufr.type = 0 and wufr.fund_type = 1 and wufr.record_time >=  " + startDate + " and wufr.record_time < " + endDate;
        Query query = manager.createNativeQuery(sql);
        if (query != null) {
            Object obj = query.getSingleResult();
            if (obj != null) return (BigDecimal) obj;
        }
        return BigDecimal.ZERO;
    }


    /**
     * 活期宝之和
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public BigDecimal findCurrentAllFund(Long startDate, Long endDate) {
        String sql = "select sum(wpi.funds - wpi.surplus) from wm_product_info as wpi where wpi.product_clas = 1 and wpi.type_id = 1 and wpi.created >=  " + startDate + " and wpi.created < " + endDate;
        Query query = manager.createNativeQuery(sql);
        if (query != null) {
            Object obj = query.getSingleResult();
            if (obj != null) return (BigDecimal) obj;
        }
        return BigDecimal.ZERO;
    }


    /**
     * 查询活期宝产品
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public List<WmProductInfo> findCurrentWmProductInfo(Long startDate, Long endDate) {
        String sql = "select wp from WmProductInfo as wp where wp.type = 1 and wp.productClas = 1 and  wp.created >=  " + startDate + " and wp.created < " + endDate;
        TypedQuery<WmProductInfo> query = manager.createQuery(sql, WmProductInfo.class);
        if (query != null) {
            return query.getResultList();
        }
        return null;
    }

    /**
     * 定存宝之和
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public BigDecimal findRagularAllFund(Long startDate, Long endDate) {
        //String sql = "select sum(wpi.funds - wpi.surplus) from wm_product_info as wpi where wpi.product_clas = 1 and wpi.type_id = 2 and wpi.created >=  " + startDate + " and wpi.created < " + endDate;
        String sql = " SELECT SUM(account.buy_fund) FROM wm_ragular_user_account AS account" +
                " JOIN wm_product_info AS product ON account.product_id = product.id" +
                " WHERE product.type_id = 2" +  //定存产品
                " AND product.product_clas = 1" + //银多类别
                " AND account.buy_time >= " + startDate +
                " AND account.buy_time < " + endDate;
        Query query = manager.createNativeQuery(sql);
        if (query != null) {
            Object obj = query.getSingleResult();
            if (obj != null) return (BigDecimal) obj;
        }
        return BigDecimal.ZERO;
    }

    /**
     * 随心存之和
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public BigDecimal findFreeAllFund(Long startDate, Long endDate) {
        //String sql = "select sum(wpi.funds - wpi.surplus) from wm_product_info as wpi where wpi.product_clas = 1 and wpi.type_id = 2 and wpi.created >=  " + startDate + " and wpi.created < " + endDate;
        String sql = " SELECT SUM(account.buy_fund) FROM wm_free_user_account AS account" +
                " JOIN wm_product_info AS product ON account.product_id = product.id" +
                " WHERE product.type_id = 8" +  //随心存产品
                " AND product.product_clas = 1" + //银多类别
                " AND account.buy_time >= " + startDate +
                " AND account.buy_time < " + endDate;
        Query query = manager.createNativeQuery(sql);
        if (query != null) {
            Object obj = query.getSingleResult();
            if (obj != null) return (BigDecimal) obj;
        }
        return BigDecimal.ZERO;
    }


    /**
     * 查询定存宝产品
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public List<Object[]> findRagularWmProductInfo(Long startDate, Long endDate) {
        //String sql = "select wp from WmProductInfo as wp where wp.type = 2 and wp.productClas = 1 and  wp.created >=  " + startDate + " and wp.created < " + endDate;
        String sql = "SELECT SUM(account.buy_fund), product.name FROM wm_ragular_user_account AS account" +
                " JOIN wm_product_info AS product ON account.product_id = product.id" +
                " WHERE product.type_id = 2" +  //定存产品
                " AND product.product_clas = 1" + //银多类别
                " AND account.buy_time >= " + startDate +
                " AND account.buy_time < " + endDate +
                " GROUP BY product.type_id, product.cylcle_days";
        Query query = manager.createNativeQuery(sql);
        if (query != null) {
            return query.getResultList();
        }
        return null;
    }

    /**
     * 查询定存宝产品
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public List<Object[]> findFreeWmProductInfo(Long startDate, Long endDate) {
        //String sql = "select wp from WmProductInfo as wp where wp.type = 2 and wp.productClas = 1 and  wp.created >=  " + startDate + " and wp.created < " + endDate;
        String sql = "SELECT SUM(account.buy_fund), product.name, account.days FROM wm_free_user_account AS account" +
                " JOIN wm_product_info AS product ON account.product_id = product.id" +
                " WHERE product.type_id = 8" +  //随心存产品
                " AND product.product_clas = 1" + //银多类别
                " AND account.buy_time >= " + startDate +
                " AND account.buy_time < " + endDate +
                " GROUP BY product.type_id, account.days";
        Query query = manager.createNativeQuery(sql);
        if (query != null) {
            return query.getResultList();
        }
        return null;
    }


    /**
     * 机构宝之和
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public BigDecimal findInstitutionAllFund(Long startDate, Long endDate) {
        //String sql = "select sum(wpi.funds - wpi.surplus) from wm_product_info as wpi where wpi.product_clas = 2 and wpi.type_id = 3 and wpi.created >=  " + startDate + " and wpi.created < " + endDate;
        String sql = " SELECT SUM(account.buy_fund) FROM wm_ragular_user_account AS account" +
                " JOIN wm_product_info AS product ON account.product_id = product.id" +
                " WHERE product.product_clas = 2" + //机构类别
                " AND account.buy_time >= " + startDate +
                " AND account.buy_time < " + endDate;
        Query query = manager.createNativeQuery(sql);
        if (query != null) {
            Object obj = query.getSingleResult();
            if (obj != null) return (BigDecimal) obj;
        }
        return BigDecimal.ZERO;
    }


    /**
     * 查询机构宝产品
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public List<Object[]> findInstitutionWmProductInfo(Long startDate, Long endDate) {
        //String sql = "select wp from WmProductInfo as wp where wp.type = 2 and wp.productClas = 1 and  wp.created >=  " + startDate + " and wp.created < " + endDate;
        String sql = "SELECT SUM(account.buy_fund), product.name FROM wm_ragular_user_account AS account" +
                " JOIN wm_product_info AS product ON account.product_id = product.id" +
                " WHERE product.product_clas = 2" + //机构类别
                " AND account.buy_time >= " + startDate +
                " AND account.buy_time < " + endDate +
                " GROUP BY product.type_id, product.cylcle_days";
        Query query = manager.createNativeQuery(sql);
        if (query != null) {
            return query.getResultList();
        }
        return null;
    }


    /**
     * 自主交易之和
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public BigDecimal findSelfAllFund(Long startDate, Long endDate) {
        String sql = "select sum(wst.trade_money) from wm_self_tradelog as wst where wst.confirm_time >=  " + startDate + " and wst.confirm_time < " + endDate;
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
