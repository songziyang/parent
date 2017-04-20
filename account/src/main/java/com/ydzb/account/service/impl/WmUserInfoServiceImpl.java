package com.ydzb.account.service.impl;

import com.ydzb.account.repository.WmUserInfoRepository;
import com.ydzb.account.service.IWmUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

/**
 * Created by sy on 2016/6/28.
 */
@Service
public class WmUserInfoServiceImpl implements IWmUserInfoService {

    @Autowired
    private WmUserInfoRepository wmUserInfoRepository;

    @Override
    public BigInteger queryDailySignNumber(Long startTime, Long endTime) {
        return wmUserInfoRepository.queryDailySignCount(startTime, endTime);
    }
}