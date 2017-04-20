package com.ydzb.account.service;

import com.ydzb.account.entity.WmProductInfo;
import com.ydzb.account.entity.WmRagularUserAccount;

import java.math.BigDecimal;

/**
 * 定存宝到期处理service接口
 */
public interface IWmRagularExpireService {

    /**
     * 保存定存复投交易记录
     *
     * @param userId       用户ID
     * @param fund         金额
     * @param expireNum    复投次数
     * @param expireMode   复投模式
     * @param incomeMode   利息复投模式
     * @return 复投生成的新ragularUserAccount
     */
    WmRagularUserAccount doExpire(WmProductInfo productInfo, Long userId, BigDecimal fund, Integer expireNum, Integer expireMode, Integer incomeMode) throws Exception;
}