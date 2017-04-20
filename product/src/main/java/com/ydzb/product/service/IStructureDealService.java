package com.ydzb.product.service;


import com.ydzb.core.service.IBaseService;
import com.ydzb.product.entity.StructureDeal;

import java.util.List;
import java.util.Map;

/**
 * 稳进宝交易记录service接口
 */
public interface IStructureDealService extends IBaseService<StructureDeal, Long> {

    List<Object[]> findExportData(Map<String, Object> filters);

    String exportExcel(List<Object[]> data, String address);
}
