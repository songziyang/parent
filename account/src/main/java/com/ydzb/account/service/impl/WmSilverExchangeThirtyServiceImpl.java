package com.ydzb.account.service.impl;

import com.ydzb.account.repository.WmSilverExchangeThirtyRepository;
import com.ydzb.account.service.IWmSilverExchangeThirtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 30亿活动兑换service实现
 */
@Service
public class WmSilverExchangeThirtyServiceImpl implements IWmSilverExchangeThirtyService {

    @Autowired
    private WmSilverExchangeThirtyRepository wmSilverExchangeThirtyRepository;

    /**
     * 查询兑换所有产品的人数
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public Integer queryAllExchangeUserCount(Long startTime, Long endTime) {
        return wmSilverExchangeThirtyRepository.queryAllExchangeUserCount(startTime, endTime);
    }

    /**
     * 查询兑换实物产品的人数
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public Integer queryGoodsExchangeUserCount(Long startTime, Long endTime) {
        return wmSilverExchangeThirtyRepository.queryGoodsExchangeUserCount(startTime, endTime);
    }
}