package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmSilverTradeRecordThirty;
import com.ydzb.account.entity.WmUserSilverThirty;
import com.ydzb.account.repository.WmSilverTradeRecordThirtyRepository;
import com.ydzb.account.service.IWmSilverTradeRecordThirtyService;
import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 30亿活动银多币交易记录service实现
 */
@Service
public class WmSilverTradeRecordThirtyServiceImpl implements IWmSilverTradeRecordThirtyService {

    @Autowired
    private WmSilverTradeRecordThirtyRepository wmSilverTradeRecordThirtyRepository;

    /**
     * 创建
     * @param wmUserSilverThirty
     * @param count
     * @param type
     * @return
     */
    @Override
    public WmSilverTradeRecordThirty createOne(WmUserSilverThirty wmUserSilverThirty, Long linkId, Integer count, Integer type) {
        if (wmUserSilverThirty == null) return null;
        return createOne(wmUserSilverThirty.getUserId(), linkId, count, wmUserSilverThirty.getUsableFund(), type);
    }

    /**
     * 创建
     * @param userId
     * @param linkId
     * @param fund
     * @param usableFund
     * @param type
     * @return
     */
    @Override
    public WmSilverTradeRecordThirty createOne(Long userId, Long linkId, Integer fund, Integer usableFund, Integer type) {

        if (fund.compareTo(0) == 1) {
            WmSilverTradeRecordThirty wmSilverTradeRecordThirty = new WmSilverTradeRecordThirty();
            wmSilverTradeRecordThirty.setUserId(userId);
            wmSilverTradeRecordThirty.setUsableFund(usableFund);
            wmSilverTradeRecordThirty.setCreated(DateUtil.getSystemTimeSeconds());
            wmSilverTradeRecordThirty.setFund(fund);
            wmSilverTradeRecordThirty.setType(type);
            wmSilverTradeRecordThirty.setLinkId(linkId);
            return wmSilverTradeRecordThirtyRepository.saveOrUpdate(wmSilverTradeRecordThirty);
        }
        return null;
    }

    /**
     * 查询签到用户人数
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public Integer queryLotteryUserCount(Long startTime, Long endTime) {
        return wmSilverTradeRecordThirtyRepository.queryLotteryUserCount(startTime, endTime);
    }
}