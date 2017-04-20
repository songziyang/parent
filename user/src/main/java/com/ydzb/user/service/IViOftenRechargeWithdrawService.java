package com.ydzb.user.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.user.entity.ViOftenRechargeWithdraw;

import java.util.List;
import java.util.Map;

/**
 * Created by sy on 2016/9/30.
 */
public interface IViOftenRechargeWithdrawService extends IBaseService<ViOftenRechargeWithdraw, String> {

    List<Object[]> findExportData(Map<String, Object> filters);

    String exportExcel(List<Object[]> records, String address);
}