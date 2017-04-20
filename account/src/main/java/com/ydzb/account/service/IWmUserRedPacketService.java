package com.ydzb.account.service;


import com.ydzb.account.entity.WmUserRedPacket;

import java.math.BigDecimal;
import java.util.List;

public interface IWmUserRedPacketService {

    /**
     * 根据用户 结算未投资的红包
     *
     * @param userId 用户ID
     * @throws Exception
     */
    public void accountUsedUserRedPacket(Long userId) throws Exception;


    /**
     * 根据用户 结算投资的红包
     *
     * @param userId 用户ID
     * @throws Exception
     */
    public void accountInvestedUserRedPacket(Long userId) throws Exception;

    public WmUserRedPacket createOne(Long userId, Long productTradeId, Long redpacketId, String redpacketName,
            Integer productType, Integer redpacketType, Integer triggerType, Long beginTime, Long finishTime,
            Integer useDays, Integer investDays, BigDecimal giveValue);

    /**
     * 根据到期时间查询红包信息
     * @param finishTime
     * @return
     */
    List<Object[]> queryRedpacketInfoByFinishTime(Long finishTime);
}
