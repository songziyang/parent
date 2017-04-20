package com.ydzb.account.service;

import com.ydzb.account.entity.WmUserIntegralRecord;

import java.math.BigDecimal;

/**
 * 用户积分记录接口
 */
public interface IWmUserIntegralRecordService {

    WmUserIntegralRecord createOne(Long userId, String fundflow, BigDecimal integral,
            BigDecimal balance, Integer type, Integer linkType, Long linkId);
}