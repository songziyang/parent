package com.ydzb.user.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.user.entity.ViDivestmentUser;

import java.util.List;
import java.util.Map;


public interface IViDivestmentUserService extends IBaseService<ViDivestmentUser, Long> {



    /**
     * 查询导出excel数据
     * @param filters
     * @return
     */
    List<Object[]> findExportData(Map<String, Object> filters);

    /**
     * 导出excel
     * @param data
     * @param address
     * @return
     */
    String exportExcel(List<Object[]> data, String address);


}
