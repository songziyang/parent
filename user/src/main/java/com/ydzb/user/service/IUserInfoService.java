package com.ydzb.user.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.user.entity.UserInfo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IUserInfoService extends IBaseService<UserInfo, Long> {


    public Integer findRechargePersons();

    public BigDecimal findRechargeAllFund();

    public UserInfo findByUserId(Long userId);

    /**
     * 导出到Excel
     * @param userInfoList
     * @param address
     * @return
     */
    public String exportUser(List<UserInfo> userInfoList, String address);
}
