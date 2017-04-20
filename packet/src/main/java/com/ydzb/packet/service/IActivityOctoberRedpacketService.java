package com.ydzb.packet.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.packet.entity.ActivityOctoberRedpacket;

import java.util.List;
import java.util.Map;

/**
 * 金秋十月活动service接口
 */
public interface IActivityOctoberRedpacketService extends IBaseService<ActivityOctoberRedpacket, Long> {

    List<Object[]> findExportData(Map<String, Object> filters);

    /**
     * 导出Excel
     *
     * @param address
     */
    String exportExcel(List<Object[]> datas, String address);
}