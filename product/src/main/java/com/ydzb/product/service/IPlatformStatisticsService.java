package com.ydzb.product.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.product.entity.PlatformStatistics;

import java.util.List;
import java.util.Map;


/**
 * 平台统计service接口
 */
public interface IPlatformStatisticsService extends IBaseService<PlatformStatistics, Long> {

    /**
     * 导出Excel
     *
     * @param statistics
     * @param address
     * @return
     */
    public String exportExcel(List<Object[]> statistics, String address);


    /**
     * 查询导出excel数据
     * @param filter
     * @return
     */
    public List<Object[]> findExportData(Map<String, Object> filter);
}