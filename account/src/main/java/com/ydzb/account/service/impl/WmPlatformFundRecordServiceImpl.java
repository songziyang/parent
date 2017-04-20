package com.ydzb.account.service.impl;


import com.ydzb.account.context.WmPlatformRecordLinkType;
import com.ydzb.account.context.WmPlatformRecordType;
import com.ydzb.account.entity.WmPlatformFundRecord;
import com.ydzb.account.repository.IWmPlatformFundRecordRepository;
import com.ydzb.account.service.IWmPlatformFundRecordService;

import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 平台资金记录service实现
 */
@Service
public class WmPlatformFundRecordServiceImpl implements IWmPlatformFundRecordService {

    @Autowired
    private IWmPlatformFundRecordRepository platformFundRecordRepository;


    /**
     * 保存平台用户资金记录
     *
     * @param type       类型
     * @param fund       金额
     * @param usableFund 余额
     * @param linkId     链接id
     * @param linkType   链接类型
     * @param opinfo     操作说明
     * @return
     */
    @Override
    public WmPlatformFundRecord savePlatformFundRecord(WmPlatformRecordType type, BigDecimal fund, BigDecimal usableFund, Long linkId, WmPlatformRecordLinkType linkType, String opinfo) {
        WmPlatformFundRecord record = new WmPlatformFundRecord();
        record.setType(type == null? null: type.getValue());
        record.setFund(fund);
        record.setUsableFund(usableFund);
        record.setLinkId(linkId);
        record.setLinkType(linkType == null? null: linkType.getValue());
        record.setOpinfo(opinfo);
        record.setOptime(DateUtil.getSystemTimeSeconds());
        return platformFundRecordRepository.save(record);
    }


    public IWmPlatformFundRecordRepository getPlatformFundRecordRepository() {
        return platformFundRecordRepository;
    }

    public void setPlatformFundRecordRepository(IWmPlatformFundRecordRepository platformFundRecordRepository) {
        this.platformFundRecordRepository = platformFundRecordRepository;
    }
}
