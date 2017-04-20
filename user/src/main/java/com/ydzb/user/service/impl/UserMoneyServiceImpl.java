package com.ydzb.user.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.user.entity.UserMoney;
import com.ydzb.user.repository.IUserMoneyRepository;
import com.ydzb.user.service.IUserMoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserMoneyServiceImpl extends BaseServiceImpl<UserMoney, Long> implements IUserMoneyService {

    @Autowired
    private IUserMoneyRepository userMoneyRepository;

    public IUserMoneyRepository getUserMoneyRepository() {
        return userMoneyRepository;
    }

    public void setUserMoneyRepository(IUserMoneyRepository userMoneyRepository) {
        this.userMoneyRepository = userMoneyRepository;
    }
}
