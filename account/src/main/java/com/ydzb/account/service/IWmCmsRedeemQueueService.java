package com.ydzb.account.service;

import com.ydzb.account.entity.WmCmsRedeemQueue;

import java.math.BigDecimal;

/**
 * 用户赎回队列service接口
 */
public interface IWmCmsRedeemQueueService {

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
    WmCmsRedeemQueue createOne(Long userId, Long dealId, BigDecimal amount, Integer productType, BigDecimal income,
        Integer type, Integer status, Integer warn, Long rbId, Long transferUserId) throws Exception;
}