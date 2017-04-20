package com.ydzb.product.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.product.entity.PlatformFundRecord;
import com.ydzb.product.enumeration.PlatformRecordLinkType;
import com.ydzb.product.enumeration.PlatformRecordType;
import com.ydzb.product.repository.IPlatformFundRecordRepository;
import com.ydzb.product.service.IPlatformFundRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 平台资金记录service实现
 */
@Service
public class PlatformFundRecordServiceImpl extends BaseServiceImpl<PlatformFundRecord, Long> implements IPlatformFundRecordService {

    @Autowired
    private IPlatformFundRecordRepository platformFundRecordRepository;

    /**
     * 创建
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
    public PlatformFundRecord createOne(Integer type, BigDecimal fund, BigDecimal usableFund, Long linkId, Integer linkType, String opinfo) {
        PlatformFundRecord record = new PlatformFundRecord();
        record.setType(type);
        record.setFund(fund);
        record.setUsableFund(usableFund);
        record.setUser(null);
        record.setLinkType(linkType);
        record.setOpinfo(opinfo);
        record.setOptime(DateUtil.getSystemTimeSeconds());
        return platformFundRecordRepository.save(record);
    }

    @Override
    public PlatformFundRecord createOne(PlatformRecordType type, BigDecimal fund, BigDecimal usableFund, Long linkId, PlatformRecordLinkType linkType, String opinfo) {
        PlatformFundRecord record = new PlatformFundRecord();
        record.setType(type == null ? null : type.getValue());
        record.setFund(fund);
        record.setUsableFund(usableFund);
        record.setUser(null);
        record.setLinkType(linkType == null ? null : linkType.getValue());
        record.setOpinfo(opinfo);
        record.setOptime(DateUtil.getSystemTimeSeconds());
        return platformFundRecordRepository.save(record);
    }
}
