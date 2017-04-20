package com.ydzb.account.service;

import com.ydzb.account.entity.WmActivityQingming;

import java.math.BigInteger;
import java.util.List;

/**
 * 清明节活动结算service接口
 */
public interface IWmActivityQingmingAccountService {

    /**
     * 查询所有推荐人
     * @return
     */
    public List<Object[]> findReferralUsers();

    /**
     * 结算清明节活动
     * @param userId 活动id
     * @param startTime 活期宝结算开始时间
     * @param endTime 活期宝结算结束时间
     * @param activityQingming 推荐人清明节活动记录
     * @throws Exception
     */
    public void account(Long userId, Long startTime, Long endTime, WmActivityQingming activityQingming) throws Exception ;

    /**
     * 获得活动记录
     * @param userId 用户id
     * @return
     */
    public WmActivityQingming getActivityRecord(Long userId);

    /**
     * 根据推荐人手机号以及注册时间段查找用户
     * @param mobile 推荐人手机号
     * @param startTime 注册时间开始
     * @param endTime 注册时间结束
     * @return
     */
    public List<BigInteger> findUsersByReferralUserAndRegisterTime(String mobile, Long startTime, Long endTime);
}