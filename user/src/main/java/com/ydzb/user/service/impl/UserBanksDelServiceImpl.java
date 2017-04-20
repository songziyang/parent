package com.ydzb.user.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.user.entity.User;
import com.ydzb.user.entity.UserBanksDel;
import com.ydzb.user.repository.IUserBanksDelRepository;
import com.ydzb.user.repository.IUserBanksRepository;
import com.ydzb.user.service.IUserBanksDelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户银行解绑service实现
 */
@Service
public class UserBanksDelServiceImpl extends BaseServiceImpl<UserBanksDel, Long> implements IUserBanksDelService {

    @Autowired
    private IUserBanksDelRepository userBanksDelRepository;
    @Autowired
    private IUserBanksRepository userBanksRepository;

    /**
     * 审核操作
     * @param operation success-操作成功    failure-操作失败
     * @param reason 失败原因
     * @param banksDelId 银行卡解绑id
     * @throws Exception
     */
    @Override
    public void validate(final String operation, final String reason, final Long banksDelId) throws Exception {
        if ("success".equals(operation)) {
            userBanksDelRepository.updateStatus(banksDelId, UserBanksDel.VALIDATE_SUCCESS);
            //默认银行卡置为删除
            userBanksRepository.deleteByBanksDel(banksDelId);
        } else if ("failure".equals(operation)) {
            userBanksDelRepository.validateFailure(banksDelId, reason);
        }
    }

    /**
     * 根据银行卡解绑查找用户
     * @param id
     * @return
     */
    @Override
    public User findUserByBanksDel(final Long id) {
        return userBanksDelRepository.findUserByBanksDel(id);
    }
}