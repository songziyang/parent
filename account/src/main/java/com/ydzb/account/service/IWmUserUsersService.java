package com.ydzb.account.service;

import com.ydzb.account.entity.WmUser;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by sy on 2016/6/3.
 */
public interface IWmUserUsersService {

    /**
     * 查询所有推荐人id以及手机号
     *
     * @return
     */
    public List<Object[]> findReferralUserIdAndMobile();

    /**
     * 查询所有推荐人id以及手机号
     *
     * @return
     */
    List<Object[]> findReferralUserIdAndMobile2();

    /**
     * 根据推荐人手机号以及注册时间开始查找用户id
     *
     * @param referralUserMobile 推荐人手机号
     * @param startTime          注册时间开始
     * @return
     */
    public List<BigInteger> findUsersIdByReferralUserAndRegisterTime(String referralUserMobile, Long startTime);

    WmUser findById(Long useId);
}
