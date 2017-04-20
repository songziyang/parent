package com.ydzb.account.repository;

import com.ydzb.account.entity.WmPlatformPay;
import com.ydzb.account.entity.WmProductInfo;
import com.ydzb.account.entity.WmRagularUserAccount;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;

/**
 * 平台支出结算repository
 *
 * @author sy
 */
@Repository
public class WmPlatformPayRepository {

    @PersistenceContext
    private EntityManager manager;

    public void save(WmPlatformPay wmPlatformPay) {
        manager.persist(wmPlatformPay);
    }


    /**
     * 查询活期宝总收益
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public BigDecimal findCurrentAllFund(Long startDate, Long endDate) {
        String sql = " SELECT SUM(income) FROM wm_current_settlement WHERE account_date >= " + startDate + " AND account_date < " + endDate;
        Query query = manager.createNativeQuery(sql.toString());
        if (query != null) {
            Object obj = query.getSingleResult();
            if (obj != null) return (BigDecimal) obj;
        }
        return BigDecimal.ZERO;
    }

    /**
     * 查询定存宝总收益
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public BigDecimal findRegularAllFund(Long startDate, Long endDate) {
        String sql = " SELECT SUM(refund.fund) FROM wm_ragular_refund AS refund" +
                " JOIN wm_ragular_user_account AS account ON refund.account_id = account.id" +
                " JOIN wm_product_info AS product ON account.product_id = product.id" +
                " WHERE product.product_clas = " + WmProductInfo.YINDUO +   //类别是银多
                " AND product.type_id = " + WmProductInfo.TYPE_REGULAR +    //类型是定存
                " AND account.status <> " + WmRagularUserAccount.TRANSFER_SUCCESS + //去掉转让成功的记录，防止重复计算
                " AND refund.refund_time >= " + startDate +
                " AND refund.refund_time < " + endDate;
        Query query = manager.createNativeQuery(sql.toString());
        if (query != null) {
            Object obj = query.getSingleResult();
            if (obj != null) return (BigDecimal) obj;
        }
        return BigDecimal.ZERO;
    }

    /**
     * 查询机构宝总收益
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public BigDecimal findInstitutionAllFund(Long startDate, Long endDate) {
        String sql = " SELECT SUM(refund.fund) FROM wm_ragular_refund AS refund" +
                " JOIN wm_ragular_user_account AS account ON refund.account_id = account.id" +
                " JOIN wm_product_info AS product ON account.product_id = product.id" +
                " WHERE product.product_clas = " + WmProductInfo.INSTITUTION +   //类别是机构
                " AND account.status <> " + WmRagularUserAccount.TRANSFER_SUCCESS + //去掉转让成功的记录，防止重复计算
                " AND refund.refund_time >= " + startDate +
                " AND refund.refund_time < " + endDate;
        Query query = manager.createNativeQuery(sql.toString());
        if (query != null) {
            Object obj = query.getSingleResult();
            if (obj != null) return (BigDecimal) obj;
        }
        return BigDecimal.ZERO;
    }

    /**
     * 查询自主交易总收益
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public BigDecimal findPrivateOrderingAllFund(Long startDate, Long endDate) {
        String sql = " SELECT SUM(revenue) FROM wm_self_revenue" +
                " WHERE revenue > 0" +  //查询收大于0的
                " AND created >= " + startDate +
                " AND created < " + endDate;
        Query query = manager.createNativeQuery(sql.toString());
        if (query != null) {
            Object obj = query.getSingleResult();
            if (obj != null) return (BigDecimal) obj;
        }
        return BigDecimal.ZERO;
    }

    /**
     * 查询推荐收益
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public BigDecimal findRefereeAllFund(Long startDate, Long endDate) {
        String sql = " SELECT SUM(total_revenue) FROM wm_seven_days" +
                " WHERE created >= " + startDate +
                " AND created < " + endDate;
        Query query = manager.createNativeQuery(sql.toString());
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