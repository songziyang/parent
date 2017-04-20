package com.ydzb.withdraw.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.withdraw.entity.WithdrawNum;

import java.util.List;
import java.util.Map;

public interface IWithdrawNumService extends IBaseService<WithdrawNum, Long> {

    public void initData() throws Exception;

    public void deleteAllWithdrawNum() throws Exception;

    public List<Object[]> findExportData(Map<String, Object> filter);

    /**
     * 导出excel
     *
     * @param withDrawNums
     * @param address
     * @return
     */
    public String exportExcel(List<Object[]> withDrawNums, String address);
}
