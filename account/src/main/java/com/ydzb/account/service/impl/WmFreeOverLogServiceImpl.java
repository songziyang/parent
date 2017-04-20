package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmFreeOverLog;
import com.ydzb.account.repository.WmFreeOverLogRepository;
import com.ydzb.account.service.IWmFreeOverLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 随心存到期日志service实现
 */
@Service
public class WmFreeOverLogServiceImpl implements IWmFreeOverLogService {

    @Autowired
    private WmFreeOverLogRepository freeOverLogRepository;

    /**
     * 创建
     * @param userId 用户id
     * @param accountId 随心存账户id
     * @param type 类型
     * @param fund 金额
     * @param logTime 记录时间
     * @param grandFund 其他活动赠与资金
     * @return
     */
    @Override
    public WmFreeOverLog createOne(Long userId, Long accountId, Integer type, BigDecimal fund,
            Long logTime, BigDecimal grandFund) throws Exception {
        WmFreeOverLog freeOverLog = new WmFreeOverLog();
        freeOverLog.setUserId(userId);
        freeOverLog.setAccountId(accountId);
        freeOverLog.setType(type);
        freeOverLog.setFund(fund);
        freeOverLog.setLogTime(logTime);
        freeOverLog.setGrandFund(grandFund);
        return freeOverLogRepository.createOrUpdate(freeOverLog);
    }
}