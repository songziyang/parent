package com.ydzb.packet.repository;

import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.packet.entity.SilverExchange;
import com.ydzb.packet.entity.SilverUser;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 * 银币活动
 */
public interface ISilverExchangeRepository extends IBaseRepository<SilverExchange, Long> {

    /**
     * 根据用户id、活动类型、原状态修改现状态以及修改日期
     * @param userId
     * @param targetStatus
     * @param sended
     * @param sourceStatus
     * @param type
     */
    @Modifying
    @Query(" UPDATE SilverExchange SET status = :targetStatus, sended = :sended WHERE user.id = :userId AND status = :sourceStatus AND type = :type")
    public void updateStatus(@Param("userId") Long userId, @Param("targetStatus") Integer targetStatus, @Param("sended") Long sended, @Param("sourceStatus") Integer sourceStatus, @Param("type") Integer type);

    /**
     * 根据兑换id修改日期以及状态
     * @param id
     * @param status
     * @param sended
     */
    @Modifying
    @Query(" UPDATE SilverExchange SET status = :status, sended = :sended WHERE id = :id")
    public void updateStatus(@Param("id") Long id, @Param("status") Integer status, @Param("sended") Long sended);
}
