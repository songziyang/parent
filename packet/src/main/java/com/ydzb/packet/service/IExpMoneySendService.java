package com.ydzb.packet.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.packet.entity.ExpMoney;
import com.ydzb.packet.entity.RpUser;

/**
 * 体验金发送service接口
 */
public interface IExpMoneySendService extends IBaseService<ExpMoney, Long> {

    /**
     * 发送体验金
     * @param rpUser
     * @param expMoneyId
     * @return
     */
    public String sendExpmoney(RpUser rpUser, Long expMoneyId);
}
