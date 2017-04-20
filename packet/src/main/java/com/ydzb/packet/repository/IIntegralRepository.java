package com.ydzb.packet.repository;

import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.packet.entity.Integral;
import com.ydzb.packet.entity.RedPacketInterest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 积分 repository
 */
@Repository
public interface IIntegralRepository extends IBaseRepository<Integral, Long> {

    /**
     * 根据Id改变积分状态
     * @param id 积分id
     * @param status 目标状态
     */
    @Modifying
    @Query(" UPDATE Integral SET status = :status WHERE id = :id ")
    public void updateStatus(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 根据状态查询所有所有积分
     * @param status
     * @return
     */
    @Query(" FROM Integral WHERE status = :status ORDER BY created DESC ")
    public List<Integral> findAll(@Param("status") Integer status);
}