package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmActivityReferUser;
import com.ydzb.account.entity.WmCurrentSettlement;
import com.ydzb.account.entity.WmUserInvestinfo;
import com.ydzb.account.repository.WmActivityReferUserRepository;
import com.ydzb.account.repository.WmCurrentSettlementRepository;
import com.ydzb.account.repository.WmUserInvestInfoRepository;
import com.ydzb.account.repository.WmUserUsersRepository;
import com.ydzb.account.service.IWmActivityReferUserAccountService;
import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by sy on 2016/6/3.
 */
@Service
public class WmActivityReferUserAccountServiceImpl implements IWmActivityReferUserAccountService {

    @Autowired
    private WmUserUsersRepository wmUserUsersRepository;
    @Autowired
    private WmActivityReferUserRepository wmActivityReferUserRepository;
    @Autowired
    private WmUserInvestInfoRepository wmUserInvestInfoRepository;
    @Autowired
    private WmCurrentSettlementRepository wmCurrentSettlementRepository;

    public static final BigDecimal FIRSTLEVEL_FROM = BigDecimal.ZERO;
    public static final BigDecimal FIRSTLEVEL_TO = BigDecimal.valueOf(10000);
    public static final BigDecimal SECONDLEVEL_TO = BigDecimal.valueOf(30000);
    public static final BigDecimal THIRDLEVEL_TO = BigDecimal.valueOf(50000);
    public static final BigDecimal FORTHLEVEL_TO = BigDecimal.valueOf(100000);

    public static final BigDecimal FIRSTLEVEL_RATIO = BigDecimal.valueOf(0.02);
    public static final BigDecimal SECONDLEVEL_RATIO = BigDecimal.valueOf(0.05);
    public static final BigDecimal THIRDLEVEL_RATIO = BigDecimal.valueOf(0.1);
    public static final BigDecimal FORTHLEVEL_RATIO = BigDecimal.valueOf(0.15);
    public static final BigDecimal FIFTHLEVEL_RATIO = BigDecimal.valueOf(0.2);


    /**
     * 结算
     * @param userId 推荐人id
     * @param referUserId 被推荐人id
     * @return
     */
    @Override
    public BigDecimal account(Long userId, Long referUserId) {
        if (userId != null) {
            Long created = wmUserUsersRepository.findCreatedByUser(referUserId);
            if (created != null) {
                Long today = DateUtil.getSystemTimeDay(DateUtil.getCurrentDate());
                //从用户注册开始计算,推荐返现收益共发放30天
                //把created转化成当日凌晨0点
                String date = new SimpleDateFormat("yyyy-MM-dd").format(DateUtil.getSystemTimeMillisecond(created));
                if (today.compareTo(DateUtil.getSystemTimeDay(date) + 30 * 24 * 3600) <= 0) {
                    WmUserInvestinfo investinfo = wmUserInvestInfoRepository.findByUser(userId);
                    BigDecimal currentIncome = queryYesterdayIncome(referUserId);
                    generateRecord(userId, referUserId, investinfo);
                    return currentIncome;
                }
            }
        }
        return BigDecimal.ZERO;
    }

    /**
     * 生成活动记录
     * @param userId 推荐人id
     * @param referUserId 被推荐人id
     * @param investinfo 推荐人投资信息
     */
    private void generateRecord(Long userId, Long referUserId, WmUserInvestinfo investinfo) {
        BigDecimal invest = getInvestExceptExpInvest(investinfo);
        WmActivityReferUser activityReferUser = new WmActivityReferUser();
        activityReferUser.setUserId(userId);
        activityReferUser.setRefereeUserId(referUserId);
        activityReferUser.setAllInvestment(invest);
        activityReferUser.setPercentage(getReferRatio(invest));
        activityReferUser.setDayloanIncome(queryYesterdayIncome(referUserId));
        activityReferUser.setCreated(DateUtil.getSystemTimeSeconds());
        activityReferUser.setOpdate(DateUtil.getSystemTimeDay(DateUtil.getCurrentDate()));
        wmActivityReferUserRepository.saveWmActivityReferUser(activityReferUser);
    }

    @Override
    public BigDecimal getInvestExceptExpInvest(WmUserInvestinfo investinfo) {
        if (investinfo == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal allInvest = investinfo == null? BigDecimal.ZERO: investinfo.getAllInvest();
        allInvest = allInvest == null? BigDecimal.ZERO: allInvest;
        BigDecimal expInvest = investinfo == null? BigDecimal.ZERO: investinfo.getAllInvestInvest();
        expInvest = expInvest == null? BigDecimal.ZERO: expInvest;
        return allInvest.subtract(expInvest);
    }

    @Override
    public BigDecimal getReferRatio(BigDecimal invest) {
        invest = invest == null? BigDecimal.ZERO: invest;
        if (invest.compareTo(FIRSTLEVEL_FROM) == 1 && invest.compareTo(FIRSTLEVEL_TO) == -1) {
            return FIRSTLEVEL_RATIO;
        }
        if (invest.compareTo(FIRSTLEVEL_TO) >= 0 && invest.compareTo(SECONDLEVEL_TO) == -1 ) {
            return SECONDLEVEL_RATIO;
        }
        if (invest.compareTo(SECONDLEVEL_TO) >= 0 && invest.compareTo(THIRDLEVEL_TO) == -1 ) {
            return THIRDLEVEL_RATIO;
        }
        if (invest.compareTo(THIRDLEVEL_TO) >= 0 && invest.compareTo(FORTHLEVEL_TO) == -1 ) {
            return FORTHLEVEL_RATIO;
        }
        if (invest.compareTo(FORTHLEVEL_TO) >= 0) {
            return FIFTHLEVEL_RATIO;
        }
        return BigDecimal.ZERO;
    }

    /**
     * 查询昨日活期宝收益
     * @param userId 用户id
     * @return
     */
    private BigDecimal queryYesterdayIncome(Long userId) {

        if(userId == null) {
            return BigDecimal.ZERO;
        }

        Long startTime = DateUtil.getSystemTimeDay(DateUtil.getCurrentDate());
        Long endTime = DateUtil.getSystemTimeDay(DateUtil.addDay(DateUtil.getCurrentDate()));

        //查询用户活期宝结算记录
        WmCurrentSettlement settlement = wmCurrentSettlementRepository.findByUserAndTime(userId, startTime, endTime);
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
            return realApr.multiply(realFund).divide(BigDecimal.valueOf(36500), 6, BigDecimal.ROUND_HALF_DOWN);
        }
        return BigDecimal.ZERO;
    }
}