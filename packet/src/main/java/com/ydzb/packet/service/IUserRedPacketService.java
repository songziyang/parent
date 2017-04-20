package com.ydzb.packet.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.packet.entity.RpUser;
import com.ydzb.packet.entity.UserRedPacket;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * 用户红包service接口
 * @author sy
 */
public interface IUserRedPacketService extends IBaseService<UserRedPacket, Long> {

    /**
     * 查询要发送红包的用户数量
     * @param rpUser 前台传递的用户条件
     * @return
     */
    public BigInteger querySendUserCount(RpUser rpUser);

    /**
     * 发送加息券红包
     * @param rpUser
     * @param redpacketId
     * @return
     */
    public String sendRedpacketInterest(RpUser rpUser, Long redpacketId);

    /**
     * 发送现金红包
     * @param rpUser
     * @param redpacketId
     * @return
     */
    public String sendRedpacketCash(RpUser rpUser, Long redpacketId);

    /**
     * 发送代金券
     * @param rpUser
     * @param redpacketId
     * @return
     */
    public String sendRedpacketVoucher(RpUser rpUser, Long redpacketId);

    /**
     * 查询导出excel数据
     * @param filter
     * @return
     */
    public List<Object[]> findExportData(Map<String, Object> filter);

    /**
     * 导出excel数据
     * @param data
     * @param address
     * @return
     */
    public String exportExcel(List<Object[]> data, String address);
}