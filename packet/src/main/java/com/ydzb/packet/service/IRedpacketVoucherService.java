package com.ydzb.packet.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.packet.entity.RedPacketVoucher;

import java.util.List;

/**
 * 红包-代金券service接口
 */
public interface IRedpacketVoucherService extends IBaseService<RedPacketVoucher, Long> {

    /**
     * 保存
     *
     * @param voucher
     * @param aBeginTime
     * @param aFinishTime
     * @return
     */
    public String saveOne(RedPacketVoucher voucher, String aBeginTime, String aFinishTime, Integer[] productDaysArr);

    /**
     * 停用
     *
     * @param id
     */
    public void disable(Long id);

    /**
     * 启用
     *
     * @param id
     */
    public void enable(Long id);

    /**
     * 根据id改变该加息券的状态值
     *
     * @param id
     * @param status
     */
    public void updateStatus(Long id, byte status);

    /**
     * 根据状态查询加息券
     *
     * @param status
     * @return
     */
    public List<RedPacketVoucher> findAll(byte status);
}
