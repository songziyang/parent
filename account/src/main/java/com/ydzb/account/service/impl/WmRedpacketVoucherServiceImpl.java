package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmRedpacketVoucher;
import com.ydzb.account.repository.WmRedpacketVoucherRepository;
import com.ydzb.account.service.IWmRedpacketVoucherService;
import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 定存红包service实现
 */
@Service
public class WmRedpacketVoucherServiceImpl implements IWmRedpacketVoucherService {

    @Autowired
    private WmRedpacketVoucherRepository wmRedpacketVoucherRepository;

    /**
     * 根据触发类型查询定存红包
     * @param triggerType 触发类型
     * @return
     */
    @Override
    public WmRedpacketVoucher queryByTriggerType(Integer triggerType) {
        return wmRedpacketVoucherRepository.queryByTriggerType(triggerType);
    }

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
    @Override
    public WmRedpacketVoucher createOne(BigDecimal fund, Integer limitFund,
            String name, Integer triggerType, Integer useDays, Long beginDate, Long endDate) {
        WmRedpacketVoucher voucher = new WmRedpacketVoucher();
        voucher.setFund(fund);
        voucher.setLimitFund(limitFund);
        voucher.setName(name);
        voucher.setTriggerType(triggerType);
        voucher.setUseDays(useDays);
        voucher.setBeginDate(beginDate);
        voucher.setEndDate(endDate);
        voucher.setStatus(0);
        voucher.setCreated(DateUtil.getSystemTimeSeconds());
        voucher.setCreateUser(0L);
        return wmRedpacketVoucherRepository.createOrUpdate(voucher);
    }

}