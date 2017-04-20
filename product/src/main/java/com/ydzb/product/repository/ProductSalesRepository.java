package com.ydzb.product.repository;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 产品售出repository
 */
@Repository
public class ProductSalesRepository {

    @PersistenceContext
    private EntityManager manager;

    /**
     * 查询导出excel数据
     * @param filter
     * @return
     */
    public List<Object[]> findExportData(Map<String, Object> filter) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT product.name, product.funds, product.surplus, sales.buy_copies," +
                " product.interest_rate, sales.buy_date" +
                " FROM vi_product_sales AS sales" +
                " JOIN wm_product_info AS product ON sales.product_id = product.id" +
                " JOIN wm_product_type AS type ON product.type_id = type.id" +
                " WHERE 1 = 1");
        if (filter != null) {
            Long typeId = (Long) filter.get("typeId");
            if (typeId != null && !"".equals(typeId)) {
                sql.append(" AND type.id = " + typeId);
            }

            Long startDate = (Long) filter.get("startDate");
            if (startDate != null) {
                sql.append(" AND sales.buy_date >= " + startDate);
            }

            Long endDate = (Long) filter.get("endDate");
            if (endDate != null) {
                sql.append(" AND sales.buy_date < " + endDate);
            }

            String name = (String) filter.get("name");
            if (StringUtils.isNotEmpty(name)) {
                sql.append(" AND product.name LIKE '%" + name + "%'");
            }
        }

        sql.append(" ORDER BY sales.buy_date DESC");
        Query query = manager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return new ArrayList<Object[]>();
        }
        return query.getResultList();
    }
}
