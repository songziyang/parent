package com.ydzb.packet.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.packet.entity.SilverTraceRecordThirty;
import com.ydzb.packet.service.ISilverTraceRecordThirtyService;
import org.springframework.stereotype.Service;

/**
 * Created by sy on 2016/7/3.
 */
@Service
public class SilverTraceRecordThirtyServiceImpl extends BaseServiceImpl<SilverTraceRecordThirty, Long> implements ISilverTraceRecordThirtyService {

    /**
     * 创建
     * @param userId
     * @param type
     * @param linkId
     * @param fund
     * @param usableFund
     * @return
     */
    @Override
    public SilverTraceRecordThirty createOne(Long userId, Integer type, Long linkId, Integer fund, Integer usableFund) {

        SilverTraceRecordThirty silverTraceRecordThirty = new SilverTraceRecordThirty();
        silverTraceRecordThirty.setUserId(userId);
        silverTraceRecordThirty.setType(type);
        silverTraceRecordThirty.setLinkId(linkId);
        silverTraceRecordThirty.setFund(fund);
        silverTraceRecordThirty.setUsableFund(usableFund);
        silverTraceRecordThirty.setCreated(DateUtil.getSystemTimeSeconds());
        return save(silverTraceRecordThirty);
    }
}