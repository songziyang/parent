package com.ydzb.account.service;

import com.ydzb.account.entity.WmSilverTradeRecordThirty;
import com.ydzb.account.entity.WmUserSilverThirty;

/**
 * 30亿活动银多币记录service接口
 */
public interface IWmSilverTradeRecordThirtyService {

    WmSilverTradeRecordThirty createOne(WmUserSilverThirty wmUserSilverThirty, Long linkId, Integer count, Integer type);

    WmSilverTradeRecordThirty createOne(Long userId, Long linkId, Integer fund, Integer usableFund, Integer type);

    Integer queryLotteryUserCount(Long startTime, Long endTime);
}
