package com.ydzb.account.quartz;

import com.ydzb.account.entity.WmRedPacketCash;
import com.ydzb.account.entity.WmUserInvestinfo;
import com.ydzb.account.service.*;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.sms.service.ISmsHandleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;

/**
 * Created by sy on 2016/6/3.
 */
public class ActivityReferUserQuartz {

    private static final Calendar ACTIVITY_CALENDAR;

    static {
        ACTIVITY_CALENDAR = Calendar.getInstance();
        ACTIVITY_CALENDAR.set(2016, Calendar.JUNE, 16, 9, 0);
    }

    @Autowired
    private IWmUserUsersService wmUserUsersService;
    @Autowired
    private IWmActivityReferUserAccountService wmActivityReferUserAccountService;
    @Autowired
    private IWmRedpacketCashService wmRedpacketCashService;
    @Autowired
    private IWmUserRedPacketService wmUserRedPacketService;
    @Autowired
    private IWmUserInvestInfoService wmUserInvestInfoService;
    @Autowired
    private ISmsHandleService smsHandleService;

    /**
     * 结算任务
     */
    public void accountJob() {
        //查询推荐人id以及手机号
        List<Object[]> referralUsers = wmUserUsersService.findReferralUserIdAndMobile2();
        if (referralUsers != null) {
            for (Object[] referralUser: referralUsers) {
                try {
                    if (referralUser != null) {
                        String referralUserMobile = (String) referralUser[1];
                        Long referralUserId = referralUser[0] == null? 0L: ((BigInteger)referralUser[0]).longValue();
                        BigDecimal totalYesterdayIncome = BigDecimal.ZERO;
                        List<BigInteger> newUsers = wmUserUsersService.findUsersIdByReferralUserAndRegisterTime(referralUserMobile, ACTIVITY_CALENDAR.getTimeInMillis() / 1000);
                        if (newUsers != null) {
                            for (BigInteger userId: newUsers) {
                                try {
                                    if(userId != null) {
                                        //执行结算
                                        BigDecimal income = wmActivityReferUserAccountService.account(referralUserId, userId.longValue());
                                        //累计推荐人收益
                                        totalYesterdayIncome = totalYesterdayIncome.add(income == null? BigDecimal.ZERO: income);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        totalYesterdayIncome = totalYesterdayIncome.setScale(1, BigDecimal.ROUND_DOWN);
                        //如果推荐人活期总收益>0
                        if (totalYesterdayIncome.compareTo(BigDecimal.ZERO) == 1) {
                            WmUserInvestinfo userInvestinfo = wmUserInvestInfoService.findByUser(referralUserId);
                            BigDecimal invest = wmActivityReferUserAccountService.getInvestExceptExpInvest(userInvestinfo);
                            BigDecimal ratio = wmActivityReferUserAccountService.getReferRatio(invest);
                            //红包值
                            BigDecimal cashValue = ratio.multiply(totalYesterdayIncome).setScale(1, BigDecimal.ROUND_DOWN);
                            if (cashValue.compareTo(BigDecimal.ZERO) == 1) {
                                WmRedPacketCash redPacketCash = wmRedpacketCashService.findByTriggerType(30);
                                if (redPacketCash == null) {
                                    Long activityDay = DateUtil.getSystemTimeDay("2016-06-16");
                                    redPacketCash = wmRedpacketCashService.createOne(BigDecimal.ZERO, 30, 7,
                                            activityDay, null, "银多4.0推荐活动返现");
                                }
                                if (redPacketCash != null) {
                                    //发放红包
                                    wmUserRedPacketService.createOne(referralUserId, null, redPacketCash.getId(), redPacketCash.getName(),
                                            0, 1, 30, redPacketCash.getBeginTime(), redPacketCash.getFinishTime(), redPacketCash.getUseDays(), null,
                                            cashValue);
                                    //站内信
                                    smsHandleService.addSiteContent("activity_referuser",
                                            referralUserId, "银多4.0推荐用户活期收益现金红包", "fund:" + ratio.multiply(totalYesterdayIncome).setScale(1, BigDecimal.ROUND_DOWN), 0);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}