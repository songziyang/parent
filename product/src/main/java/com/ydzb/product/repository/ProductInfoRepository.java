package com.ydzb.product.repository;

import com.ydzb.product.entity.ProductInfo;
import com.ydzb.product.entity.ProductType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 产品信息repository实体类
 * @author sy
 */
@Repository
public class ProductInfoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 根据产品类型id(id)、产品类别(productClas)以及天数(cylcleDays)获取含有最大期数的产品
     * @return
     */
    public ProductInfo queryProductHavingMaxStage(Long id, Byte productClas, Integer cylcleDays) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT * FROM wm_product_info" +
                " WHERE type_id = " + id +
                " AND product_clas = " + productClas +
                " AND cylcle_days = " + cylcleDays +
                " ORDER BY stage DESC " +
                " LIMIT 1");
        TypedQuery<ProductInfo> query = (TypedQuery<ProductInfo>) entityManager.createNativeQuery(sql.toString(),
                ProductInfo.class);
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return null;
        }
        return query.getSingleResult();
    }

    /**
     * @return
     */
    public Map<String, Object> querySales() {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT IFNULL(SUM(funds),0),  IFNULL(SUM(surplus),0),  IFNULL(cylcle_days,0) " +
                        " FROM wm_product_info" +
                        " GROUP BY cylcle_days");
        Query query = entityManager.createNativeQuery(sql.toString());
        if (query == null || query.getResultList() == null || query.getResultList().isEmpty()) {
            return new HashMap<String, Object>();
        }
        List<Object[]> data = query.getResultList();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        String key = "";
        for (Object[] objs: data) {
            int cDays = ((BigInteger) objs[2]).intValue();
            switch (cDays) {
                case 1:
                    key = "current";
                    break;
                case 30:
                    key = "regular_30";
                    break;
                case 90:
                    key = "regular_90";
                    break;
                case 180:
                    key = "regular_180";
                    break;
                case 360:
                case 365:
                    key = "regular_365";
                    break;
            }
            dataMap.put(key, (((BigDecimal)objs[0]).subtract((BigDecimal)objs[1])) + "/" + (BigDecimal)objs[0]);
        }
        return dataMap;
    }


}
