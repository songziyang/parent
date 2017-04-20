package com.ydzb.account.service;


import com.ydzb.account.entity.WmUserIntegral;

import javax.persistence.LockModeType;
import java.math.BigDecimal;

public interface IWmUserIntegralService {


    /**
     * 用户投资定存宝获得积分
     *
     * @param userId 用户ID
     * @param days   产品天数
     * @param fund   投资金额
     * @param linkId 外链ID
     */
    public void investObtainIntegral(Long userId, Integer days, BigDecimal fund, Long linkId);

    /**
     * 用户投资定存宝获得积分
     * @param userId 用户ID
     * @param fund   投资金额
     * @param linkId 外链ID
     */
    void investObtainIntegralNew(Long userId, BigDecimal fund, Long linkId);


    /**
     * 成为VIP获得积分
     *
     * @param userId 用户ID
     */
    public void becomeVip(Long userId);


    /**
     * 清空用户积分
     */
    public void emptyUserIntegral() throws Exception;


    /**
     * 桂花转换为积分
     *
     * @param userId   用户ID
     * @param integral 积分
     * @param linkId   外链ID
     */
    void zhongqiuObtainIntegral(Long userId, BigDecimal integral, Long linkId);

    /**
     * 根据用户查询用户积分
     * @param userId 用户id
     * @param lockType 锁类型
     * @return
     */
    WmUserIntegral queryByUser(Long userId, LockModeType lockType);

    /**
     * 重置用户积分
     * @param wmUserIntegral
     * @param totalIntegral
     * @param integral
     * @return
     */
    WmUserIntegral resetIntegral(WmUserIntegral wmUserIntegral, BigDecimal totalIntegral, BigDecimal integral);

    /**
     * 初始化
     * @param userId
     * @param totalIntegral
     * @param integral
     * @return
     */
    public WmUserIntegral init(Long userId, BigDecimal totalIntegral, BigDecimal integral);
}
