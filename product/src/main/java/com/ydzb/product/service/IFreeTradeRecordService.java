package com.ydzb.product.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.product.entity.FreeTradeRecord;

import java.util.List;
import java.util.Map;

/**
 * 随心存交易记录service接口
 */
public interface IFreeTradeRecordService extends IBaseService<FreeTradeRecord, Long> {

    /**
     * 查询导出excel所需数据
     * @param filter
     * @return
     */
    List<Object[]> findExportData(Map<String, Object> filter);

    /**
     * 导出excel
     * @param datas
     * @param address
     * @return
     */
    String exportExcel(List<Object[]> datas, String address);
}