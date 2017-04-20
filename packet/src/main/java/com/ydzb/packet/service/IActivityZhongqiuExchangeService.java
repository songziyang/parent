package com.ydzb.packet.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.packet.entity.ActivityZhongqiuExchange;

import java.util.List;
import java.util.Map;

/**
 * 中秋活动
 */
public interface IActivityZhongqiuExchangeService extends IBaseService<ActivityZhongqiuExchange, Long> {

    /**
     * 查询导出数据
     *
     * @param filter
     * @return
     */
    public List<Object[]> findExportData(Map<String, Object> filter);

    /**
     * 导出Excel
     *
     * @param exchanges
     * @param address
     * @return
     */
    public String exportExcel(List<Object[]> exchanges, String address);

    /**
     * 确认收货 和 确认发货
     *
     * @param id
     * @param status
     */
    public void updateActivityZhongqiuExchange(Long id, Integer status);

    /**
     * 取消订单
     *
     * @param id
     */
    public void auditFailure(Long id);

}
