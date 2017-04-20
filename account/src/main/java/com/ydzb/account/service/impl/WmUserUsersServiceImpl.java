package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmUser;
import com.ydzb.account.repository.WmUserUsersRepository;
import com.ydzb.account.service.IWmUserUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by sy on 2016/6/3.
 */
@Service
public class WmUserUsersServiceImpl implements IWmUserUsersService {

    @Autowired
    private WmUserUsersRepository wmUserUsersRepository;

    /**
     * 查询所有推荐人id以及手机号
     * 根据用户推荐人数>0进行判断
     * @return
     */
    @Override
    public List<Object[]> findReferralUserIdAndMobile() {
        return wmUserUsersRepository.findReferralUserIdAndMobile();
    }

    /**
     * 查询所有推荐人id以及手机号
     * 根据推荐人手机号进行判断
     * @return
     */
    @Override
    public List<Object[]> findReferralUserIdAndMobile2() {
        return wmUserUsersRepository.findReferralUserIdAndMobile2();
    }

    /**
     * 根据推荐人手机号以及注册时间开始查找用户id
     *
     * @param referralUserMobile 推荐人手机号
     * @param startTime          注册时间开始
     * @return
     */
    @Override
    public List<BigInteger> findUsersIdByReferralUserAndRegisterTime(String referralUserMobile, Long startTime) {
        return wmUserUsersRepository.findUsersIdByReferralUserAndRegisterTime(referralUserMobile, startTime);
    }

    @Override
    public WmUser findById(Long userId) {
       return wmUserUsersRepository.findByUserId(userId);
    }
}