package com.ydzb.packet.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.packet.entity.ExpMoneyRecord;

import java.util.List;
import java.util.Map;

/**
 * 体验金记录service接口
 * @author sy
 */
public interface IExpMoneyRecordService extends IBaseService<ExpMoneyRecord, Long> {

    /**
     * 导出excel
     * @param records
     * @param address
     * @return
     */
    public String exportExcel(List<Object[]> records, String address);

    public List<Object[]> findExportData(Map<String, Object> filter);
}


