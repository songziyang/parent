package com.ydzb.product.repository;

import com.ydzb.product.entity.TopRagularUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 定存宝用户交易repository
 *
 * @author
 */
@Repository
public class RagularUserAccountRepository {

    @PersistenceContext
    private EntityManager manager;

    /**
     * 根据定存产品类型数组(type)、到期状态(status)以及购买起始时间获得总购买金额
     *
     * @param type
     * @param status
     * @param startTime
     * @param endTime
     * @param mustExistTime 是否必须存在起始时间
     * @return
     */
    public BigDecimal getBuyFund(long[] type, Byte status, Long startTime, Long endTime, boolean mustExistTime) {
        if (mustExistTime && (startTime == null || startTime == 0L) && (endTime == null || endTime == 0L)) {
            return null;
        }
        return getBuyFund(type, status, startTime, endTime);
    }

    /**
     * 根据定存产品类型数组(type)、到期状态(status)以及购买起始时间获得总购买金额
     *
     * @param type
     * @param status
     * @param startTime
     * @param endTime
     * @param mustExistTime 是否必须存在起始时间
     * @return
     */
    public BigDecimal getBuyFund(long type, Byte status, Long startTime, Long endTime, boolean mustExistTime) {
        if (mustExistTime && (startTime == null || startTime == 0L) && (endTime == null || endTime == 0L)) {
            return null;
        }
        return getBuyFund(type, status, startTime, endTime);
    }

    /**
     * 根据定存产品类型数组(type)、到期状态(status)以及购买起始时间获得总购买金额
     *
     * @param type
     * @param status
     * @param startTime
     * @param endTime
     * @return
     */
    public BigDecimal getBuyFund(long[] type, Byte status, Long startTime, Long endTime) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT SUM(account.buy_fund) FROM wm_ragular_user_account AS account"
                + " JOIN wm_product_info AS product ON account.product_id = product.id"
                + " JOIN wm_product_type AS pType ON product.type_id = pType.id"
                + " WHERE 1 = 1");

        if (type != null && type.length > 0) {
            sql.append(" AND pType.id IN (");
            for (int i = 0; i < type.length; i++) {
                if (i == type.length - 1) {
                    sql.append(type[i]);
                } else {
                    sql.append(type[i] + ",");
                }
            }
            sql.append(")");
        }

        if (startTime != null && startTime > 0) {
            sql.append(" AND account.buy_time >= " + startTime);
        }

        if (endTime != null && endTime > 0) {
            sql.append(" AND account.buy_time <= " + endTime);
        }

        //如果状态是已到期或未到期
        if (status != null && (status == 0 || status == 1)) {
            sql.append(" AND account.status = " + status);
        }

        Query query = manager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return null;
        }
        return (BigDecimal) query.getSingleResult();
    }

    /**
     * 根据定存产品类型(type)、到期状态(status)以及购买起始时间获得总购买金额
     *
     * @param type
     * @param status
     * @param startTime
     * @param endTime
     * @return
     */
    public BigDecimal getBuyFund(Long type, Byte status, Long startTime, Long endTime) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT SUM(account.buy_fund) FROM wm_ragular_user_account AS account"
                + " JOIN wm_product_info AS product ON account.product_id = product.id"
                + " JOIN wm_product_type AS pType ON product.type_id = pType.id"
                + " WHERE 1 = 1");

        if (type != null) {
            sql.append(" AND pType.id = " + type);
        }

        if (startTime != null && startTime > 0) {
            sql.append(" AND account.buy_time >= " + startTime);
        }

        if (endTime != null && endTime > 0) {
            sql.append(" AND account.buy_time <= " + endTime);
        }

        //如果状态是已到期或未到期
        if (status != null && (status == 0 || status == 1)) {
            sql.append(" AND account.status = " + status);
        }

        Query query = manager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return null;
        }
        return (BigDecimal) query.getSingleResult();
    }

    /**
     * 查询某一个类型的定存交易记录累计购买金额
     *
     * @param type
     * @return
     */
    public BigDecimal findTotalBuyFund(long[] type) {
        if (type == null || type.length == 0) {
            return BigDecimal.ZERO;
        }
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT SUM(account.buy_fund) FROM wm_ragular_user_account AS account" +
                " JOIN wm_product_info AS product ON product.id = account.product_id" +
                " JOIN wm_product_type AS pType ON pType.id = product.type_id" +
                " WHERE pType.id IN (");
        for (int i = 0; i < type.length; i++) {
            if (i == type.length - 1) {
                sql.append(type[i]);
            } else {
                sql.append(type[i] + ", ");
            }
        }
        sql.append(")");
        Query query = manager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return BigDecimal.ZERO;
        }
        return (BigDecimal) query.getSingleResult();
    }

    /**
     * 查询某一个类型的定存交易记录累计购买金额
     *
     * @param type
     * @return
     */
    public BigDecimal findTotalBuyFund(Long type) {
        if (type == null) {
            return BigDecimal.ZERO;
        }
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT SUM(account.buy_fund) FROM wm_ragular_user_account AS account" +
                " JOIN wm_product_info AS product ON product.id = account.product_id" +
                " JOIN wm_product_type AS pType ON pType.id = product.type_id" +
                " WHERE pType.id = " + type);
        Query query = manager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return BigDecimal.ZERO;
        }
        return (BigDecimal) query.getSingleResult();
    }

    /**
     * 根据产品类型数组(type)查询总交易笔数
     *
     * @param type
     * @return
     */
    public BigInteger getTotalTradeNum(long[] type) {
        if (type == null || type.length == 0) {
            return BigInteger.ZERO;
        }
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT COUNT(account.id) FROM wm_ragular_user_account AS account" +
                " JOIN wm_product_info AS product ON product.id = account.product_id" +
                " JOIN wm_product_type AS pType ON pType.id = product.type_id " +
                " WHERE pType.id IN (");
        for (int i = 0; i < type.length; i++) {
            if (i == type.length - 1) {
                sql.append(type[i]);
            } else {
                sql.append(type[i] + ", ");
            }
        }
        sql.append(")");
        Query query = manager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return BigInteger.ZERO;
        }
        return (BigInteger) query.getSingleResult();
    }

    /**
     * 根据产品类型数组(type)查询总交易笔数
     *
     * @param type
     * @return
     */
    public BigInteger getTotalTradeNum(Long type) {
        if (type == null) {
            return BigInteger.ZERO;
        }
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT COUNT(account.id) FROM wm_ragular_user_account AS account" +
                " JOIN wm_product_info AS product ON product.id = account.product_id" +
                " JOIN wm_product_type AS pType ON pType.id = product.type_id " +
                " WHERE pType.id = " + type);
        Query query = manager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return BigInteger.ZERO;
        }
        return (BigInteger) query.getSingleResult();
    }

    /**
     * 根据定存类型(type)、到期状态(status)以及到期起始时间获得总到期(购买)金额
     *
     * @param type
     * @param status
     * @param startTime
     * @param endTime
     * @return
     */
    public BigDecimal getClosingFund(long[] type, Byte status, Long startTime, Long endTime) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT SUM(account.buy_fund) FROM wm_ragular_user_account AS account"
                + " JOIN wm_product_info AS product ON account.product_id = product.id"
                + " JOIN wm_product_type AS pType ON product.type_id = pType.id "
                + " WHERE 1 = 1");

        if (type != null && type.length > 0) {
            sql.append(" AND pType.id IN (");
            for (int i = 0; i < type.length; i++) {
                if (i == type.length - 1) {
                    sql.append(type[i]);
                } else {
                    sql.append(type[i] + ",");
                }
            }
            sql.append(")");
        }

        //如果状态是已到期或未到期
        if (status != null && (status == 0 || status == 1)) {
            sql.append(" AND account.status = " + status);
        }

        if (startTime != null && startTime > 0) {
            sql.append(" AND account.expire_time >= " + startTime);
        }

        if (endTime != null && endTime > 0) {
            sql.append(" AND account.expire_time <= " + endTime);
        }

        Query query = manager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return null;
        }
        return (BigDecimal) query.getSingleResult();
    }

    /**
     * 根据定存类型(type)、到期状态(status)以及到期起始时间获得总到期(购买)金额
     *
     * @param type
     * @param status
     * @param startTime
     * @param endTime
     * @return
     */
    public BigDecimal getClosingFund(Long type, Byte status, Long startTime, Long endTime) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT SUM(account.buy_fund) FROM wm_ragular_user_account AS account"
                + " JOIN wm_product_info AS product ON account.product_id = product.id"
                + " JOIN wm_product_type AS pType ON product.type_id = pType.id "
                + " WHERE 1 = 1");

        if (type != null) {
            sql.append(" AND pType.id = " + type);
        }

        //如果状态是已到期或未到期
        if (status != null && (status == 0 || status == 1)) {
            sql.append(" AND account.status = " + status);
        }

        if (startTime != null && startTime > 0) {
            sql.append(" AND account.expire_time >= " + startTime);
        }

        if (endTime != null && endTime > 0) {
            sql.append(" AND account.expire_time <= " + endTime);
        }

        Query query = manager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return null;
        }
        return (BigDecimal) query.getSingleResult();
    }

    /**
     * 根据定存类型(type)、到期状态(status)以及到期起始时间获得总到期(购买)金额
     *
     * @param type
     * @param status
     * @param startTime
     * @param endTime
     * @param mustExistTime 是否必须存在起始时间
     * @return
     */
    public BigDecimal getClosingFund(long[] type, Byte status, Long startTime, Long endTime, boolean mustExistTime) {
        if (mustExistTime && (startTime == null || startTime == 0L) && (endTime == null || endTime == 0L)) {
            return null;
        }
        return getClosingFund(type, status, startTime, endTime);
    }

    /**
     * 根据定存类型(type)、到期状态(status)以及到期起始时间获得总到期(购买)金额
     *
     * @param type
     * @param status
     * @param startTime
     * @param endTime
     * @param mustExistTime 是否必须存在起始时间
     * @return
     */
    public BigDecimal getClosingFund(Long type, Byte status, Long startTime, Long endTime, boolean mustExistTime) {
        if (mustExistTime && (startTime == null || startTime == 0L) && (endTime == null || endTime == 0L)) {
            return null;
        }
        return getClosingFund(type, status, startTime, endTime);
    }

    public List<Object[]> findExportData(Map<String, Object> filter) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT user.username, user.mobile, product.name, product.cylcle_days," +
                " account.buy_fund, account.apr, account.grand_apr, account.interest_fund," +
                " account.status, account.buy_time, account.expire_time, account.vip_apr," +
                " account.predict_income, account.expire_mode, account.buy_type" +
                " FROM wm_ragular_user_account AS account" +
                " LEFT JOIN wm_user_users AS user ON account.user_id = user.id" +
                " LEFT JOIN wm_product_info AS product ON product.id = account.product_id" +
                " WHERE 1 = 1");
        if (filter != null) {
            String username = (String) filter.get("username");
            if (StringUtils.isNotEmpty(username)) {
                sql.append(" AND user.username = '" + username + "'");
            }

            String mobile = (String) filter.get("mobile");
            if (StringUtils.isNotEmpty(mobile)) {
                sql.append(" AND user.mobile = '" + mobile + "'");
            }

            Integer status = (Integer) filter.get("status");
            if (status != null && status != -1) {
                sql.append(" AND account.status = " + status);
            }

            Long startDate = (Long) filter.get("startDate");
            if (startDate != null) {
                sql.append(" AND expire_time >= " + startDate);
            }

            Long endDate = (Long) filter.get("endDate");
            if (endDate != null) {
                sql.append(" AND expire_time < " + endDate);
            }

            Integer days = (Integer) filter.get("days");
            if (days != null) {
                sql.append(" AND product.cylcle_days = " + days);
            }

            Integer expireMode = (Integer) filter.get("expireMode");
            if (expireMode != null) {
                sql.append(" AND account.expire_mode = " + expireMode);
            }
        }
        sql.append(" ORDER BY account.expire_time ASC");
        Query query = manager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return new ArrayList<Object[]>();
        }
        return query.getResultList();
    }

    /**
     * 查询在指定时间段内，购买定存（不含债权转让）最多的Top N用户
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */

    public List<TopRagularUser> findTopRagularUser(Long startTime, Long endTime, Integer limit) {
        String sql = " SELECT user.id AS userId, user.real_name AS realName, user.username AS username, user.mobile AS mobile, user.remark AS remark," +
                " money.total_fund AS totalFund, money.usable_fund AS usableFund, SUM(ragular.buy_fund) AS buyFund, user.created AS created" +
                " FROM wm_user_users AS user" +
                " JOIN wm_ragular_user_account AS ragular ON ragular.user_id = user.id" +
                " JOIN wm_user_money AS money ON money.user_id = user.id" +
                " WHERE user.status = 0 AND ragular.status = 0 AND ragular.trans_count = 0" +
                " AND ragular.buy_time >= " + startTime +
                " AND ragular.buy_time < " + endTime +
                " GROUP BY user.id ORDER BY SUM(ragular.buy_fund) DESC LIMIT " + limit;


        Query query = manager.createNativeQuery(sql, TopRagularUser.class);
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return new ArrayList<TopRagularUser>();
        }
        return query.getResultList();
    }


    /**
     * @param currentDate
     * @return
     */
    public BigDecimal findEveryDayIncome(Long currentDate) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT  sum(interest_fund/days)  FROM wm_ragular_user_account  where    (status  = 0 or status  = 2) AND  expire_time > " + currentDate);
        Query query = manager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return BigDecimal.ZERO;
        }
        return (BigDecimal) query.getSingleResult();
    }


}
