package com.ydzb.user.repository;

import com.ydzb.core.repository.IBaseRepository;
import com.ydzb.user.entity.User;
import com.ydzb.user.entity.UserBanksDel;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 用户银行解绑repository
 */
public interface IUserBanksDelRepository extends IBaseRepository<UserBanksDel, Long> {

    /**
     * 更新状态
     * @param id 银行卡解绑id
     * @param status 目标状态
     * @throws Exception
     */
    @Modifying
    @Query(" UPDATE UserBanksDel SET status = :status WHERE id = :id ")
    public void updateStatus(@Param("id") final Long id, @Param("status") final Integer status) throws Exception;

    /**
     * 审核失败
     * @param id 银行卡解绑id
     * @param reason 失败原因
     * @throws Exception
     */
    @Modifying
    @Query(" UPDATE UserBanksDel SET status = " + UserBanksDel.VALIDATE_FAILURE + ", reason = :reason WHERE id = :id")
    public void validateFailure(@Param("id") final Long id, @Param("reason") final String reason) throws Exception;

    /**
     * 根据银行卡解绑查询用户
     * @param id 银行卡解绑id
     */
    @Query(" SELECT banksDel.user FROM UserBanksDel AS banksDel WHERE banksDel.id = :id ")
    public User findUserByBanksDel(@Param("id") final Long id);
}