package com.ydzb.product.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Repository
public class PlatformReconciliationRepository {

    @PersistenceContext
    private EntityManager manager;

    /**
     * 查询导出excel数据
     *
     * @param filters
     * @return
     */
    public List<Object[]> findExportData(Map<String, Object> filters) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT created, platform_fund, platform_income, third_fund , third_settlement , third_freeze , third_ensure  FROM wm_platform_reconciliation WHERE 1 = 1");
        if (filters != null) {
            Long totalStartTime = (Long) filters.get("totalStartTime");
            if (totalStartTime != null) {
                sql.append(" AND created >= " + totalStartTime);
            }

            Long totalEndTime = (Long) filters.get("totalEndTime");
            if (totalEndTime != null) {
                sql.append(" AND created < " + totalEndTime);
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