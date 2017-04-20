package com.ydzb.user.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.user.entity.UserFundRecord;
import com.ydzb.user.repository.IUserFundRecordRepository;
import com.ydzb.user.service.IUserFundRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class UserFundRecordServiceImpl extends BaseServiceImpl<UserFundRecord, Long> implements IUserFundRecordService {


    @Autowired
    private IUserFundRecordRepository userFundRecordRepository;

    @Override
    public void saveUserFundRecord(Long userId, String fundflow, BigDecimal fund, Integer type, Integer fundType, BigDecimal balance,Long logId) {

        UserFundRecord userFundRecord = new UserFundRecord();
        userFundRecord.setUserId(userId);
        userFundRecord.setFundflow(fundflow);
        userFundRecord.setFund(fund);
        userFundRecord.setType(type);
        userFundRecord.setFundType(fundType);
        userFundRecord.setBalance(balance);
        userFundRecord.setRecordTime(DateUtil.getSystemTimeSeconds());
        userFundRecord.setLogId(logId);
        userFundRecordRepository.save(userFundRecord);
    }

    public IUserFundRecordRepository getUserFundRecordRepository() {
        return userFundRecordRepository;
    }

    public void setUserFundRecordRepository(IUserFundRecordRepository userFundRecordRepository) {
        this.userFundRecordRepository = userFundRecordRepository;
    }
}
