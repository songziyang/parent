package com.ydzb.account.service;

import com.ydzb.account.entity.WmUserExMoney;

import javax.persistence.LockModeType;
import java.math.BigDecimal;

/**
 * 用户体验金service接口
 */
public interface IWmUserExpmoneyService {

    /**
     * 更新资金
     * @param wmUserExMoney
     * @param fund
     * @return
     */
    WmUserExMoney updateAsset(WmUserExMoney wmUserExMoney, BigDecimal fund);

    /**
     * 查询
     * @param userId
     * @param lockType
     * @return
     */
    WmUserExMoney queryOne(Long userId, LockModeType lockType);
}
