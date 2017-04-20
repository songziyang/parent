package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmActivityQingming;
import com.ydzb.account.entity.WmCurrentSettlement;
import com.ydzb.account.repository.WmActivityQingmingRepository;
import com.ydzb.account.repository.WmCurrentSettlementRepository;
import com.ydzb.account.repository.WmUserUsersRepository;
import com.ydzb.account.service.IWmActivityQingmingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * 清明节活动结算service实现
 */
@Service
public class WmActivityQingmingAccountServiceImpl implements IWmActivityQingmingAccountService {

    @Autowired
    private WmUserUsersRepository userUsersRepository;
    @Autowired
    private WmCurrentSettlementRepository currentSettlementRepository;
    @Autowired
    private WmActivityQingmingRepository activityQingmingRepository;

    /**
     * 查询所有推荐人
     * @return obj[0]-id obj[1]-mobile
     */
    @Override
    public List<Object[]> findReferralUsers() {
        return userUsersRepository.findReferralUserIdAndMobile();
    }

    /**
     * 根据推荐人手机号以及注册时间段查找用户
     * @param mobile 推荐人手机号
     * @param startTime 注册时间开始
     * @param endTime 注册时间结束
     * @return
     */
    @Override
    public List<BigInteger> findUsersByReferralUserAndRegisterTime(String mobile, Long startTime, Long endTime) {
        return userUsersRepository.findUsersIdByReferralUserAndRegisterTime(mobile, startTime, endTime);
    }

    /**
     * 结算清明节活动
     * @param userId 活动id
     * @param startTime 活期宝结算开始时间
     * @param endTime 活期宝结算结束时间
     * @param activityQingming 推荐人清明节活动记录
     * @throws Exception
     */
    @Override
    public void account(Long userId, Long startTime, Long endTime, WmActivityQingming activityQingming) throws Exception {
        //查询用户活期宝结算记录
        WmCurrentSettlement settlement = currentSettlementRepository.findByUserAndTime(userId, startTime, endTime);
        if (settlement != null) {
            //真实利率 = 利率 + 日加息 + 月加息 + vip加息
            BigDecimal apr = settlement.getApr() == null? BigDecimal.ZERO: settlement.getApr();
            BigDecimal dayApr = settlement.getDaypr() == null? BigDecimal.ZERO: settlement.getDaypr();
            BigDecimal monthApr = settlement.getMonthApr() == null? BigDecimal.ZERO: settlement.getMonthApr();
            BigDecimal vipApr = settlement.getVipApr() == null? BigDecimal.ZERO: settlement.getVipApr();
            BigDecimal realApr = apr.add(dayApr).add(monthApr).add(vipApr);
            //真实投资金额 = 总金额
            BigDecimal realFund = settlement.getFund() == null? BigDecimal.ZERO: settlement.getFund();
            //活期宝收益 = 利率 * 金额 /36500
            BigDecimal currentRevenue = realApr.multiply(realFund).divide(BigDecimal.valueOf(36500), 6, BigDecimal.ROUND_HALF_DOWN);
            activityQingming.setDayloanIncome(activityQingming.getDayloanIncome() == null?
                    currentRevenue: activityQingming.getDayloanIncome().add(currentRevenue));
            activityQingmingRepository.updateWmActivityQingming(activityQingming);
        }
    }

    /**
     * 获得活动记录
     * @param userId 用户id
     * @return
     */
    @Override
    public WmActivityQingming getActivityRecord(Long userId) {
        WmActivityQingming record = activityQingmingRepository.findByUser(userId);
        if (record == null) {
            record = new WmActivityQingming();
            record.setUserId(userId);
            activityQingmingRepository.saveWmActivityQingming(record);
        }
        return record;
    }
}