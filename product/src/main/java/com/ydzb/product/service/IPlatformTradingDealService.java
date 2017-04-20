package com.ydzb.product.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.product.entity.PlatformTradingDeal;

import java.math.BigDecimal;
import java.util.List;

/**
 * 平台交易统计详细service接口
 * @author sy
 */
public interface IPlatformTradingDealService extends IBaseService<PlatformTradingDeal, Long> {

    /**
     * 根据平台交易记录类型、操作日期起始时间获得总金额
     * @param type
     * @param startDate
     * @param endDate
     * @return
     */
    public BigDecimal findSumFund(Byte type, String startDate, String endDate);

    /**
     * 导出excel
     * @param tradingDeals
     * @param address
     * @return
     */
    public String exportExcel(List<PlatformTradingDeal> tradingDeals, String address);
}