package com.ydzb.account.service.impl;


import com.ydzb.account.context.WmPlatformRecordLinkType;
import com.ydzb.account.context.WmPlatformRecordType;
import com.ydzb.account.entity.WmPlatformInvestRecord;
import com.ydzb.account.repository.IWmPlatformInvestRecordRepository;
import com.ydzb.account.service.IWmPlatformInvestRecordService;

import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 平台债权记录service实现
 */
@Service
public class WmPlatformInvestRecordServiceImpl implements IWmPlatformInvestRecordService {


    @Autowired
    private IWmPlatformInvestRecordRepository platformInvestRecordRepository;

    /**
     * 保存平台债权记录
     *
     * @param type      类型
     * @param invest    债权
     * @param allInvest 债权总额
     * @param linkId    链接id
     * @param linkType  链接类型
     * @param opinfo    操作说明
     * @return
     */
    @Override
    public WmPlatformInvestRecord savePlatformInvestRecord(WmPlatformRecordType type, BigDecimal invest, BigDecimal allInvest, Long linkId, WmPlatformRecordLinkType linkType, String opinfo) {
        WmPlatformInvestRecord record = new WmPlatformInvestRecord();
        record.setType(type == null? null: type.getValue());
        record.setInvest(invest);
        record.setAllInvest(allInvest);
        record.setLinkId(linkId);
        record.setLinkType(linkType == null? null: linkType.getValue());
        record.setOpinfo(opinfo);
        record.setOptime(DateUtil.getSystemTimeSeconds());
        return platformInvestRecordRepository.save(record);
    }

    public IWmPlatformInvestRecordRepository getPlatformInvestRecordRepository() {
        return platformInvestRecordRepository;
    }

    public void setPlatformInvestRecordRepository(IWmPlatformInvestRecordRepository platformInvestRecordRepository) {
        this.platformInvestRecordRepository = platformInvestRecordRepository;
    }
}
