package com.ydzb.user.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.user.entity.UserBanks;
import com.ydzb.user.repository.IUserBanksRepository;
import com.ydzb.user.service.IUserBanksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户银行卡service实现
 *
 * @author sy
 */
@Service
public class UserBanksServiceImpl extends BaseServiceImpl<UserBanks, Long> implements IUserBanksService {

    @Autowired
    private IUserBanksRepository userBanksRepository;

    /**
     * 根据用户id查找用户银行卡信息
     *
     * @param userId
     * @return
     */
    @Override
    public List<UserBanks> queryUserCards(Long userId) {
        return userBanksRepository.findUserCardsByUserIdAndState(userId);
    }


    /**
     * 根据用户ID 和 状态 查询
     *
     * @param userId
     * @return
     */
    @Override
    public List<UserBanks> findUserCardsByUserIdAndState(Long userId) {
        return userBanksRepository.findUserCardsByUserIdAndState(userId);
    }

    /**
     * 根据银行卡解绑id查询用户银行卡
     * @param banksDelId 银行卡解绑id
     * @return
     */
    @Override
    public UserBanks findByBanksDel(final Long banksDelId) {
        return userBanksRepository.findByBanksDel(banksDelId);
    }

    public IUserBanksRepository getUserBanksRepository() {
        return userBanksRepository;
    }

    public void setUserBanksRepository(IUserBanksRepository userBanksRepository) {
        this.userBanksRepository = userBanksRepository;
    }
}
