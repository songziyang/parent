package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmPrivilegeTradeRecord;
import com.ydzb.account.repository.WmPrivilegeTradeRecordRepository;
import com.ydzb.account.service.IWmPrivilegeTradeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 新手特权交易记录service实现
 */
@Service
public class WmPrivilegeTradeRecordServiceImpl implements IWmPrivilegeTradeRecordService {

    @Autowired
    private WmPrivilegeTradeRecordRepository privilegeTradeRecordRepository;

    /**
     * 创建
     * @param userId 用户id
     * @param names 来源名称
     * @param fund 金额
     * @param buyTime 购买时间
     * @param type 类型
     * @param fundSource 资金来源
     * @param logId 日志id
     * @return
     */
    @Override
    public WmPrivilegeTradeRecord createOne(Long userId, String names, BigDecimal fund, Long buyTime, Integer type, Integer fundSource, Long logId) {
        WmPrivilegeTradeRecord privilegeTradeRecord = new WmPrivilegeTradeRecord();
        privilegeTradeRecord.setUserId(userId);
        privilegeTradeRecord.setNames(names);
        privilegeTradeRecord.setFund(fund);
        privilegeTradeRecord.setBuyTime(buyTime);
        privilegeTradeRecord.setType(type);
        privilegeTradeRecord.setFundSource(fundSource);
        privilegeTradeRecord.setLogId(logId);
        return privilegeTradeRecordRepository.createOrUpdate(privilegeTradeRecord);
    }
}