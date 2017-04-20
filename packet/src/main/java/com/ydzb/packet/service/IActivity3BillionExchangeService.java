package com.ydzb.packet.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.packet.entity.Activity3BillionExchange;

import java.util.List;
import java.util.Map;

/**
 * 30亿活动兑换service接口
 */
public interface IActivity3BillionExchangeService extends IBaseService<Activity3BillionExchange, Long> {

    Activity3BillionExchange updateExchange(Long id, Integer status);

    void resetToUnderHandle(Long id);

    /**
     * 取消订单
     * @param id
     */
    void auditFailure(Long id);

    /**
     * 更新
     * @param ids
     * @param status
     */
    void updateExchange(Long[] ids, Integer status);


    List<Object[]> findExportData(Map<String, Object> filter);

    String exportExcel(List<Object[]> exchanges, String address);
}