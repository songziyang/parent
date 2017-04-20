package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmCurrentTradeRecored;
import com.ydzb.account.repository.WmCurrentTradeRecordRepository;
import com.ydzb.account.service.IWmCurrentTradeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 活期宝交易记录service实现
 */
@Service
public class WmCurrentTradeRecordServiceImpl implements IWmCurrentTradeRecordService {

    @Autowired
    private WmCurrentTradeRecordRepository currentTradeRecordRepository;

    /**
     * 创建
     * @param userId 用户id
     * @param names 来源名称
     * @param fund 购买资金
     * @param buyTime 操作时间
     * @param type 类型
     * @param fundSource 来源
     * @param logId 购买（或其他）日志id
     * @return
     */
    @Override
    public WmCurrentTradeRecored createOne(Long userId, String names, BigDecimal fund, Long buyTime, Integer type, Integer fundSource, Long logId) {
        WmCurrentTradeRecored tradeRecored = new WmCurrentTradeRecored();
        tradeRecored.setUserId(userId);
        tradeRecored.setNames(names);
        tradeRecored.setFund(fund);
        tradeRecored.setBuyTime(buyTime);
        tradeRecored.setType(type);
        tradeRecored.setFundSource(fundSource);
        tradeRecored.setLogId(logId);
        return (WmCurrentTradeRecored) currentTradeRecordRepository.createOrUpdate(tradeRecored);
    }
}
