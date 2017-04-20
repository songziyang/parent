package com.ydzb.product.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 平台统计repository
 */
@Repository
public class PlatformStatisticsRepository {

    @PersistenceContext
    private EntityManager manager;

    /**
     * 查询导出excel数据
     * @param filters
     * @return
     */
    public List<Object[]> findExportData(Map<String, Object> filters) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT total_date, total_fund, total_revenue, total_dayloan, total_deposit, dayloan_apr," +
                " invest_num, invest_fund, platform_num, invest_persons, recharge_num, current_persons, ragular_persons,currents , ragulars , total_persons " +
                " FROM wm_platform_statistics" +
                " WHERE 1 = 1");
        if (filters != null) {
            Long totalStartTime = (Long) filters.get("totalStartTime");
            if (totalStartTime != null) {
                sql.append(" AND total_date >= " + totalStartTime);
            }

            Long totalEndTime = (Long) filters.get("totalEndTime");
            if (totalEndTime != null) {
                sql.append(" AND total_date < " + totalEndTime);
            }
        }

        sql.append(" ORDER BY created DESC");
        Query query = manager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return new ArrayList<>();
        }
        return query.getResultList();
    }
}