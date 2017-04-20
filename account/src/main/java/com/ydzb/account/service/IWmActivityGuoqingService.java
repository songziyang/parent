package com.ydzb.account.service;

import com.ydzb.account.entity.WmActivityGuoqing;

import javax.persistence.LockModeType;
import java.math.BigDecimal;
import java.util.List;

/**
 * 国庆活动service接口
 */
public interface IWmActivityGuoqingService {

    /**
     * 赠送福袋
     * @param userId 用户id
     * @param fund 复投定存金额
     * @param ragularAccountId 定存产品记录id
     */
    void grantLuckBag(Long userId, BigDecimal fund, Long ragularAccountId) throws Exception;

    /**
     * 根据用户查询国庆活动信息
     * @param userId 用户id
     * @param lockType 锁类型
     * @return
     */
    WmActivityGuoqing queryByUser(Long userId, LockModeType lockType);

    /**
     * 查询有福包的用户国庆信息
     * @return
     */
    List<Object[]> queryOnesHavingLuckbag();

    /**
     * 转换为积分
     * @param zhongqiuId 中秋活动id
     * @param userId 用户id
     * @param luckbagCount 福包数量
     */
    void convertToIntegral(Long zhongqiuId, Long userId, Integer luckbagCount);
}