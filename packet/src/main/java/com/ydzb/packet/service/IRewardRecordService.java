package com.ydzb.packet.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.packet.entity.RewardRecord;

import java.util.List;
import java.util.Map;

public interface IRewardRecordService extends IBaseService<RewardRecord, Long> {

    /**
     * 查询导出excel数据
     * @param filters
     * @return
     */
    public List<Object[]> findExportData(Map<String, Object> filters);

    public String exportExcel(List<Object[]> pays, String address);
}
