package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmCmsBuyQueue;
import com.ydzb.account.repository.WmCmsBuyQueueRepository;
import com.ydzb.account.service.IWmCmsBuyQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 用户购买队列表service实现
 */
@Service
public class WmCmsBuyQueueServiceImpl implements IWmCmsBuyQueueService {

    @Autowired
    private WmCmsBuyQueueRepository wmCmsBuyQueueRepository;

    @Override
    public WmCmsBuyQueue createOne(Long userId, Long bId, Long eqId, BigDecimal amount, Integer type, Long cId, Integer status, String kId) {
        WmCmsBuyQueue buyQueue = new WmCmsBuyQueue();
        buyQueue.setUserId(userId);
        buyQueue.setBid(bId);
        buyQueue.setEqId(eqId);
        buyQueue.setAmount(amount);
        buyQueue.setType(type);
        buyQueue.setcId(cId);
        buyQueue.setStatus(status);
        buyQueue.setkId(kId);
        return wmCmsBuyQueueRepository.createOrUpdate(buyQueue);
    }
}