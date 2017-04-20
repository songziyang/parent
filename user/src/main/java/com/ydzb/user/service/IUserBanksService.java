package com.ydzb.user.service;

import com.ydzb.core.service.IBaseService;
import com.ydzb.user.entity.UserBanks;

import java.util.List;

/**
 * 用户银行卡service接口
 *
 * @author sy
 */
public interface IUserBanksService extends IBaseService<UserBanks, Long> {

    /**
     * 根据用户id查找用户银行卡信息
     *
     * @param userId
     * @return
     */
    public List<UserBanks> queryUserCards(Long userId);


    /**
     * 根据用户ID 和 状态 查询
     *
     * @param userId
     * @return
     */
    public List<UserBanks> findUserCardsByUserIdAndState(Long userId);

    /**
     * 根据银行卡解绑id查询用户银行卡
     * @param banksDelId 银行卡解绑id
     * @return
     */
    public UserBanks findByBanksDel(final Long banksDelId);
}
