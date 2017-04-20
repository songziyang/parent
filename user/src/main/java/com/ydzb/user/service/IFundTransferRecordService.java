package com.ydzb.user.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.user.entity.FundTransferRecord;

import java.util.List;
import java.util.Map;

/**
 * 资金转账记录service接口
 * @author sy
 */
public interface IFundTransferRecordService extends IBaseService<FundTransferRecord, Long> {

    /**
     * 查询导出excel所需数据
     * @param filter
     * @return
     */
    public List<Object[]> findExportData(Map<String, Object> filter);

    /**
     * 导出excel
     * @param data
     * @param address
     * @return
     */
    public String exportExcel(List<Object[]> data, String address);
}
