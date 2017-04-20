package com.ydzb.user.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.user.entity.ViRechargePenny;

import java.util.List;
import java.util.Map;

/**
 * 小额充值service接口
 */
public interface IViRechargePennyService extends IBaseService<ViRechargePenny, Long> {

    Integer queryUsersCount(Long startTime, Long endTime);

    List<Object[]> findExportData(Map<String, Object> filters);

    String exportExcel(List<Object[]> records, String address);
}
