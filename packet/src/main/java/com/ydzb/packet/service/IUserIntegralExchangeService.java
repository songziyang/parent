package com.ydzb.packet.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.packet.entity.UserIntegralExchange;

import java.util.List;
import java.util.Map;

/**
 * 积分兑换
 */
public interface IUserIntegralExchangeService extends IBaseService<UserIntegralExchange, Long> {

    public void updateExchange(Long id, Integer status);

    public List<Object[]> findExportData(Map<String, Object> filter);

    public String exportExcel(List<Object[]> exchanges, String address);

    /**
     * 更新
     * @param ids
     * @param status
     */
    public void updateExchange(Long[] ids, Integer status);

    /**
     * 充值为待处理
     */
    public void resetToUnderHandle(Long id);

    /**
     * 订单取消
     * @param id
     */
    public void auditFailure(Long id);
}
