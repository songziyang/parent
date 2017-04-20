package com.ydzb.product.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.product.entity.ProductType;
import com.ydzb.product.repository.IProductTypeRepository;
import com.ydzb.product.service.IProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 产品类别service实现类
 * @author sy
 */
@Service
public class ProductTypeServiceImpl extends BaseServiceImpl<ProductType, Long> implements IProductTypeService {

    @Autowired
    private IProductTypeRepository productTypeRepository;

    /**
     * 保存产品类别
     * @param productType
     * @return
     */
    @Override
    public String saveOne(ProductType productType) {
        if (productType == null) {
            return null;
        }
        Long typeId = productType.getId();
        String typeName = productType.getType();
        //如果是新建
        if (typeId == null) {
            ProductType existType = productTypeRepository.queryOne(typeName);
            if (existType != null) {
                return "产品类别已存在！";
            } else {
                productTypeRepository.save(productType);
            }
        }
        //如果是修改
        else {
            ProductType sourceType = productTypeRepository.findOne(productType.getId());
            String sourceTypeName = sourceType.getType();
            //如果类别名称更改，则在数据库查询改名称类别是否存在，如存在则提示错误，否则进行保存
            if (sourceTypeName != null && !sourceTypeName.equals(typeName)) {
                ProductType existType = productTypeRepository.queryOne(productType.getType());
                if (existType != null) {
                    return "产品类别已存在！";
                } else {
                    productTypeRepository.save(productType);
                }
            }
        }
        productTypeRepository.save(productType);
        return null;
    }
}