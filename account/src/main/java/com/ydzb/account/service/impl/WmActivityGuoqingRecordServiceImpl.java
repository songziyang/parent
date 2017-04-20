package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmActivityGuoqingRecord;
import com.ydzb.account.repository.WmActivityGuoqingRecordRepository;
import com.ydzb.account.service.IWmActivityGuoqingRecordService;
import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 国庆活动记录service实现
 */
@Service
public class WmActivityGuoqingRecordServiceImpl implements IWmActivityGuoqingRecordService {

    @Autowired
    private WmActivityGuoqingRecordRepository wmActivityGuoqingRecordRepository;

    /**
     * 创建
     * @param userId 用户id
     * @param type 类型
     * @param linkId 外链id
     * @param fund 福袋数量
     * @param usableFund 剩余福袋数量
     * @return
     */
    @Override
    public WmActivityGuoqingRecord createOne(Long userId, int type, Long linkId, int fund, int usableFund) {
        WmActivityGuoqingRecord record = new WmActivityGuoqingRecord();
        record.setUserId(userId);
        record.setType(type);
        record.setLinkId(linkId);
        record.setFund(fund);
        record.setUsableFund(usableFund);
        record.setCreated(DateUtil.getSystemTimeSeconds());
        return wmActivityGuoqingRecordRepository.createOrUpdate(record);
    }
}