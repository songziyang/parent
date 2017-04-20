package com.ydzb.account.quartz;

import com.ydzb.account.service.IWmActivityGuoqingService;
import com.ydzb.account.service.IWmRagularUserAccountService;
import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

/**
 * 国庆活动定时任务
 */
public class ActivityGuoqingQuartz {

    @Autowired
    private IWmActivityGuoqingService wmActivityGuoqingService;
    @Autowired
    private IWmRagularUserAccountService wmRagularUserAccountService;

    /**
     * 赠送福袋
     */
    public void grantLuckBag() {
        try {
            Long userId;
            BigDecimal fund;
            Long ragularAccountId;

            //获得今日复投定存记录
            List<Object[]> ragularTrades = wmRagularUserAccountService.queryRagularInfoBetweenTime(DateUtil.getSystemTimeDay(DateUtil.getCurrentDate()), DateUtil.getSystemTimeSeconds());
            if (ragularTrades != null) {
                for (Object[] ragularTrade: ragularTrades) {
                    if (ragularTrade != null && ragularTrade.length >= 4) {
                        try {
                            userId = (Long) ragularTrade[0];    //用户id
                            fund = (BigDecimal) ragularTrade[1];    //购买金额
                            ragularAccountId = (Long) ragularTrade[3];  //定存id
                            wmActivityGuoqingService.grantLuckBag(userId, fund,ragularAccountId);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将福袋转换成积分
     */
    public void convertToIntegral() {
        try {
            Long zhongqiuId;
            Long userId;
            Integer luckbagCount;

            //查询有剩余福袋的人
            List<Object[]> luckbagsUsers = wmActivityGuoqingService.queryOnesHavingLuckbag();
            if (luckbagsUsers != null) {
                for (Object[] luckbagsUser: luckbagsUsers) {
                    if (luckbagsUser != null && luckbagsUser.length >= 3) {
                        try {
                            zhongqiuId = (Long) luckbagsUser[0];
                            userId = (Long) luckbagsUser[1];
                            luckbagCount = (Integer) luckbagsUser[2];
                            wmActivityGuoqingService.convertToIntegral(zhongqiuId, userId, luckbagCount);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
