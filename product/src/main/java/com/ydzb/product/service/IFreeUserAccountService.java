package com.ydzb.product.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.product.entity.FreeUserAccount;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 随心存购买记录
 */
public interface IFreeUserAccountService extends IBaseService<FreeUserAccount, Long> {

    /**
     * 根据起始时间查询总购买金额
     * @param startDate
     * @param endDate
     * @return
     */
    BigDecimal findTotalFund(Long startDate, Long endDate);

    /**
     * 查询导出excel的数据
     * @param filter
     * @return
     */
    List<Object[]> findExportData(Map<String, Object> filter);

    /**
     * 导出excel
     * @param datas
     * @param address
     * @return
     */
    String exportExcel(List<Object[]> datas, String address);
}