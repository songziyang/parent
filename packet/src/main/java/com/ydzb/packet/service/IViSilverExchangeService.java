package com.ydzb.packet.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.packet.entity.SilverUser;
import com.ydzb.packet.entity.ViSilverExchange;

import java.util.List;
import java.util.Map;

public interface IViSilverExchangeService extends IBaseService<ViSilverExchange, Long> {

    public void updateSilverExchange(Long id, Integer status);

    public void auditFailure(Long id);

    public SilverUser querySilverUserByUserId(Long userId);

    public List<Object[]> findExportData(Map<String, Object> filter);

    public String exportExcel(List<Object[]> exchanges, String address);
}
