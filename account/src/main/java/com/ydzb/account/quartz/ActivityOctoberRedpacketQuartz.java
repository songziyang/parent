package com.ydzb.account.quartz;

import com.ydzb.account.entity.WmRedpacketVoucher;
import com.ydzb.account.service.IWmActivityOctoberRedpacketService;
import com.ydzb.account.service.IWmRagularUserAccountService;
import com.ydzb.account.service.IWmRedpacketVoucherService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * 十月红包活动定时任务
 */
public class ActivityOctoberRedpacketQuartz {

    @Autowired
    private IWmActivityOctoberRedpacketService activityOctoberRedpacketService;
    @Autowired
    private IWmRagularUserAccountService wmRagularUserAccountService;
    @Autowired
    private IWmRedpacketVoucherService wmRedpacketVoucherService;

    public Logger logger = Logger.getLogger(ActivityOctoberRedpacketQuartz.class);

    /**
     * 发放红包
     */
    public void sendRedpacket() {
        logger.info("**********<定时任务>十月红包活动-开始**********");
        Long activityStartTime = 1476925200L;   //活动开始时间-2016年10月20日9时
        Long activityEndTime = 1477821600L; //活动结束时间-2016年10月30日18时

        Long userId;
        BigDecimal buyFund;
        Integer days;
        Long accountId;
        //查询活动红包
        WmRedpacketVoucher voucher = wmRedpacketVoucherService.queryByTriggerType(WmRedpacketVoucher.TRIGGERTYPE_OCTREDPACKET);
        if (voucher == null) {
            voucher = wmRedpacketVoucherService.createOne(BigDecimal.ZERO, 10000, "金秋十月活动", 60, 30, activityStartTime, activityEndTime);
        }
        //查询购买的基本信息,天数等
        List<Object[]> ragularInfos = wmRagularUserAccountService.queryWithoutVoucherAndNotTransfer(activityStartTime, activityEndTime);
        if (ragularInfos != null) {
            for (Object[] ragularInfo: ragularInfos) {
                if (ragularInfo != null && ragularInfo.length >= 3) {
                    try {
                        userId = ragularInfo[0] == null? null: ((BigInteger) ragularInfo[0]).longValue();
                        buyFund = ragularInfo[1] == null? BigDecimal.ZERO: (BigDecimal) ragularInfo[1];
                        days = ragularInfo[2] == null? 0: (Integer) ragularInfo[2];
                        accountId = ragularInfo[3] == null? null: ((BigInteger) ragularInfo[3]).longValue();
                        activityOctoberRedpacketService.sendRedpacket(userId, buyFund, days, accountId, voucher);
                    } catch (Exception e) {
                        logger.info("**********<定时任务>十月红包活动->发放红包抛出异常**********");
                    }
                }
            }
        }

        //20号复投部分
        //查询购买的基本信息,天数等
        List<Object[]> rebuys = wmRagularUserAccountService.queryRebuy(1476892800L, 1476979200L);
        if (rebuys != null) {
            for (Object[] ragularInfo: rebuys) {
                if (ragularInfo != null && ragularInfo.length >= 3) {
                    try {
                        userId = ragularInfo[0] == null? null: ((BigInteger) ragularInfo[0]).longValue();
                        buyFund = ragularInfo[1] == null? BigDecimal.ZERO: (BigDecimal) ragularInfo[1];
                        days = ragularInfo[2] == null? 0: (Integer) ragularInfo[2];
                        accountId = ragularInfo[3] == null? null: ((BigInteger) ragularInfo[3]).longValue();
                        activityOctoberRedpacketService.sendRedpacket(userId, buyFund, days, accountId, voucher);
                    } catch (Exception e) {
                        logger.info("**********<定时任务>十月红包活动->发放红包抛出异常**********");
                    }
                }
            }
        }

        logger.info("**********<定时任务>十月红包活动->结束**********");
    }
}