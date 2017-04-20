package com.ydzb.packet.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.packet.entity.SilverExchange;
import com.ydzb.packet.entity.SilverExchangeThirty;
import com.ydzb.packet.entity.SilverUser;
import com.ydzb.packet.entity.SilverUserThirty;

import java.util.List;
import java.util.Map;

/**
 * 银币活动
 */
public interface ISilverExchangeThirtyService extends IBaseService<SilverExchangeThirty, Long> {

    public void updateSilverExchange(Long id, Integer status);


    public void auditFailure(Long id);


    public SilverUserThirty querySilverUserByUserId(Long userId);

    public List<Object[]> findExportData(Map<String, Object> filter);

    public String exportExcel(List<Object[]> exchanges, String address);
}
