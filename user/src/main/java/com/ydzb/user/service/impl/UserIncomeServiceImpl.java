package com.ydzb.user.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.user.entity.UserIncome;
import com.ydzb.user.repository.IUserIncomeRepository;
import com.ydzb.user.service.IUserIncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户收益信息service实现类
 */
@Service
public class UserIncomeServiceImpl extends BaseServiceImpl<UserIncome, Long> implements IUserIncomeService {

    @Autowired
    private IUserIncomeRepository userIncomeRepository;

    /**
     * 根据用户id查询用户收益信息
     * @param userId
     * @return
     */
    @Override
    public UserIncome queryByUser(Long userId) {
        return userIncomeRepository.queryByUser(userId);
    }
}
