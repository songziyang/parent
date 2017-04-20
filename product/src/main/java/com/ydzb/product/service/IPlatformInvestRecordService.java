package com.ydzb.product.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.product.entity.PlatformFundRecord;
import com.ydzb.product.entity.PlatformInvestRecord;
import com.ydzb.product.enumeration.PlatformRecordLinkType;
import com.ydzb.product.enumeration.PlatformRecordType;

import java.math.BigDecimal;

/**
 * 平台债权记录service接口
 */
public interface IPlatformInvestRecordService extends IBaseService<PlatformInvestRecord, Long> {

    /**
     * 创建
     * @param type 类型
     * @param invest 债权
     * @param allInvest 债权总额
     * @param linkId 链接id
     * @param linkType 链接类型
     * @param opinfo 操作说明
     * @return
     */
    PlatformInvestRecord createOne(Integer type, BigDecimal invest, BigDecimal allInvest, Long linkId, Integer linkType, String opinfo);

    PlatformInvestRecord createOne(PlatformRecordType type, BigDecimal invest, BigDecimal allInvest, Long linkId, PlatformRecordLinkType linkType, String opinfo);
}
