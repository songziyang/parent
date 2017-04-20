package com.ydzb.product.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.product.entity.PlatformFundRecord;
import com.ydzb.product.enumeration.PlatformRecordLinkType;
import com.ydzb.product.enumeration.PlatformRecordType;

import java.math.BigDecimal;

/**
 * 平台资金记录service接口
 */
public interface IPlatformFundRecordService extends IBaseService<PlatformFundRecord, Long> {

    /**
     * 创建
     * @param type 类型
     * @param fund 金额
     * @param usableFund 余额
     * @param linkId 链接id
     * @param linkType 链接类型
     * @param opinfo 操作说明
     * @return
     */
    PlatformFundRecord createOne(Integer type, BigDecimal fund, BigDecimal usableFund, Long linkId, Integer linkType, String opinfo);

    PlatformFundRecord createOne(PlatformRecordType type, BigDecimal fund, BigDecimal usableFund, Long linkId, PlatformRecordLinkType linkType, String opinfo);
}
