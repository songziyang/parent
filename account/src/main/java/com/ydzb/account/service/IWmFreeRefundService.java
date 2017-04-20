package com.ydzb.account.service;

import com.ydzb.account.entity.WmFreeRefund;

import javax.persistence.LockModeType;

/**
 * 随心存还息记录service接口
 */
public interface IWmFreeRefundService {

    /**
     * 更新状态
     * @param freeRefund 随心存还息记录
     * @param state 目标状态
     * @return
     */
    WmFreeRefund updateState(WmFreeRefund freeRefund, int state);

    /**
     * 根据主键查询
     * @param id 主键
     * @param lockModeType 锁类型
     * @return
     */
    WmFreeRefund queryById(Long id, LockModeType lockModeType);

    /**
     * 根据LinkId和锁类型查询实体
     * @param linkId 定存账户id
     * @param lockModeType 锁类型
     * @return
     */
    WmFreeRefund queryOneByLinkId(Long linkId, LockModeType lockModeType);
}