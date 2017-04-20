package com.ydzb.product.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.product.entity.BeautyTradeRecord;

import java.util.List;
import java.util.Map;

/**
 * 美利金融交易记录service接口
 */
public interface IBeautyTradeRecordService extends IBaseService<BeautyTradeRecord, Long> {

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