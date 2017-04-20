package com.ydzb.product.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.product.entity.ProductInfo;

/**
 * 产品信息service接口
 * @author sy
 */
public interface IProductInfoService extends IBaseService<ProductInfo, Long> {

    /**
     * 清空活期宝剩余份数
     * @param productId
     * @return
     */
    public String emptyCurrent(Long productId);

    /**
     * 保存某个产品信息
     * @param productInfo
     * @return
     */
    public String saveOne(ProductInfo productInfo);

    /**
     * 查询
     * @return
     */
    public String queryProductName(Long productTypeId);
}
