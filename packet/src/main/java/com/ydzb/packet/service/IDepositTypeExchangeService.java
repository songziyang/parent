package com.ydzb.packet.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.packet.entity.DepositTypeExchange;

import java.util.List;
import java.util.Map;


public interface IDepositTypeExchangeService extends IBaseService<DepositTypeExchange, Long> {

    /**
     * 更新订单状态
     *
     * @param id
     * @param status
     */
    void updateExchange(Long id, Integer status);

    /**
     * 跟新订单状态
     *
     * @param ids
     * @param status
     */
    void updateExchange(Long[] ids, Integer status);


    /**
     * 查询数据
     *
     * @param filter
     * @return
     */
    List<Object[]> findExportData(Map<String, Object> filter);

    /**
     * 导出Excel
     *
     * @param exchanges
     * @param address
     * @return
     */
    String exportExcel(List<Object[]> exchanges, String address);


}
