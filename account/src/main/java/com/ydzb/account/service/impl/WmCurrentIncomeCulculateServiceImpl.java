package com.ydzb.account.service.impl;

import com.ydzb.account.entity.*;
import com.ydzb.account.repository.WmRedpacketUsedRepository;
import com.ydzb.account.repository.WmVipGradeRepository;
import com.ydzb.account.service.IWmCurrentIncomeCulculateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.LockModeType;
import java.math.BigDecimal;
import java.util.List;

/**
 * 收益计算service实现
 */
@Service
public class WmCurrentIncomeCulculateServiceImpl implements IWmCurrentIncomeCulculateService {

    @Autowired
    private WmRedpacketUsedRepository redpacketUsedRepository;
    @Autowired
    private WmVipGradeRepository vipGradeRepository;

    /**
     * 计算收益
     * @param user
     * @param productInfo
     * @param allFund
     * @param expFund
     * @return
     */
    @Override
    public WmIncomeEntity culculateIncome(WmUser user, WmProductInfo productInfo, BigDecimal allFund, BigDecimal expFund) {

        //收益实体
        WmIncomeEntity incomeEntity = new WmIncomeEntity();
        //总金额
        BigDecimal incomeFund = allFund.add(expFund);
        //活期宝利率
        BigDecimal incomeApr = productInfo.getInterestRate();
        //日加息券利率
        BigDecimal dayApr = BigDecimal.ZERO;
        //月加息券利率
        BigDecimal monthApr = BigDecimal.ZERO;
        //VIP利率
        BigDecimal vipApr = BigDecimal.ZERO;

        //总收益
        BigDecimal income = incomeApr.multiply(incomeFund).divide(new BigDecimal(36500), 6, BigDecimal.ROUND_HALF_DOWN);
        //体验金收益
        BigDecimal investIncome = incomeApr.multiply(expFund).divide(new BigDecimal(36500), 6, BigDecimal.ROUND_HALF_DOWN);
        //加息收益
        BigDecimal interestIncome = BigDecimal.ZERO;
        //VIP加息收益
        BigDecimal vipIncome = BigDecimal.ZERO;

        //用户活期使用红包
        List<WmRedpacketUsed> usedRedpackets = redpacketUsedRepository.queryByUser(user.getId());
        //判断是否有使用红包
        if (usedRedpackets != null) {
            for (WmRedpacketUsed wmRedpacketUsed: usedRedpackets) {
                if (wmRedpacketUsed != null) {
                    //单笔加息
                    if (wmRedpacketUsed.getProductType() == 1) {
                        //单笔加息
                        monthApr = monthApr.add(wmRedpacketUsed.getApr());
                        //判断活期宝账户金额是否大于单笔加息金额
                        if (wmRedpacketUsed.getFund() != null && allFund.doubleValue() > wmRedpacketUsed.getFund().doubleValue()) {
                            interestIncome = interestIncome.add(wmRedpacketUsed.getApr().multiply(wmRedpacketUsed.getFund()).divide(new BigDecimal(36500), 6, BigDecimal.ROUND_HALF_DOWN));
                        } else {
                            interestIncome = interestIncome.add(wmRedpacketUsed.getApr().multiply(allFund).divide(new BigDecimal(36500), 6, BigDecimal.ROUND_HALF_DOWN));
                        }
                    }
                    //整个加息
                    if (wmRedpacketUsed.getProductType() == 2) {
                        dayApr = dayApr.add(wmRedpacketUsed.getApr());
                        interestIncome = interestIncome.add(wmRedpacketUsed.getApr().multiply(allFund).divide(new BigDecimal(36500), 6, BigDecimal.ROUND_HALF_DOWN));
                    }
                }
            }
            //总收益 = 总收益 + 红包收益
            income = income.add(interestIncome);
        }
        //判断用户
        if (user != null) {
            //用户等级
            WmVipGrade vipGrade = vipGradeRepository.queryById(user.getUserLeve(), LockModeType.NONE);
            if (vipGrade != null) {
                vipApr = vipGrade.getDayloanApr();
                vipIncome = allFund.multiply(vipApr).divide(new BigDecimal(36500), 6, BigDecimal.ROUND_HALF_DOWN);
                income = income.add(vipIncome);
            }
        }

        incomeEntity.setIncome(income);
        incomeEntity.setInterestIncome(interestIncome);
        incomeEntity.setExpIncome(investIncome);
        incomeEntity.setVipIncome(vipIncome);

        //利率实体
        WmAprEntity aprEntity = new WmAprEntity();
        aprEntity.setApr(incomeApr);
        aprEntity.setDayApr(dayApr);
        aprEntity.setMonthApr(monthApr);
        aprEntity.setVipApr(vipApr);
        incomeEntity.setAprEntity(aprEntity);
        return incomeEntity;
    }
}