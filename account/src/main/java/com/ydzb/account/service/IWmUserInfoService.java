package com.ydzb.account.service;

import java.math.BigInteger;

/**
 * Created by sy on 2016/6/29.
 */
public interface IWmUserInfoService {

    BigInteger queryDailySignNumber(Long startTime, Long endTime);
}