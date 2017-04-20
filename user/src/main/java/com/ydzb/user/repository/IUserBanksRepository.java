package com.ydzb.user.repository;

import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.user.entity.UserBanks;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 用户银行卡repository
 */
public interface IUserBanksRepository extends IBaseRepository<UserBanks, Long> {

    /**
     * 根据用户id查找用户银行卡信息
     *
     * @param userId
     * @return
     */
    @Query(" FROM UserBanks WHERE user.id = :userId")
    public List<UserBanks> queryUserCards(@Param("userId") Long userId);

    /**
     * 根据用户ID 和 状态 查询
     *
     * @param userId
     * @return
     */
    @Query(" FROM UserBanks WHERE user.id = :userId and state =  0 ")
    public List<UserBanks> findUserCardsByUserIdAndState(@Param("userId") Long userId);

    /**
     * 查询用户未删除的银行卡数量
     * @param userId 用户id
     * @return
     */
    @Query(" SELECT COUNT(id) FROM UserBanks WHERE user.id = :userId and state =  0 ")
    public Integer findUserCardsCount(@Param("userId") Long userId);

    /**
     * 根据用户银行卡解绑id删除用户银行卡
     *
     * @param banksDelId
     */
    @Modifying
    @Query(" UPDATE UserBanks AS banks SET banks.state = 1 WHERE banks.id = (SELECT linkId FROM UserBanksDel AS banksDel WHERE banksDel.id = :banksDelId) ")
    public void deleteByBanksDel(@Param("banksDelId") final Long banksDelId);

    /**
     * 根据银行卡解绑id查询用户银行卡
     * @param banksDelId
     * @return
     */
    @Query(" FROM UserBanks AS banks WHERE banks.id = (SELECT linkId FROM UserBanksDel AS banksDel WHERE banksDel.id = :banksDelId) ")
    public UserBanks findByBanksDel(@Param("banksDelId") final Long banksDelId);
}
