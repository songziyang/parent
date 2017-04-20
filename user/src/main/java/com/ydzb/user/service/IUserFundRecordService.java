package com.ydzb.user.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.user.entity.UserFundRecord;

import java.math.BigDecimal;

public interface IUserFundRecordService extends IBaseService<UserFundRecord, Long> {


    public void saveUserFundRecord(Long userId,String fundflow,BigDecimal fund,Integer type,Integer fundType, BigDecimal balance,Long logId);

}
