package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmRedPacketCash;
import com.ydzb.account.repository.WmRedpacketCashRepository;
import com.ydzb.account.service.IWmRedpacketCashService;
import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by sy on 2016/6/4.
 */
@Service
public class WmRedpacketCashServiceImpl implements IWmRedpacketCashService {

    @Autowired
    private WmRedpacketCashRepository wmRedpacketCashRepository;

    /**
     * 根据触发类型查询
     * @param triggerType
     * @return
     */
    @Override
    public WmRedPacketCash findByTriggerType(Integer triggerType) {
        return wmRedpacketCashRepository.findByTriggerType(triggerType);
    }

    @Override
    public WmRedPacketCash createOrUpdate(WmRedPacketCash redPacketCash) {
        return wmRedpacketCashRepository.createOrUpdate(redPacketCash);
    }

    @Override
    public WmRedPacketCash createOne(BigDecimal fund, Integer triggerType, Integer userDays,
            Long beginDate, Long endDate, String name) {
        WmRedPacketCash redPacketCash = new WmRedPacketCash();
        redPacketCash.setFund(fund);
        redPacketCash.setTriggerType(triggerType);
        redPacketCash.setUseDays(userDays);
        redPacketCash.setBeginTime(beginDate);
        redPacketCash.setFinishTime(endDate);
        redPacketCash.setStatus(0);
        redPacketCash.setCreated(DateUtil.getSystemTimeSeconds());
        redPacketCash.setName(name);
        return createOrUpdate(redPacketCash);
    }
}