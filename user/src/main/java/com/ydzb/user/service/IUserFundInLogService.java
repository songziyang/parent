package com.ydzb.user.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.user.entity.UserFundInLog;

import java.math.BigDecimal;

public interface IUserFundInLogService extends IBaseService<UserFundInLog, Long> {

    /**
     * @param userId
     * @param linkId
     * @param type
     * @param incomeFund
     * @param incomeInterest
     * @param usableFund
     * @param remark
     */
    public Long saveUserFundInLog(Long userId,
        Long linkId, Integer type, BigDecimal incomeFund,
        BigDecimal incomeInterest, BigDecimal usableFund, String remark);
}
