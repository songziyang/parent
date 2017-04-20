package com.ydzb.product.service;


import com.ydzb.core.service.IBaseService;
import com.ydzb.product.entity.CurrentTradeRecored;
import com.ydzb.product.entity.CurrentUserAccount;

import java.util.List;
import java.util.Map;


public interface ICurrentUserAccountService extends IBaseService<CurrentUserAccount, Long> {

    /**
     * 导出excel
     * @param userAccounts
     * @param address
     * @return
     */
    public String exportExcel(List<Object[]> userAccounts, String address);

    /**
     * 查询导出excel的数据
     * @param filters
     * @return
     */
    public List<Object[]> findExportData(Map<String, Object> filters);
}