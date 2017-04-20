package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmActivityEleven;
import com.ydzb.account.entity.WmRedPacketCash;
import com.ydzb.account.entity.WmRedPacketInterest;
import com.ydzb.account.repository.WmActivityDoubleElevenRepository;
import com.ydzb.account.service.IWmActivityDoubleElevenService;
import com.ydzb.account.service.IWmRedpacketUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 双十一活动service实现
 */
@Service
public class WmActivityDoubleElevenServiceImpl implements IWmActivityDoubleElevenService {

    public static final BigDecimal PACKETLEVEL = new BigDecimal(200000);   //红包级别分界线
    public static final BigDecimal RATIO_A = new BigDecimal(0.002);
    public static final BigDecimal RATIO_B = new BigDecimal(0.0025);
    public static final BigDecimal RATIO_C = new BigDecimal(0.003);
    public static final BigDecimal RATIO_D = new BigDecimal(0.004);
    @Autowired
    private WmActivityDoubleElevenRepository wmActivityDoubleElevenRepository;
    @Autowired
    private IWmRedpacketUserService wmRedpacketUserService;

    /**
     * 查询投资榜前50的用户
     * 类别包括:
     * 定存宝(不是购买债权转让得)
     * 定存宝复投(8号凌晨也算)
     * 转转赚
     * 随心存
     *
     * @param normalStartTime 正常活动开始时间
     * @param rebuyStartTime  复投开始时间
     * @param endTime         活动结束时间
     * @return
     */
    @Override
    public List<Object[]> queryTop50Users(Long normalStartTime, Long rebuyStartTime, Long endTime) {
        return wmActivityDoubleElevenRepository.queryTop50Users(normalStartTime, rebuyStartTime, endTime);
    }

    /**
     * 发放加息券红包
     *
     * @param rank
     * @param userId
     * @param interest
     */
    @Override
    public void sendInterest(int rank, Long userId, WmRedPacketInterest interest) {
        BigDecimal giveValue;
        if (rank >= 1 && rank <= 10) {
            giveValue = new BigDecimal(1.5);
        } else if (rank >= 11 && rank <= 20) {
            giveValue = new BigDecimal(1.2);
        } else if (rank >= 21 && rank <= 30) {
            giveValue = new BigDecimal(0.9);
        } else if (rank >= 31 && rank <= 40) {
            giveValue = new BigDecimal(0.6);
        } else giveValue = new BigDecimal(0.3);
        wmRedpacketUserService.sendInterest(userId, 2, giveValue, interest, WmRedPacketInterest.TRIGGERTYPE_RAGULARRANK);
    }


    @Override
    public void sendCash(Long userId, BigDecimal buyFund, Integer days, WmRedPacketCash cash) {
        BigDecimal cashFund = culculateCashFund(buyFund, days);
        if (cashFund != null && cashFund.compareTo(BigDecimal.ZERO) == 1) {
            wmRedpacketUserService.sendCash(userId, cash, cashFund, 45);
            saveWmActivityEleven(userId, buyFund, cashFund, days);
        }
    }


    public void saveWmActivityEleven(Long userId, BigDecimal buyFund, BigDecimal cashFund, Integer days) {
        WmActivityEleven activityEleven = new WmActivityEleven();
        activityEleven.setUserId(userId);
        activityEleven.setMoney(buyFund);
        activityEleven.setRedpacket(cashFund);
        if (days == 30) {
            activityEleven.setName("定存宝1个月");
        }
        if (days == 90) {
            activityEleven.setName("定存宝3个月");
        }

        if (days == 180) {
            activityEleven.setName("定存宝6个月");
        }

        if (days >= 360) {
            activityEleven.setName("定存宝12个月");
        }
        wmActivityDoubleElevenRepository.saveWmActivityEleven(activityEleven);

    }

    /**
     * 计算现金红包金额
     *
     * @param buyFund 购买金额
     * @param days    产品天数
     * @return
     */
    private BigDecimal culculateCashFund(BigDecimal buyFund, Integer days) {
        if (buyFund == null || buyFund.compareTo(BigDecimal.ZERO) <= 0 || days == null) return BigDecimal.ZERO;
        if (days >= 90 && days < 180) {
            if (buyFund.compareTo(PACKETLEVEL) <= 0) {
                return buyFund.multiply(RATIO_A).setScale(2, BigDecimal.ROUND_DOWN);
            }
            return buyFund.multiply(RATIO_B).setScale(2, BigDecimal.ROUND_DOWN);
        } else if (days >= 180 && days < 365) {
            if (buyFund.compareTo(PACKETLEVEL) <= 0) {
                return buyFund.multiply(RATIO_C).setScale(2, BigDecimal.ROUND_DOWN);
            }
            return buyFund.multiply(RATIO_D).setScale(2, BigDecimal.ROUND_DOWN);
        }
        return BigDecimal.ZERO;
    }
}