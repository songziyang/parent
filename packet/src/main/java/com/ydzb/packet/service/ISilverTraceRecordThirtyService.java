package com.ydzb.packet.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.packet.entity.SilverTraceRecordThirty;

/**
 * Created by sy on 2016/7/3.
 */
public interface ISilverTraceRecordThirtyService extends IBaseService<SilverTraceRecordThirty, Long> {

    SilverTraceRecordThirty createOne(Long userId, Integer type, Long linkId, Integer fund, Integer usableFund);
}