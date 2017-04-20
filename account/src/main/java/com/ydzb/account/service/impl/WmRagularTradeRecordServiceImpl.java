package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmRagularTradeRecored;
import com.ydzb.account.repository.WmRagularTradeRecordRepository;
import com.ydzb.account.service.IWmRagularTradeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 定存宝交易记录service实现
 */
@Service
public class WmRagularTradeRecordServiceImpl implements IWmRagularTradeRecordService {

    @Autowired
    private WmRagularTradeRecordRepository ragularTradeRecordRepository;

    /**
     * 根据起始时间查询定存宝交易记录id以及购买金额
     * 复投类型
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    @Override
    public List<Object[]> queryRagularInfoBetweenTime(Long startTime, Long endTime) {
        return ragularTradeRecordRepository.queryRagularInfoBetweenTime(startTime, endTime);
    }

    /**
     * 创建
     * @param names 来源名称
     * @param days 产品天数
     * @param fund 购买资金
     * @param buyTime 购买时间
     * @param type 类型
     * @param userId 用户id
     * @param fundSource 资金来源
     * @param logId 日志id
     * @return
     */
    @Override
    public WmRagularTradeRecored createOne(String names, Integer days, BigDecimal fund, Long buyTime,
                Integer type, Long userId, Integer fundSource, Long logId) {
        WmRagularTradeRecored ragularTradeRecored = new WmRagularTradeRecored();
        ragularTradeRecored.setNames(names);
        ragularTradeRecored.setDays(days);
        ragularTradeRecored.setFund(fund);
        ragularTradeRecored.setBuyTime(buyTime);
        ragularTradeRecored.setType(type);
        ragularTradeRecored.setUserId(userId);
        ragularTradeRecored.setFundSource(fundSource);
        ragularTradeRecored.setLogId(logId);
        return (WmRagularTradeRecored) ragularTradeRecordRepository.createOrUpdate(ragularTradeRecored);
    }
}