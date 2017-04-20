package com.ydzb.account.service;


import com.ydzb.account.context.WmPlatformRecordLinkType;
import com.ydzb.account.context.WmPlatformRecordType;
import com.ydzb.account.entity.WmPlatformInvestRecord;

import java.math.BigDecimal;

/**
 * 平台债权记录service接口
 */
public interface IWmPlatformInvestRecordService {

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
    WmPlatformInvestRecord savePlatformInvestRecord(WmPlatformRecordType type, BigDecimal invest, BigDecimal allInvest, Long linkId, WmPlatformRecordLinkType linkType, String opinfo);
}
