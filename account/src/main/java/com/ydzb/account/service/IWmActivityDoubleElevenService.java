package com.ydzb.account.service;

import com.ydzb.account.entity.WmRedPacketCash;
import com.ydzb.account.entity.WmRedPacketInterest;

import java.math.BigDecimal;
import java.util.List;

/**
 * 双十一活动service接口
 */
public interface IWmActivityDoubleElevenService {

    /**
     * 查询投资榜前50的用户
     * 类别包括:
     *  定存宝(不是购买债权转让得)
     *  定存宝复投(8号凌晨也算)
     *  转转赚
     *  随心存
     * @param normalStartTime 正常活动开始时间
     * @param rebuyStartTime 复投开始时间
     * @param endTime 活动结束时间
     * @return
     */
    List<Object[]> queryTop50Users(Long normalStartTime, Long rebuyStartTime, Long endTime);

    /**
     * 发放加息券红包
     * @param rank
     * @param userId
     * @param interest
     */
    void sendInterest(int rank, Long userId, WmRedPacketInterest interest);

    void sendCash(Long userId, BigDecimal buyFund, Integer days, WmRedPacketCash cash);
}
