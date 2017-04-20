package com.ydzb.account.service;

import com.ydzb.account.entity.WmActivityOctoberRedpacket;
import com.ydzb.account.entity.WmRagularUserAccount;
import com.ydzb.account.entity.WmRedpacketVoucher;

import java.math.BigDecimal;

/**
 * 十月红包活动service接口
 */
public interface IWmActivityOctoberRedpacketService {

    /**
     * 发放红包
     * @param userId 用户id
     * @param buyFund 购买金额
     * @param days
     * @param accountId
     * @param voucher
     */
    void sendRedpacket(Long userId, BigDecimal buyFund, Integer days, Long accountId, WmRedpacketVoucher voucher);

    /**
     * 创建
     * @param userId
     * @param buyFund
     * @param days
     * @param buyType
     * @param redpacketFund
     * @param redpacketUserId
     * @param accountId
     * @return
     */
    WmActivityOctoberRedpacket createOne(Long userId, BigDecimal buyFund, Integer days,
            Integer buyType, BigDecimal redpacketFund, Long redpacketUserId, Long accountId);

    /**
     * 创建
     * @param userId
     * @param account
     * @param redpacketFund
     * @param redpacketUserId
     * @return
     */
    WmActivityOctoberRedpacket createOne(Long userId, WmRagularUserAccount account,
            BigDecimal redpacketFund, Long redpacketUserId);
}