package com.ydzb.account.service;

import com.ydzb.account.entity.WmCmsBuyQueue;

import java.math.BigDecimal;

/**
 * 用户购买队列表service接口
 */
public interface IWmCmsBuyQueueService {

    WmCmsBuyQueue createOne(Long userId, Long bId, Long eqId, BigDecimal amount, Integer type, Long cId, Integer status, String kId);
}
