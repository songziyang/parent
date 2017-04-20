package com.ydzb.product.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.product.entity.PlatformInvestRecord;
import com.ydzb.product.enumeration.PlatformRecordLinkType;
import com.ydzb.product.enumeration.PlatformRecordType;
import com.ydzb.product.repository.IPlatformInvestRecordRepository;
import com.ydzb.product.service.IPlatformInvestRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 平台债权记录service实现
 */
@Service
public class PlatformInvestRecordServiceImpl extends BaseServiceImpl<PlatformInvestRecord, Long> implements IPlatformInvestRecordService {

    @Autowired
    private IPlatformInvestRecordRepository platformInvestRecordRepository;

    /**
     * 创建
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
    public PlatformInvestRecord createOne(Integer type, BigDecimal invest, BigDecimal allInvest, Long linkId, Integer linkType, String opinfo) {
        PlatformInvestRecord record = new PlatformInvestRecord();
        record.setType(type);
        record.setInvest(invest);
        record.setAllInvest(allInvest);
        record.setUser(null);
        record.setLinkType(linkType);
        record.setOpinfo(opinfo);
        record.setOptime(DateUtil.getSystemTimeSeconds());
        return platformInvestRecordRepository.save(record);
    }

    /**
     * 创建
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
    public PlatformInvestRecord createOne(PlatformRecordType type, BigDecimal invest, BigDecimal allInvest, Long linkId, PlatformRecordLinkType linkType, String opinfo) {
        PlatformInvestRecord record = new PlatformInvestRecord();
        record.setType(type == null ? null : type.getValue());
        record.setInvest(invest);
        record.setAllInvest(allInvest);
        record.setUser(null);
        record.setLinkType(linkType == null ? null : linkType.getValue());
        record.setOpinfo(opinfo);
        record.setOptime(DateUtil.getSystemTimeSeconds());
        return platformInvestRecordRepository.save(record);
    }
}
