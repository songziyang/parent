package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmFreeTradeRecored;
import com.ydzb.account.repository.WmFreeTradeRecordRepository;
import com.ydzb.account.service.IWmFreeTradeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 随心存交易记录service实现
 */
@Service
public class WmFreeTradeRecordServiceImpl implements IWmFreeTradeRecordService {

    @Autowired
    private WmFreeTradeRecordRepository freeTradeRecordRepository;

    /**
     * 创建
     * @param names 来源名称
     * @param days 产品天数
     * @param fund 购买资金
     * @param buyTime 购买时间
     * @param type 类型
     * @param userId userId
     * @param fundSource 金额来源
     * @param logId 日志id
     * @return
     * @throws Exception
     */
    @Override
    public WmFreeTradeRecored createOne(String names, Integer days, BigDecimal fund, Long buyTime,
            Integer type, Long userId, Integer fundSource, Long logId) throws Exception {
        WmFreeTradeRecored freeTradeRecored = new WmFreeTradeRecored();
        freeTradeRecored.setNames(names);
        freeTradeRecored.setDays(days);
        freeTradeRecored.setFund(fund);
        freeTradeRecored.setBuyTime(buyTime);
        freeTradeRecored.setType(type);
        freeTradeRecored.setUserId(userId);
        freeTradeRecored.setFundSource(fundSource);
        freeTradeRecored.setLogId(logId);
        return freeTradeRecordRepository.createOrUpdate(freeTradeRecored);
    }
}