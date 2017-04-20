package com.ydzb.user.service.impl;

import com.ydzb.core.service.impl.BaseServiceImpl;
import com.ydzb.user.entity.UserInvestinfo;
import com.ydzb.user.repository.IUserInvestinfoRepository;
import com.ydzb.user.service.IUserInvestinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInvestinfoServiceImpl extends BaseServiceImpl<UserInvestinfo, Long> implements IUserInvestinfoService {

    @Autowired
    private IUserInvestinfoRepository investinfoRepository;

    @Override
    public UserInvestinfo queryByUser(Long userId) {
        return investinfoRepository.queryByUser(userId);
    }
}
