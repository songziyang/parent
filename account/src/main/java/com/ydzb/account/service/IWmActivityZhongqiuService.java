package com.ydzb.account.service;


import com.ydzb.account.context.IDRange;

import java.math.BigDecimal;

public interface IWmActivityZhongqiuService {


    /**
     * 活动桂花
     *
     * @param userId 用户ID
     * @param days   天数
     * @param fund   金额
     * @param linkId 链接ID
     */
    void investObtainFund(Long userId, Integer days, BigDecimal fund, Long linkId);


    /**
     * 桂花活动最大ID和最小ID
     *
     * @return
     */
    IDRange findMaxIdAndMinId();


    /**
     * 桂花兑换积分
     *
     * @param id 桂花
     * @throws Exception
     */
    void sendIntegral(Long id) throws Exception;
}
