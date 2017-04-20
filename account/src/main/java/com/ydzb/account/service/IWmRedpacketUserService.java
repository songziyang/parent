package com.ydzb.account.service;

import com.ydzb.account.entity.WmRedPacketCash;
import com.ydzb.account.entity.WmRedPacketInterest;
import com.ydzb.account.entity.WmRedpacketUser;
import com.ydzb.account.entity.WmRedpacketVoucher;

import java.math.BigDecimal;

/**
 * 用户红包service接口
 */
public interface IWmRedpacketUserService {

    /**
     * 发送定存红包
     * @param userId 用户id
     * @param fund 红包基恩
     * @param voucher 红包
     * @param triggerType 触发类型
     * @param limitFund 满多少可用
     * @return
     */
    WmRedpacketUser sendVoucher(Long userId, BigDecimal fund, WmRedpacketVoucher voucher, int triggerType, int limitFund);

    /**
     * 发送加息券
     * @param userId 用户Id
     * @param productType 产品类型
     * @param giveValue 赠送值
     * @param interest 红包
     * @param triggerType 触发类型
     * @return
     */
    WmRedpacketUser sendInterest(Long userId, Integer productType, BigDecimal giveValue, WmRedPacketInterest interest, int triggerType);

    /**
     * 创建
     * @param userId 用户id
     * @param linkId 红包id
     * @param redpacketName 红包名称
     * @param productType 产品类型
     * @param redpacketType 红包类型
     * @param triggerType 触发类型
     * @param beginTime 开始时间
     * @param finishTime 结束时间
     * @param useDays 使用天数
     * @param value 红包值
     * @param limitFund 满多少可使用(如不需则填0)
     * @return
     */
    WmRedpacketUser createOne(Long userId, Long linkId, String redpacketName, Integer productType, Integer redpacketType,
        Integer triggerType, Long beginTime, Long finishTime, Integer useDays, BigDecimal value, Integer limitFund);

    /**
     * 发放现金红包
     * @param userId 用户Id
     * @param cash 红包
     * @param fund 红包金额
     * @param triggerType 出发类型
     * @return
     */
    WmRedpacketUser sendCash(Long userId, WmRedPacketCash cash, BigDecimal fund, int triggerType);

    /**
     * 根据用户id，账户id（例如ragularAccountId)和产品类别查询所使用的红包id
     * @param userId 用户id
     * @param accountId 账户id
     * @param productType 产品类别
     */
    Long queryRedpacketId(Long userId, Long accountId, Integer productType);
}