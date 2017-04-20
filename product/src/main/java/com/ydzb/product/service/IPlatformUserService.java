package com.ydzb.product.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.product.entity.PlatformUser;

import java.math.BigDecimal;

/**
 * 平台用户service接口
 */
public interface IPlatformUserService extends IBaseService<PlatformUser, Long> {

    /**
     * 查询最新的一条数据
     * @return
     */
    PlatformUser queryLastOne();

    /**
     * 充值
     * @param platformUserId 平台用户id
     * @param fund 充值金额
     * @param desc 说明
     * @return
     */
    String recharge(Long platformUserId, BigDecimal fund, String desc);

    /**
     * 购买债权
     * @param platformUserId 平台用户id
     * @param fund 债权购买金额
     * @param desc 说明
     * @return
     */
    String buyDebt(Long platformUserId, BigDecimal fund, String desc);

    /**
     * 售出债权
     * @param platformUserId 平台用户id
     * @param fund 债权售出金额
     * @param desc 说明
     * @return
     */
    String sellDebt(Long platformUserId, BigDecimal fund, String desc);
}
