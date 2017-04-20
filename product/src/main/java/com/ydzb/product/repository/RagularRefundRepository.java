package com.ydzb.product.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;

/**
 * 定存还息记录repository
 * @author sy
 */
@Repository
public class RagularRefundRepository {

    @PersistenceContext
    private EntityManager manager;

    /**
     * 根据还款状态(state)以及对应产品的类型(type)计算收益
     * @param type
     * @param state
     * @return
     */
    public BigDecimal getTotalRevenue(long[] type, byte state) {
        if (type == null || type.length < 0) {
            return null;
        }
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT SUM(refund.fund) FROM wm_ragular_refund AS refund"
                + " JOIN wm_ragular_user_account AS account ON refund.account_id = account.id"
                + " JOIN wm_product_info AS product ON product.id = account.product_id"
                + " JOIN wm_product_type AS pType ON pType.id = product.type_id"
                + " WHERE refund.state = " + state
                + " AND pType.id IN(");
        for(int i = 0; i < type.length; i ++) {
            if (i == type.length - 1) {
                sql.append(type[i]);
            } else {
                sql.append(type[i] + ", ");
            }
        }
        sql.append(")");
        Query query = manager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return null;
        }
        return (BigDecimal) query.getSingleResult();
    }

    /**
     * 根据还款状态(state)以及对应产品的类型(type)计算收益
     * @param type
     * @param state
     * @return
     */
    public BigDecimal getTotalRevenue(Long type, byte state) {
        if (type == null) {
            return null;
        }
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT SUM(refund.fund) FROM wm_ragular_refund AS refund"
                + " JOIN wm_ragular_user_account AS account ON refund.account_id = account.id"
                + " JOIN wm_product_info AS product ON product.id = account.product_id"
                + " JOIN wm_product_type AS pType ON pType.id = product.type_id"
                + " WHERE refund.state = " + state
                + " AND pType.id = " + type);
        Query query = manager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return null;
        }
        return (BigDecimal) query.getSingleResult();
    }

    /**
     * 根据定存产品类型(type)、还息状态(state)、结算起始日期查询到期收益
     * @param type
     * @param state
     * @param startTime
     * @param endTime
     * @param mustExistTime 是否必须存在
     * @return
     */
    public BigDecimal getClosingRevenue(long type[], Byte state, Long startTime, Long endTime, boolean mustExistTime) {
        if (mustExistTime && (startTime == null || startTime == 0L) && (endTime == null || endTime == 0L)) {
            return null;
        }
        return getClosingRevenue(type, state, startTime, endTime);
    }

    /**
     * 根据定存产品类型(type)、还息状态(state)、结算起始日期查询到期收益
     * @param type
     * @param state
     * @param startTime
     * @param endTime
     * @param mustExistTime 是否必须存在
     * @return
     */
    public BigDecimal getClosingRevenue(Long type, Byte state, Long startTime, Long endTime, boolean mustExistTime) {
        if (mustExistTime && (startTime == null || startTime == 0L) && (endTime == null || endTime == 0L)) {
            return null;
        }
        return getClosingRevenue(type, state, startTime, endTime);
    }

    /**
     * 根据定存产品类型(type)、结算状态(state)、结算起始日期查询到期收益
     * @param type
     * @param state
     * @param startTime
     * @param endTime
     * @return
     */
    public BigDecimal getClosingRevenue(long type[], Byte state, Long startTime, Long endTime) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT SUM(refund.fund) FROM wm_ragular_refund AS refund"
                + " JOIN wm_ragular_user_account AS account ON refund.account_id = refund.id"
                + " JOIN wm_product_info AS product ON account.product_id = product.id"
                + " JOIN wm_product_type AS pType ON product.type_id = pType.id"
                + " WHERE 1 = 1");

        if (type != null && type.length > 0) {
            sql.append(" AND pType.id IN (");
            for (int i = 0 ; i < type.length; i ++) {
                if (i == type.length - 1) {
                    sql.append(type[i]);
                } else {
                    sql.append(type[i] + ",");
                }
            }
            sql.append(")");
        }

        //如果状态是已结算或未结算
        if (state != null && (state == 0 || state == 1)) {
            sql.append(" AND refund.state = " + state);
        }

        if (startTime != null && startTime > 0) {
            sql.append(" AND refund.refund_time >= " + startTime);
        }

        if (endTime != null && endTime > 0) {
            sql.append(" AND refund.refund_time <= " + endTime);
        }

        Query query = manager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return null;
        }
        return (BigDecimal)query.getSingleResult();
    }

    /**
     * 根据定存产品类型(type)、结算状态(state)、结算起始日期查询到期收益
     * @param type
     * @param state
     * @param startTime
     * @param endTime
     * @return
     */
    public BigDecimal getClosingRevenue(Long type, Byte state, Long startTime, Long endTime) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT SUM(refund.fund) FROM wm_ragular_refund AS refund"
                + " JOIN wm_ragular_user_account AS account ON refund.account_id = refund.id"
                + " JOIN wm_product_info AS product ON account.product_id = product.id"
                + " JOIN wm_product_type AS pType ON product.type_id = pType.id"
                + " WHERE 1 = 1");

        if (type != null) {
            sql.append(" AND pType.id = " + type);
        }

        //如果状态是已结算或未结算
        if (state != null && (state == 0 || state == 1)) {
            sql.append(" AND refund.state = " + state);
        }

        if (startTime != null && startTime > 0) {
            sql.append(" AND refund.refund_time >= " + startTime);
        }

        if (endTime != null && endTime > 0) {
            sql.append(" AND refund.refund_time <= " + endTime);
        }

        Query query = manager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return null;
        }
        return (BigDecimal)query.getSingleResult();
    }
}