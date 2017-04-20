package com.ydzb.product.repository;

import com.ydzb.core.utils.DateUtil;
import com.ydzb.product.entity.ProductSalesRecord;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 小额充值repository
 */
@Repository
public class ProductSalesRecordRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 查询导出excel数据
     *
     * @param filter
     * @return
     */
    public List<Object[]> findExportData(Map<String, Object> filter) {

        StringBuffer sql = new StringBuffer();

        sql.append(" select wpi.name , wpsr.release_time , wpsr.release_amount , wpsr.sales_amount , wpsr.status  , wpsr.sales_time , wpsr.created , wpi.surplus ,  wpi.type_id  from wm_product_sales_record as wpsr , wm_product_info as wpi where wpsr.product_id =  wpi.id  ");

        if (filter != null) {

            Integer status = (Integer) filter.get("status");
            if (status != null) {
                sql.append(" AND wpsr.status = " + status);
            }

            String startDate = (String) filter.get("startDate");
            if (StringUtils.isNotEmpty(startDate)) {
                sql.append(" AND wpsr.release_time >= " + DateUtil.getSystemTimeDay(startDate));
            }

            String endDate = (String) filter.get("endDate");
            if (StringUtils.isNotEmpty(endDate)) {
                sql.append(" AND wpsr.release_time < " + DateUtil.getSystemTimeDay(DateUtil.addDay(endDate)));
            }

        }

        sql.append(" ORDER BY wpsr.created DESC");

        List<Object[]> resultList = entityManager.createNativeQuery(sql.toString()).getResultList();

        if (resultList == null || resultList.isEmpty()) {
            return new ArrayList<>();
        }

        return resultList;
    }


    /**
     * 查询产品统计数据
     *
     * @param productId
     * @return
     */
    public ProductSalesRecord findWmProductSalesRecord(Long productId) {
        String sql = "select wp from ProductSalesRecord as wp where wp.productInfo.id = " + productId + " order by id desc ";
        TypedQuery<ProductSalesRecord> query = entityManager.createQuery(sql, ProductSalesRecord.class);
        if (query != null) {
            List<ProductSalesRecord> lst = query.getResultList();
            if (lst != null && !lst.isEmpty()) {
                return lst.get(0);
            }
        }
        return null;
    }


    /**
     * 保存产品销售统计数据
     *
     * @param productSalesRecord
     */
    public void saveWmProductSalesRecord(ProductSalesRecord productSalesRecord) {
        entityManager.persist(productSalesRecord);
    }

    /**
     * 更新产品销售统计数据
     *
     * @param productSalesRecord
     */
    public void updateWmProductSalesRecord(ProductSalesRecord productSalesRecord) {
        entityManager.merge(productSalesRecord);
    }


    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}