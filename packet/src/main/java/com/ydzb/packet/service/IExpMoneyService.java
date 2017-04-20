package com.ydzb.packet.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.packet.entity.ExpMoney;
import com.ydzb.packet.entity.RedPacketCash;

import java.util.List;

/**
 * 体验金service接口
 */
public interface IExpMoneyService extends IBaseService<ExpMoney, Long> {

    /**
     * 保存
     * @param expMoney
     * @return
     */
    public String saveOne(ExpMoney expMoney);

    /**
     * 根据id改变对应状态
     * @param id
     * @param state
     */
    public void updateState(Long id, byte state);

    /**
     * 停用
     * @param id
     */
    public void disable(Long id);

    /**
     * 开启
     * @param id
     */
    public void enable(Long id);

    /**
     * 根据状态查询体验金
     * @param state
     * @return
     */
    public List<ExpMoney> findAll(byte state);
}