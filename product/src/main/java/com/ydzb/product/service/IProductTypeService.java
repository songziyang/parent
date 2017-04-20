package com.ydzb.product.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.product.entity.ProductType;

/**
 * 产品类型service接口
 * @author sy
 */
public interface IProductTypeService extends IBaseService<ProductType, Long>{

    /**
     * 保存产品类别
     * @param productType
     * @return
     */
    public String saveOne(ProductType productType);
}
