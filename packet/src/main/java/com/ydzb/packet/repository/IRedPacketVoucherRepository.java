package com.ydzb.packet.repository;

import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.packet.entity.RedPacketInterest;
import com.ydzb.packet.entity.RedPacketVoucher;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 红包-代金券repository
 */
public interface IRedPacketVoucherRepository extends IBaseRepository<RedPacketVoucher, Long> {

    /**
     * 根据Id改变该代金券的状态值
     * @param id
     * @param status
     */
    @Modifying
    @Query(" UPDATE RedPacketVoucher SET status = :status WHERE id = :id ")
    public void updateStatus(@Param("id") Long id, @Param("status") Byte status);

    /**
     * 根据状态查询所有所有代金券
     * @param status
     * @return
     */
    @Query(" FROM RedPacketVoucher AS voucher where voucher.status = :status ORDER BY voucher.created DESC ")
    public List<RedPacketVoucher> findAll(@Param("status") Byte status);
}
