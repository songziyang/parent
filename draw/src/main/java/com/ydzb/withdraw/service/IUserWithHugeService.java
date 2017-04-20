package com.ydzb.withdraw.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.withdraw.entity.UserWithHuge;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface IUserWithHugeService extends IBaseService<UserWithHuge, Long> {

    /**
     *
     * @param filters
     * @return
     */
    List<Object[]> findWithdrawExportData(Map<String, Object> filters);

    String exportExcel(List<Object[]> data, String address);
}