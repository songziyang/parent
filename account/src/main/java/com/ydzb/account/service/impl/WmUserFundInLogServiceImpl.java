package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmUserFundInLog;
import com.ydzb.account.entity.WmUserMoney;
import com.ydzb.account.repository.WmUserFundInLogRepository;
import com.ydzb.account.service.IWmUserFundInLogService;
import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 用户入账日志service实现
 */
@Service
public class WmUserFundInLogServiceImpl implements IWmUserFundInLogService {

    @Autowired
    private WmUserFundInLogRepository wmUserFundInLogRepository;

    /**
     * 添加用户进账日志
     * @param userId 用户id
     * @param wmUserMoney 用户资金
     * @param type 类型
     * @param fund 金额
     * @param linkId 外链id
     * @return
     */
    @Override
    public WmUserFundInLog createOne(Long userId, WmUserMoney wmUserMoney, Integer type, BigDecimal fund, Long linkId) {

        WmUserFundInLog inLog = new WmUserFundInLog();
        inLog.setUserId(userId);
        inLog.setType(type);
        inLog.setReceiptsTime(DateUtil.getSystemTimeSeconds());
        inLog.setIncomeFund(fund);
        inLog.setUsableFund(wmUserMoney == null? BigDecimal.ZERO: wmUserMoney.getUsableFund());
        inLog.setLinkId(linkId);
        return wmUserFundInLogRepository.saveOrUpdate(inLog);
    }

    /**
     * 创建
     * @param userId 用户id
     * @param type 类型
     * @param receiptsTime 到账时间
     * @param fund 所得本金
     * @param incomeInterest 所得利息
     * @param usableFund 用户可用余额
     * @param ramark 备注
     * @param linkId 外链id
     * @return
     */
    @Override
    public WmUserFundInLog createOne(Long userId, Integer type, Long receiptsTime, BigDecimal fund, BigDecimal incomeInterest, BigDecimal usableFund, String ramark, Long linkId) {
        WmUserFundInLog inLog = new WmUserFundInLog();
        inLog.setUserId(userId);
        inLog.setType(type);
        inLog.setReceiptsTime(receiptsTime);
        inLog.setIncomeFund(fund);
        inLog.setIncomeInterest(incomeInterest);
        inLog.setUsableFund(usableFund);
        inLog.setRemark(ramark);
        inLog.setLinkId(linkId);
        return wmUserFundInLogRepository.saveOrUpdate(inLog);
    }

    @Override
    public WmUserFundInLog saveOrUpdate(WmUserFundInLog entity) {
        return wmUserFundInLogRepository.saveOrUpdate(entity);
    }
}
