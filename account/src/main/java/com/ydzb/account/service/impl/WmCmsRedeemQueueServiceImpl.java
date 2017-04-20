package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmCmsRedeemQueue;
import com.ydzb.account.repository.WmCmsRedeemQueueRepository;
import com.ydzb.account.service.IWmCmsRedeemQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 用户赎回队列service实现
 */
@Service
public class WmCmsRedeemQueueServiceImpl implements IWmCmsRedeemQueueService {

    @Autowired
    private WmCmsRedeemQueueRepository wmCmsRedeemQueueRepository;

    /**
     * 创建
     * @param userId 用户ID
     * @param dealId 交易ID
     * @param amount 本金金额
     * @param productType 产品类型 1 活期 2定存 3随心存
     * @param income 收益金额
     * @param type 类型
     * @param status 状态
     * @param warn 错误警告状态
     * @param rbId 冻结ID
     * @param transferUserId 转让接收人ID
     * @return
     */
    @Override
    public WmCmsRedeemQueue createOne(Long userId, Long dealId, BigDecimal amount, Integer productType, BigDecimal income,
            Integer type, Integer status, Integer warn, Long rbId, Long transferUserId) throws Exception {
        WmCmsRedeemQueue queue = new WmCmsRedeemQueue();
        queue.setUserId(userId);
        queue.setDealId(dealId);
        queue.setAmount(amount);
        queue.setProductType(productType);
        queue.setIncome(income);
        queue.setType(type);
        queue.setStatus(status);
        queue.setWarn(warn);
        queue.setRbId(rbId);
        queue.setTransferUserId(transferUserId);
        return wmCmsRedeemQueueRepository.createOrUpdate(queue);
    }
}
