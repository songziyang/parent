package com.ydzb.account.quartz;

import com.ydzb.account.entity.WmRedPacketCash;
import com.ydzb.account.entity.WmRedPacketInterest;
import com.ydzb.account.service.IWmActivityDoubleElevenService;
import com.ydzb.account.service.IWmRagularUserAccountService;
import com.ydzb.account.service.IWmRedpacketCashService;
import com.ydzb.account.service.IWmRedpacketInterestService;
import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * 双十一活动定时任务
 */
public class ActivityDoubleElevenQuartz {

    public static final Long activityStartTime = 1478566800L;   //活动开始时间: 2016-11-08 09:00:00
    public static final Long activityEndTime = 1479463200L; //活动结束时间: 2016-11-18 18:00:00
    public static final Long rebuyStartTime = 1478534400L;  //复投开始时间: 2016-11-08 00:00:00
    @Autowired
    private IWmActivityDoubleElevenService wmActivityDoubleElevenService;
    @Autowired
    private IWmRedpacketInterestService wmRedpacketInterestService;
    @Autowired
    private IWmRagularUserAccountService wmRagularUserAccountService;
    @Autowired
    private IWmRedpacketCashService wmRedPacketInterest;

    /**
     * 发放定存排行加息券
     */
    public void sendRankingInterest() {
        List<Object[]> top50Users = wmActivityDoubleElevenService.queryTop50Users(activityStartTime, rebuyStartTime, activityEndTime);
        if (top50Users != null) {
            WmRedPacketInterest wmRedPacketInterest = wmRedpacketInterestService.queryByTriggerType(WmRedPacketInterest.TRIGGERTYPE_RAGULARRANK);
            if (wmRedPacketInterest == null) {
                wmRedPacketInterest = wmRedpacketInterestService.createOne("双十一活动排行", 2L, WmRedPacketInterest.TRIGGERTYPE_RAGULARRANK,
                        1478534400L, 1479398400L, 2, 30, BigDecimal.ZERO, null);
            }

            Object[] curUser;
            Long userId;
            for (int i = 0; i < top50Users.size(); i++) {
                try {
                    curUser = top50Users.get(i);
                    if (curUser != null && curUser.length >= 1) {
                        userId = ((BigInteger) curUser[0]).longValue();
                        wmActivityDoubleElevenService.sendInterest((i + 1), userId, wmRedPacketInterest);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 返现
     */
    public void returnCash() {

        WmRedPacketCash wmRedPacketCash = wmRedPacketInterest.findByTriggerType(WmRedPacketCash.TRIGGERTYPE_DOUBLEELEVENCASH);
        if (wmRedPacketCash == null) {
            wmRedPacketCash = wmRedPacketInterest.createOne(BigDecimal.ZERO, WmRedPacketCash.TRIGGERTYPE_DOUBLEELEVENCASH, 7,
                    1478534400L, 1479398400L, "双十一返现");
        }
        //定存宝复投部分
        try {
            List<Object[]> rebuyInfos = wmRagularUserAccountService.queryRagularInfoBetweenTime(DateUtil.getSystemTimeDay(DateUtil.getCurrentDate()),
                    DateUtil.getSystemTimeDay(DateUtil.addDay(DateUtil.getCurrentDate())));
            if (rebuyInfos != null) {
                Long userId;
                BigDecimal buyFund;
                Integer days;
                for (Object[] buyInfo: rebuyInfos) {
                    try {
                        if (buyInfo != null && buyInfo.length >= 3) {
                            userId = (Long) buyInfo[0];
                            buyFund = (BigDecimal) buyInfo[1];
                            days = (Integer) buyInfo[2];
                            wmActivityDoubleElevenService.sendCash(userId, buyFund, days, wmRedPacketCash);
                        }
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