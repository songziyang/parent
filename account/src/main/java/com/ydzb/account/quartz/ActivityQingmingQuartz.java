package com.ydzb.account.quartz;

import com.ydzb.account.entity.WmActivityQingming;
import com.ydzb.account.service.IWmActivityQingmingAccountService;
import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.List;

/**
 * 清明节活动结算quartz
 */
public class ActivityQingmingQuartz {

    @Autowired
    private IWmActivityQingmingAccountService activityQingmingAccountService;

    /**
     * 结算任务
     */
    public void accountJob() {
        Long today = DateUtil.getSystemTimeDay(DateUtil.getCurrentDate());
        Long tomorrow = DateUtil.getSystemTimeDay(DateUtil.addDay(DateUtil.getCurrentDate()));
        //查询推荐人id以及手机号
        List<Object[]> referralUsers = activityQingmingAccountService.findReferralUsers();
        if (referralUsers != null) {
            for (Object[] referralUser: referralUsers) {
                if (referralUser != null) {
                    try {
                        WmActivityQingming activityQingming = activityQingmingAccountService.getActivityRecord(((BigInteger) referralUser[0]).longValue());
                        //查询推荐人推荐的新用户
                        List<BigInteger> newUsers = activityQingmingAccountService.findUsersByReferralUserAndRegisterTime((String) referralUser[1],
                                WmActivityQingming.ACITVITY_STARTTIME, WmActivityQingming.ACTIVITY_ENDTIME);
                        if (newUsers != null) {
                            for (BigInteger userId: newUsers) {
                                try {
                                    //执行结算
                                    activityQingmingAccountService.account(userId.longValue(), today, tomorrow, activityQingming);
                                } catch (Exception e) {
                                    e.printStackTrace();
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
}