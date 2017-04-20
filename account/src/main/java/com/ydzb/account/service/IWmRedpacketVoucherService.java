package com.ydzb.account.service;

import com.ydzb.account.entity.WmRedpacketVoucher;

import java.math.BigDecimal;

/**
 * 定存红包service接口
 */
public interface IWmRedpacketVoucherService {

    /**
     * 根据触发类型查询定存红包
     * @param triggerType 触发类型
     * @return
     */
    WmRedpacketVoucher queryByTriggerType(Integer triggerType);

    /**
     * 创建
     * @param fund
     * @param limitFund
     * @param name
     * @param triggerType
     * @param useDays
     * @param beginDate
     * @param endDate
     * @return
     */
    WmRedpacketVoucher createOne(BigDecimal fund, Integer limitFund,
            String name, Integer triggerType, Integer useDays, Long beginDate, Long endDate);
}
