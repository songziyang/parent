package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmUserIncome;
import com.ydzb.account.repository.WmUserIncomeRepository;
import com.ydzb.account.service.IWmUserIncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.LockModeType;
import java.math.BigDecimal;

/**
 * Created by sy on 2016/7/25.
 */
@Service
public class WmUserIcomeServiceImpl implements IWmUserIncomeService {



    @Autowired
    private WmUserIncomeRepository wmUserIncomeRepository;

    @Override
    public WmUserIncome findByUser(Long userId, LockModeType lockModeType) {
        return wmUserIncomeRepository.findByUser(userId, lockModeType);
    }

    @Override
    public WmUserIncome saveOrUpdate(WmUserIncome entity) {
        return wmUserIncomeRepository.saveOrUpdate(entity);
    }

    /**
     * 增加新手宝结算收益
     * @param userId         用户ID
     * @param income         总收益
     */
    @Override
    public WmUserIncome increasePrivilegetSettlementIncome(Long userId, BigDecimal income) {
        WmUserIncome userIncome = wmUserIncomeRepository.findByUser(userId, LockModeType.PESSIMISTIC_WRITE);
        if (userIncome != null) {
            //昨日收益
            userIncome.setYesterdayIncome(userIncome.getYesterdayIncome() == null? income: userIncome.getYesterdayIncome().add(income));
            //累计收益总额
            userIncome.setAllIncome(userIncome.getAllIncome() == null? income: userIncome.getAllIncome().add(income));
            //新手宝收益
            userIncome.setAllIncomePrivilege(userIncome.getAllIncomePrivilege() == null? income: userIncome.getAllIncomePrivilege().add(income));
            //更新用户收益
            wmUserIncomeRepository.createOrUpdate(userIncome);
        }
        return userIncome;
    }

    /**
     * 增加活期宝结算收益
     * @param userId         用户ID
     * @param income         总收益
     * @param investIncome   体验金收益
     * @param interestIncome 加息收益
     * @param vipIncome      VIP收益
     */
    @Override
    public WmUserIncome increaseCurrentSettlementIncome(Long userId, BigDecimal income, BigDecimal investIncome, BigDecimal interestIncome, BigDecimal vipIncome) {
        WmUserIncome userIncome = wmUserIncomeRepository.findByUser(userId, LockModeType.PESSIMISTIC_WRITE);
        if (userIncome != null) {
            //昨日收益
            userIncome.setYesterdayIncome(userIncome.getYesterdayIncome() == null? income: userIncome.getYesterdayIncome().add(income));
            //累计收益总额
            userIncome.setAllIncome(userIncome.getAllIncome() == null? income: userIncome.getAllIncome().add(income));
            //活期收益
            userIncome.setAllIncomeDayloan(userIncome.getAllIncomeDayloan() == null? income: userIncome.getAllIncomeDayloan().add(income));
            //体验金收益
            userIncome.setAllIncomeInvest(userIncome.getAllIncomeInvest() == null? investIncome: userIncome.getAllIncomeInvest().add(investIncome));
            //加息收益
            userIncome.setAllIncomeInterest(userIncome.getAllIncomeInterest() == null? interestIncome: userIncome.getAllIncomeInterest().add(interestIncome));
            //VIP收益
            userIncome.setAllIncomeVip(userIncome.getAllIncomeVip() == null? vipIncome: userIncome.getAllIncomeVip().add(vipIncome));
            //更新用户收益
            wmUserIncomeRepository.createOrUpdate(userIncome);
        }
        return userIncome;
    }

    /**
     * 更新定存本息复投的收益
     * @param userId 用户id
     * @param income 收益
     * @param interestIncome 加息券收益
     * @param vipIncome vip收益
     * @param vouchersIncome 定存红包收益
     * @throws Exception
     */
    @Override
    public void increaseRagularAllExpireIncome(Long userId, BigDecimal income, BigDecimal interestIncome, BigDecimal vipIncome,
            BigDecimal vouchersIncome) throws Exception {
        WmUserIncome userIncome = wmUserIncomeRepository.findByUser(userId, LockModeType.PESSIMISTIC_WRITE);
        if (userIncome != null) {
            //昨日收益
            userIncome.setYesterdayIncome(userIncome.getYesterdayIncome() == null? income: userIncome.getYesterdayIncome().add(income));
            //累计收益总额
            userIncome.setAllIncome(userIncome.getAllIncome() == null? income: userIncome.getAllIncome().add(income));
            //定存收益
            userIncome.setAllIncomeDeposit(userIncome.getAllIncomeDeposit() == null? income: userIncome.getAllIncomeDeposit().add(income));
            //加息收益
            userIncome.setAllIncomeInterest(userIncome.getAllIncomeInterest() == null? interestIncome: userIncome.getAllIncomeInterest().add(interestIncome));
            //VIP收益
            userIncome.setAllIncomeVip(userIncome.getAllIncomeVip() == null? vipIncome: userIncome.getAllIncomeVip().add(vipIncome));
            //预期收益
            BigDecimal predictIncome = userIncome.getPredictIncome() == null? BigDecimal.ZERO: userIncome.getPredictIncome().subtract(income);
            if (predictIncome.doubleValue() < 0) {
                predictIncome = BigDecimal.ZERO;
            }
            userIncome.setPredictIncome(predictIncome);
            //定存红包收益
            userIncome.setAllIncomeVouchers(userIncome.getAllIncomeVouchers() == null? vouchersIncome: userIncome.getAllIncomeVouchers().add(vouchersIncome));
            //更新用户收益
            wmUserIncomeRepository.createOrUpdate(userIncome);
        }
    }

    /**
     * 更新定存本金复投的收益
     * @param userId 用户id
     * @param income 收益
     * @param interestIncome 加息券收益
     * @param vipIncome vip收益
     * @param vouchersIncome 定存红包收益
     * @throws Exception
     */
    @Override
    public void increaseRagularPrincipalExpireIncome(Long userId, BigDecimal income, BigDecimal interestIncome, BigDecimal vipIncome,
            BigDecimal vouchersIncome) throws Exception {
        increaseRagularAllExpireIncome(userId, income, interestIncome, vipIncome, vouchersIncome);
    }

    /**
     * 更新定存不是最后一期还款的收益
     * @param userId 用户id
     * @param income 收益
     * @param interestIncome 加息券收益
     * @param vipIncome vip收益
     * @param vouchersIncome 定存红包收益
     * @throws Exception
     */
    @Override
    public void increaseRagularNotLastStageIncome(Long userId, BigDecimal income, BigDecimal interestIncome, BigDecimal vipIncome,
            BigDecimal vouchersIncome) throws Exception {
        increaseRagularAllExpireIncome(userId, income, interestIncome, vipIncome, vouchersIncome);
    }

    /**
     * 增加定存宝结算收益
     * @param userId 用户id
     * @param income 收益
     * @param interestIncome 加息券收益
     * @param vipIncome vip收益
     * @param vouchersIncome 代金券收益
     */
    @Override
    public void increaseRagularSettlementIncome(Long userId, BigDecimal income, BigDecimal interestIncome, BigDecimal vipIncome, BigDecimal vouchersIncome) {
        WmUserIncome userIncome = wmUserIncomeRepository.findByUser(userId, LockModeType.PESSIMISTIC_WRITE);
        if (userIncome != null) {
            //昨日收益
            userIncome.setYesterdayIncome(userIncome.getYesterdayIncome() == null? income: userIncome.getYesterdayIncome().add(income));
            //累计收益总额
            userIncome.setAllIncome(userIncome.getAllIncome() == null? income: userIncome.getAllIncome().add(income));
            //定存收益
            userIncome.setAllIncomeDeposit(userIncome.getAllIncomeDeposit() == null? income: userIncome.getAllIncomeDeposit().add(income));
            //加息收益
            userIncome.setAllIncomeInterest(userIncome.getAllIncomeInterest() == null? interestIncome: userIncome.getAllIncomeInterest().add(interestIncome));
            //VIP收益
            userIncome.setAllIncomeVip(userIncome.getAllIncomeVip() == null? vipIncome:  userIncome.getAllIncomeVip().add(vipIncome));
            //预期收益
            BigDecimal predictIncome = userIncome.getPredictIncome() == null? BigDecimal.ZERO: userIncome.getPredictIncome().subtract(income);
            if (predictIncome.doubleValue() < 0) {
                userIncome.setPredictIncome(BigDecimal.ZERO);
            } else {
                userIncome.setPredictIncome(predictIncome);
            }
            //代金券收益
            if (userIncome.getAllIncomeVouchers() == null) userIncome.setAllIncomeVouchers(BigDecimal.ZERO);
            userIncome.setAllIncomeVouchers(userIncome.getAllIncomeVouchers().add(vouchersIncome == null? BigDecimal.ZERO: vouchersIncome));
            //更新用户收益
            wmUserIncomeRepository.createOrUpdate(userIncome);
        }
    }

    /**
     * 增加预期收益
     * @param userId 用户id
     * @param fund 金额
     */
    public WmUserIncome increasePredictIncome(Long userId, BigDecimal fund) throws Exception {
        WmUserIncome userIncome = wmUserIncomeRepository.findByUser(userId, LockModeType.PESSIMISTIC_WRITE);
        if (userIncome != null) {
            userIncome.setPredictIncome(userIncome.getPredictIncome() == null? fund: userIncome.getPredictIncome().add(fund));
            wmUserIncomeRepository.createOrUpdate(userIncome);
        }
        return null;
    }


    /**
     * 更新随心存结算收益
     * @param userId 用户id
     * @param income 收益
     * @param vipIncome vip收益
     * @throws Exception
     */
    @Override
    public void increaseFreeExpire(Long userId, BigDecimal income, BigDecimal vipIncome) throws Exception {
        WmUserIncome userIncome = wmUserIncomeRepository.findByUser(userId, LockModeType.PESSIMISTIC_WRITE);
        if (userIncome != null) {
            //昨日收益
            userIncome.setYesterdayIncome(userIncome.getYesterdayIncome() == null? income: userIncome.getYesterdayIncome().add(income));
            //累计收益总额
            userIncome.setAllIncome(userIncome.getAllIncome() == null? income: userIncome.getAllIncome().add(income));
            //随心存收益
            userIncome.setAllIncomeFree(userIncome.getAllIncomeFree() == null? income: userIncome.getAllIncomeFree().add(income));
            //VIP收益
            userIncome.setAllIncomeVip(userIncome.getAllIncomeVip() == null? vipIncome: userIncome.getAllIncomeVip().add(vipIncome));
            //随心存预期收益
            BigDecimal freeIncome = userIncome.getFreeIncome() == null? BigDecimal.ZERO: userIncome.getFreeIncome().subtract(income);
            if (freeIncome.doubleValue() < 0) {
                freeIncome = BigDecimal.ZERO;
            }
            userIncome.setFreeIncome(freeIncome);
            //更新用户收益
            wmUserIncomeRepository.createOrUpdate(userIncome);
        }
    }
}
