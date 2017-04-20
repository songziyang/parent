package com.ydzb.product.repository;

import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.product.entity.ProductType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 产品类别repository层
 * @author sy
 */
public interface IProductTypeRepository extends IBaseRepository<ProductType, Long> {

    /**
     * 通过类别名称查找产品类别
     * @param type
     * @return
     */
    @Query(" FROM ProductType WHERE type = :type ")
    public ProductType queryOne(@Param("type") String type);

}
