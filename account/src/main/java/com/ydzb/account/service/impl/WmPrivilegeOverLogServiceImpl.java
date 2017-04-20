package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmPrivilegeOverLog;
import com.ydzb.account.repository.WmPrivilegeOverLogRepository;
import com.ydzb.account.service.IWmPrivilegeOverLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 新手特权赎回日志service实现
 */
@Service
public class WmPrivilegeOverLogServiceImpl implements IWmPrivilegeOverLogService {

    @Autowired
    private WmPrivilegeOverLogRepository privilegeOverLogRepository;

    /**
     * 创建
     * @param userId 用户id
     * @param type 类型
     * @param redemptionFund 赎回金额
     * @param redemptionTime 赎回时间
     * @return
     */
    @Override
    public WmPrivilegeOverLog createOne(Long userId, Integer type, BigDecimal redemptionFund, Long redemptionTime) {
        WmPrivilegeOverLog privilegeOverLog = new WmPrivilegeOverLog();
        privilegeOverLog.setUserId(userId);
        privilegeOverLog.setType(type);
        privilegeOverLog.setRedemptionFund(redemptionFund);
        privilegeOverLog.setRedemptionTime(redemptionTime);
        return privilegeOverLogRepository.createOrUpdate(privilegeOverLog);
    }
}