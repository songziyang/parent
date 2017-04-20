package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmUserFundInLog;
import com.ydzb.account.entity.WmUserIncomeRecord;
import com.ydzb.account.entity.WmUserMoney;
import com.ydzb.account.repository.WmUserIncomeRecordRepository;
import com.ydzb.account.repository.WmUserIncomeRepository;
import com.ydzb.account.service.IWmUserFundInLogService;
import com.ydzb.account.service.IWmUserIncomeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 */
@Service
public class WmUserIncomeRecordServiceImpl implements IWmUserIncomeRecordService {

    @Autowired
    private WmUserIncomeRecordRepository wmUserIncomeRecordRepository;

    @Override
    public WmUserIncomeRecord saveOrUpdate(WmUserIncomeRecord entity) {
        return wmUserIncomeRecordRepository.saveOrUpdate(entity);
    }

    /**
     * 创建
     * @param userId 用户id
     * @param name 来源名称
     * @param income 收益
     * @param pType 产品类型
     * @param optime 操作时间
     * @return
     */
    @Override
    public WmUserIncomeRecord createOne(Long userId, String name, BigDecimal income, Integer pType, Long optime) {
        WmUserIncomeRecord record = new WmUserIncomeRecord();
        record.setUserId(userId);
        record.setName(name);
        record.setIncome(income);
        record.setPtype(pType);
        record.setOptime(optime);
        return (WmUserIncomeRecord) wmUserIncomeRecordRepository.createOrUpdate(record);
    }
}
