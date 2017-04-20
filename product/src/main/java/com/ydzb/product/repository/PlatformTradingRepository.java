package com.ydzb.product.repository;

import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import com.ydzb.core.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 平台交易repository
 */
@Repository
public class PlatformTradingRepository {

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
        sql.append(" SELECT SUM(fund) FROM wm_platform_trading" +
                " WHERE 1 = 1");
        if (type != null) {
            sql.append(" AND type = " + type);
        }

        if (StringUtils.isNotEmpty(startDate)) {
            sql.append(" AND opdate >= " + DateUtil.getSystemTimeDay(startDate));
        }

        if (StringUtils.isNotEmpty(endDate)) {
            sql.append(" AND opdate <= " + DateUtil.getSystemTimeDay(DateUtil.addDay(endDate)));
        }

        Query query = entityManager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return BigDecimal.ZERO;
        }
        return (BigDecimal) query.getSingleResult();
    }

    /**
     * 查询导出excel数据
     * @param filter
     * @return
     */
    public List<Object[]> findExportData(Map<String, Object> filter) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT fund, type, opdate FROM wm_platform_trading WHERE 1 = 1");

        if (filter != null) {
            Byte type = (Byte) filter.get("type");
            if (type != null && type > 0) {
                sql.append(" AND type = " + type);
            }

            Long startDate = (Long) filter.get("startDate");
            if (startDate != null) {
                sql.append(" AND opdate >= " + startDate);
            }

            Long endDate = (Long) filter.get("endDate");
            if (endDate != null) {
                sql.append(" AND opdate < " + endDate);
            }

        }
        sql.append(" ORDER BY opdate DESC");
        Query query = entityManager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return new ArrayList<Object[]>();
        }
        return query.getResultList();
    }
}