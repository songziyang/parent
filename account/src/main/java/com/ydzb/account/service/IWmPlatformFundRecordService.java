package com.ydzb.account.service;


import com.ydzb.account.context.WmPlatformRecordLinkType;
import com.ydzb.account.context.WmPlatformRecordType;
import com.ydzb.account.entity.WmPlatformFundRecord;

import java.math.BigDecimal;

/**
 * 平台资金记录service接口
 */
public interface IWmPlatformFundRecordService {


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
    WmPlatformFundRecord savePlatformFundRecord(WmPlatformRecordType type, BigDecimal fund, BigDecimal usableFund, Long linkId, WmPlatformRecordLinkType linkType, String opinfo);


}
