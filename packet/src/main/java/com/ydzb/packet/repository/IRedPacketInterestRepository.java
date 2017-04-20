package com.ydzb.packet.repository;

import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.packet.entity.RedPacketInterest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 红包-加息券repository接口
 */
public interface IRedPacketInterestRepository extends IBaseRepository<RedPacketInterest, Long> {

    /**
     * 根据Id改变该加息券的状态值
     * @param id
     * @param status
     */
    @Modifying
    @Query(" UPDATE RedPacketInterest SET status = :status WHERE id = :id ")
    public void updateStatus(@Param("id") Long id, @Param("status") Byte status);

    /**
     * 根据状态查询所有所有加息券
     * @param status
     * @return
     */
    @Query(" FROM RedPacketInterest AS redpacket where redpacket.status = :status ORDER BY redpacket.created DESC ")
    public List<RedPacketInterest> findAll(@Param("status") Byte status);
}