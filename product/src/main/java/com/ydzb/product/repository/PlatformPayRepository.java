package com.ydzb.product.repository;

import com.ydzb.core.entity.search.SearchOperator;
import com.ydzb.core.entity.search.filter.SearchFilterHelper;
import com.ydzb.core.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 平台支出repository
 */
@Repository
public class PlatformPayRepository {

    @PersistenceContext
    private EntityManager manager;

    /**
     * 查询导出excel数据
     * @param filters
     * @return
     */
    public List<Object[]> findExportData(Map<String, Object> filters) {

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT fund, type, opdate FROM wm_platform_pay WHERE 1 = 1");

        if (filters != null) {
            Byte type = (Byte) filters.get("type");
            if (type != null && type > 0) {
                sql.append(" AND type = " + type);
            }

            Long startTime = (Long) filters.get("startTime");
            if (startTime != null) {
                sql.append(" AND opdate >= " + startTime);
            }

            Long endTime = (Long) filters.get("endTime");
            if (endTime != null) {
                sql.append(" AND opdate < " + endTime);
            }
        }

        sql.append(" ORDER BY created DESC");
        Query query = manager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return new ArrayList<Object[]>();
        }
        return query.getResultList();
    }
}
