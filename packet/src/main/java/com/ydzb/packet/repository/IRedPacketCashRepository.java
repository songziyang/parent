package com.ydzb.packet.repository;

import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.packet.entity.RedPacketCash;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 红包现金repository
 */
public interface IRedPacketCashRepository extends IBaseRepository<RedPacketCash, Long> {
    /**
     * 根据Id改变该现金红包的状态值
     * @param id
     * @param status
     */
    @Modifying
    @Query(" UPDATE RedPacketCash SET status = :status WHERE id = :id ")
    public void updateStatus(@Param("id") Long id, @Param("status") Byte status);

    /**
     * 根据状态查询所有所有现金红包
     * @param status
     * @return
     */
    @Query(" FROM RedPacketCash AS redpacket where redpacket.status = :status ORDER BY redpacket.created DESC ")
    public List<RedPacketCash> findAll(@Param("status") Byte status);
}
