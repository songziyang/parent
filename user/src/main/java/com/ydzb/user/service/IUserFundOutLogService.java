package com.ydzb.user.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.user.entity.UserFundInLog;
import com.ydzb.user.entity.UserFundOutLog;

import java.math.BigDecimal;

public interface IUserFundOutLogService extends IBaseService<UserFundOutLog, Long> {


    /**
     *
     * @param userId
     * @param linkId
     * @param type
     * @param outFund
     * @param balance
     * @param remark
     * @return
     */
    public Long saveUserFundOutLog(Long userId,Long linkId,Integer type,BigDecimal outFund,BigDecimal balance,String remark);

}
