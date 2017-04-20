package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmUserIntegralRecord;
import com.ydzb.account.repository.WmUserIntegralRecordRepository;
import com.ydzb.account.service.IWmUserIntegralRecordService;
import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 用户积分记录service实现
 */
@Service
public class WmUserIntegralRecordServiceImpl implements IWmUserIntegralRecordService {

    @Autowired
    private WmUserIntegralRecordRepository wmUserIntegralRecordRepository;

    /**
     * 创建
     * @param userId
     * @param fundflow
     * @param integral
     * @param balance
     * @param type
     * @param linkType
     * @param linkId
     * @return
     */
    @Override
    public WmUserIntegralRecord createOne(Long userId, String fundflow, BigDecimal integral,
            BigDecimal balance, Integer type, Integer linkType, Long linkId) {
        WmUserIntegralRecord record = new WmUserIntegralRecord();
        record.setUserId(userId);
        record.setFundflow(fundflow);
        record.setIntegral(integral);
        record.setBalance(balance);
        record.setType(type);
        record.setLinkType(linkType);
        record.setLinkId(linkId);
        record.setCreated(DateUtil.getSystemTimeSeconds());
        return wmUserIntegralRecordRepository.createOrUpdate(record);
    }
}