package com.ydzb.packet.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.packet.entity.ExpMoney;
import com.ydzb.packet.entity.RpUser;

/**
 * Created by Administrator on 15-10-26.
 */
public interface IExMoneySendService  extends IBaseService<ExpMoney, Long> {

    /**
     * 发送体验金
     * @param rpUser
     * @param expMoneyId
     * @return
     */
    public String sendMoney(RpUser rpUser, Long expMoneyId) throws  Exception;
}
