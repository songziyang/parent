package com.ydzb.product.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.core.utils.ExcelUtil;
import com.ydzb.core.utils.POIUtil;
import com.ydzb.product.entity.CurrentTradeRecored;
import com.ydzb.product.entity.PlatformTrading;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 平台交易统计service接口
 * @author sy
 */
public interface IPlatformTradingService extends IBaseService<PlatformTrading, Long> {

    /**
     * 查询图标所需的七天数据
     * @return
     */
    public Map<String, String[]> queryDates();

    /**
     * 根据平台交易记录类型、操作起始时间获得总金额
     * @param type
     * @param startDate
     * @param endDate
     * @return
     */
    public BigDecimal findSumFund(Byte type, String startDate, String endDate);

    public String exportExcel(List<Object[]> tradings, String address);

    /**
     * 查询导出excel数据
     * @param filter
     * @return
     */
    public List<Object[]> findExportData(Map<String, Object> filter);
}
