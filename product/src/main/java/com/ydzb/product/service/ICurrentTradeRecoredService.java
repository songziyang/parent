package com.ydzb.product.service;


import com.ydzb.core.service.IBaseService;
import com.ydzb.product.entity.CurrentTradeRecored;

import java.util.List;
import java.util.Map;


public interface ICurrentTradeRecoredService extends IBaseService<CurrentTradeRecored, Long> {


    /**
     * 导出excel
     * @param tradeRecoreds
     * @param address
     * @return
     */
    public String exportExcel(List<Object[]> tradeRecoreds, String address);

    public List<Object[]> findExportData(Map<String, Object> filter);
}