package com.ydzb.packet.repository;

import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.packet.entity.ExpMoney;
import com.ydzb.packet.entity.RedPacketCash;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 体验金repository
 * @author sy
 */
public interface IExpMoneyRepository extends IBaseRepository<ExpMoney, Long> {

    /**
     * 根据Id改变对应状态
     * @param expMoneyId
     * @param state
     */
    @Modifying
    @Query(" UPDATE ExpMoney SET state = :state WHERE id = :expMoneyId ")
    public void updateState(@Param("expMoneyId") Long expMoneyId, @Param("state") Byte state);

    /**
     * 根据状态查询所有所有体验金
     * @param state
     * @return
     */
    @Query(" FROM ExpMoney AS expMoney where expMoney.state = :state ORDER BY expMoney.created DESC ")
    public List<ExpMoney> findAll(@Param("state") Byte state);
}