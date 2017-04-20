package com.ydzb.packet.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.packet.entity.RedPacketInterest;

import java.util.List;

/**
 * 红包-加息券service接口
 * @author sy
 */
public interface IRedPacketInterestService extends IBaseService<RedPacketInterest, Long> {

    /**
     * 保存
     * @param interest
     * @param aBeginTime
     * @param aFinishTime
     * @return
     */
    public String saveOne(RedPacketInterest interest, String aBeginTime, String aFinishTime);

    /**
     * 停用
     * @param id
     */
    public void disable(Long id);

    /**
     * 启用
     * @param id
     */
    public void enable(Long id);

    /**
     * 根据id改变该加息券的状态值
     * @param id
     * @param status
     */
    public void updateStatus(Long id, byte status);

    /**
     * 根据状态查询加息券
     * @param status
     * @return
     */
    public List<RedPacketInterest> findAll(byte status);
}
