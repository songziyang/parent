package com.ydzb.packet.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.packet.entity.ActivityGuoqingExchange;

import java.util.List;
import java.util.Map;

/**
 * 国庆活动记录service接口
 */
public interface IActivityGuoqingExchangeService extends IBaseService<ActivityGuoqingExchange, Long> {

    void updateActivityZhongqiuExchange(Long id, Integer status);

    /**
     * 取消订单
     *
     * @param id
     */
    void auditFailure(Long id);

    /**
     * 保存桂花记录
     *
     * @param userId     用户ID
     * @param type       类型 1、获得 2、使用3、退款
     * @param linkId     外链ID 类型1定存记录ID 类型2兑换记录ID
     * @param fund       数量
     * @param usableFund 余额数量
     */
    void saveActivityZhongqiuRecord(Long userId, Integer type, Long linkId, Integer fund, Integer usableFund);

    /**
     * 查询导出数据
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