package com.ydzb.product.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.product.entity.VProductSales;

import java.util.List;
import java.util.Map;

/**
 * 产品售出service接口
 */
public interface IVProductSalesService extends IBaseService<VProductSales, Long> {

    public Map<String, Object> querySales();

    public String exportExcel(List<Object[]> productSales, String address);

    /**
     * 查询导出excel数据
     * @param filter
     * @return
     */
    public List<Object[]> findExportData(Map<String, Object> filter);
}