package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmRagularOverLog;
import com.ydzb.account.repository.WmRagularOverLogRepository;
import com.ydzb.account.service.IWmRagularOverLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 定存宝到期日志service实现
 */
@Service
public class WmRagularOverLogServiceImpl implements IWmRagularOverLogService {

    @Autowired
    private WmRagularOverLogRepository ragularOverLogRepository;

    /**
     * 创建
     * @param userId 用户id
     * @param accountId 用户定存产品记录id
     * @param type 类别：0-产品到期，1-产品转让
     * @param fund 金额
     * @param logTime 记录时间(带时分秒)
     * @param grandFund 其他活动赠与资金
     * @return
     */
    @Override
    public WmRagularOverLog createOne(Long userId, Long accountId, Integer type, BigDecimal fund, Long logTime, BigDecimal grandFund) {
        WmRagularOverLog overLog = new WmRagularOverLog();
        overLog.setUserId(userId);
        overLog.setAccountId(accountId);
        overLog.setType(type);
        overLog.setFund(fund);
        overLog.setLogTime(logTime);
        overLog.setGrandFund(grandFund);
        return (WmRagularOverLog) ragularOverLogRepository.createOrUpdate(overLog);
    }
}