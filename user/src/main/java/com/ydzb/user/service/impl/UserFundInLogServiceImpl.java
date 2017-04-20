package com.ydzb.user.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.user.entity.UserFundInLog;
import com.ydzb.user.repository.IUserFundInLogRepository;
import com.ydzb.user.service.IUserFundInLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class UserFundInLogServiceImpl extends BaseServiceImpl<UserFundInLog, Long> implements IUserFundInLogService {


    @Autowired
    private IUserFundInLogRepository userFundInLogRepository;

    /**
     * 进账日志
     *
     * @param userId
     * @param linkId
     * @param type
     * @param incomeFund
     * @param incomeInterest
     * @param usableFund
     */
    public Long saveUserFundInLog(Long userId,
                                  Long linkId, Integer type, BigDecimal incomeFund,
                                  BigDecimal incomeInterest, BigDecimal usableFund, String remark) {
        // 进账日志
        UserFundInLog log = new UserFundInLog();
        log.setUserId(userId);
        log.setLinkId(linkId);
        log.setType(type);
        log.setReceiptsTime(DateUtil.getSystemTimeSeconds());
        log.setIncomeFund(incomeFund);
        log.setIncomeInterest(incomeInterest);
        log.setUsableFund(usableFund);
        log.setRemark(remark);
        userFundInLogRepository.save(log);
        return log.getId();
    }

    public IUserFundInLogRepository getUserFundInLogRepository() {
        return userFundInLogRepository;
    }

    public void setUserFundInLogRepository(IUserFundInLogRepository userFundInLogRepository) {
        this.userFundInLogRepository = userFundInLogRepository;
    }
}
