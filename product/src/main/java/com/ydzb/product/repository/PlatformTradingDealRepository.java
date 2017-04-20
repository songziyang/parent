package com.ydzb.product.repository;

import com.ydzb.core.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;

@Repository
public class PlatformTradingDealRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 根据平台交易记录类型、操作起始时间获得总金额
     * @param type
     * @param startDate
     * @param endDate
     * @return
     */
    public BigDecimal findSumFund(Byte type, String startDate, String endDate) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT SUM(deal.fund) FROM wm_platform_trading_deal AS deal" +
                " JOIN wm_platform_trading AS trading ON deal.trade_id = trading.id " +
                " WHERE 1 = 1");
        if (type != null) {
            sql.append(" AND trading.type = " + type);
        }

        if (StringUtils.isNotEmpty(startDate)) {
            sql.append(" AND deal.opdate >= " + DateUtil.getSystemTimeDay(startDate));
        }

        if (StringUtils.isNotEmpty(endDate)) {
            sql.append(" AND deal.opdate <= " + DateUtil.getSystemTimeDay(endDate));
        }

        Query query = entityManager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return BigDecimal.ZERO;
        }
        return (BigDecimal) query.getSingleResult();
    }
}