package com.ydzb.user.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.user.entity.UserFundOutLog;
import com.ydzb.user.repository.IUserFundOutLogRepository;
import com.ydzb.user.service.IUserFundOutLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class UserFundOutLogServiceImpl extends BaseServiceImpl<UserFundOutLog, Long> implements IUserFundOutLogService {

    @Autowired
    private IUserFundOutLogRepository userFundOutLogRepository;


    @Override
    public Long saveUserFundOutLog(Long userId, Long linkId, Integer type, BigDecimal outFund, BigDecimal balance, String remark) {
        UserFundOutLog userFundOutLog = new UserFundOutLog();
        userFundOutLog.setUserId(userId);
        userFundOutLog.setLinkId(linkId);
        userFundOutLog.setType(type);
        userFundOutLog.setOutFund(outFund);
        userFundOutLog.setBalance(balance);
        userFundOutLog.setRemark(remark);
        userFundOutLog.setOutTime(DateUtil.getSystemTimeSeconds());
        userFundOutLogRepository.save(userFundOutLog);
        return userFundOutLog.getId();
    }

    public IUserFundOutLogRepository getUserFundOutLogRepository() {
        return userFundOutLogRepository;
    }

    public void setUserFundOutLogRepository(IUserFundOutLogRepository userFundOutLogRepository) {
        this.userFundOutLogRepository = userFundOutLogRepository;
    }
}
