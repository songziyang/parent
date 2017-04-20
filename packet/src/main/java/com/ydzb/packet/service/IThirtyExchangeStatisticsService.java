package com.ydzb.packet.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.packet.entity.ThirtyExchangeStatistics;

import java.util.List;
import java.util.Map;

/**
 * Created by sy on 2016/8/25.
 */
public interface IThirtyExchangeStatisticsService extends IBaseService<ThirtyExchangeStatistics, Long> {

    List<Object[]> findExportData(Map<String, Object> filter);
    String exportExcel(List<Object[]> exchanges, String address);
}