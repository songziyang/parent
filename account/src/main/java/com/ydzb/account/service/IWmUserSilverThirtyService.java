package com.ydzb.account.service;

import com.ydzb.account.entity.WmUserSilverThirty;

import javax.persistence.LockModeType;

/**
 * 30亿活动用户银多币service接口
 */
public interface IWmUserSilverThirtyService {

    /**
     * 根据用户查询
     * @param userId
     * @return
     */
    WmUserSilverThirty queryByUser(Long userId, LockModeType lockType);

    /**
     * 创建
     * @param userId
     * @return
     */
    WmUserSilverThirty createOne(Long userId);

    /**
     * 更新银多币数
     * @param wmUserSilverThirty
     * @param count
     */
    void updateCoins(WmUserSilverThirty wmUserSilverThirty, Integer count);
}
