package com.ydzb.packet.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.packet.entity.RpUser;
import com.ydzb.user.entity.UserIntegral;

/**
 * Created by sy on 2016/6/6.
 */
public interface IUserIntegralService extends IBaseService<UserIntegral, Long> {

    /**
     * 发送现金红包
     * @param rpUser
     * @param integralId
     * @return
     */
    String sendIntegral(RpUser rpUser, Long integralId) throws Exception ;
}
