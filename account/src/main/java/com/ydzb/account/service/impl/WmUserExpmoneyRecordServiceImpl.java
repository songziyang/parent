package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmUserExMoney;
import com.ydzb.account.entity.WmUserExpMoneyRecord;
import com.ydzb.account.repository.WmUserExpmoneyRecordRepository;
import com.ydzb.account.service.IWmUserExpmoneyRecordService;
import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 体验金记录service实现
 */
@Service
public class WmUserExpmoneyRecordServiceImpl implements IWmUserExpmoneyRecordService {

    @Autowired
    private WmUserExpmoneyRecordRepository wmUserExpmoneyRecordRepository;

    /**
     * 创建
     * @param wmUserExMoney 用户体验金
     * @param fundflow 来源去向
     * @param fund 金额
     * @param inLogId 入账日志id
     * @param type 体验金类型
     */
    @Override
    public void createOne(WmUserExMoney wmUserExMoney, String fundflow, BigDecimal fund, Long inLogId, Integer type) {

        WmUserExpMoneyRecord record = new WmUserExpMoneyRecord();
        record.setUserId(wmUserExMoney == null? null: wmUserExMoney.getUserId());
        record.setFundflow(fundflow);
        record.setFund(fund);
        record.setBalance(wmUserExMoney == null? null: wmUserExMoney.getAbleMoney());
        record.setType(type);
        record.setRecordTime(DateUtil.getSystemTimeSeconds());
        record.setLogId(inLogId);
        wmUserExpmoneyRecordRepository.saveOrUpdate(record);
    }
}
