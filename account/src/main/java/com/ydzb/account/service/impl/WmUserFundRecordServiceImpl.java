package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmUserFundRecord;
import com.ydzb.account.repository.WmUserFundRecordRepository;
import com.ydzb.account.service.IWmUserFundRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by sy on 2016/7/25.
 */
@Service
public class WmUserFundRecordServiceImpl implements IWmUserFundRecordService {

    @Autowired
    private WmUserFundRecordRepository wmUserFundRecordRepository;

    @Override
    public WmUserFundRecord saveOrUpdate(WmUserFundRecord entity) {
        return wmUserFundRecordRepository.saveOrUpdate(entity);
    }

    /**
     * 创建
     * @param userId 用户id
     * @param fundflow 来源去向
     * @param fund 金额
     * @param logId 日志id
     * @param type 类型
     * @param fundType 资金类别
     * @param balance 余额
     * @param recordTime 记录时间
     * @return
     */
    @Override
    public WmUserFundRecord createOne(Long userId, String fundflow, BigDecimal fund, Long logId, Integer type, Integer fundType, BigDecimal balance, Long recordTime) {
        WmUserFundRecord fundRecord = new WmUserFundRecord();
        fundRecord.setUserId(userId);
        fundRecord.setFundflow(fundflow);
        fundRecord.setFund(fund);
        fundRecord.setLogId(logId);
        fundRecord.setType(type);
        fundRecord.setFundType(fundType);
        fundRecord.setBalance(balance);
        fundRecord.setRecordTime(recordTime);
        return saveOrUpdate(fundRecord);
    }
}