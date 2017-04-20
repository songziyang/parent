package com.ydzb.product.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.product.entity.PlatformPay;

import java.util.List;
import java.util.Map;

/**
 * 平台支出统计service接口
 * @author sy
 */
public interface IPlatformPayService extends IBaseService<PlatformPay, Long> {

    /**
     * 导出excel
     * @param pays
     * @param address
     * @return
     */
    public String exportExcel(List<Object[]> pays, String address);

    /**
     * 获得导出excel数据
     * @param filters
     * @return
     */
    public List<Object[]> findExportData(Map<String, Object> filters);
}
