package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmUserMoney;
import com.ydzb.account.repository.WmUserMoneyRepository;
import com.ydzb.account.service.IWmUserMoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.LockModeType;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 */
@Service
public class WmUserMoneyServiceImpl implements IWmUserMoneyService {

    @Autowired
    private WmUserMoneyRepository wmUserMoneyRepository;

    /**
     * 根据userId查询
     * @param userId 用户id
     * @param lockType 锁类型
     * @return
     */
    @Override
    public WmUserMoney queryOne(Long userId, LockModeType lockType) {

        if (lockType == null || lockType == LockModeType.NONE) {
            return wmUserMoneyRepository.findByUser(userId);
        }
        Long wmUserMoneyId = wmUserMoneyRepository.queryIdByUser(userId);
        if (wmUserMoneyId != null) {
            return wmUserMoneyRepository.queryById(wmUserMoneyId, lockType);
        }

        return null;
    }

    @Override
    public WmUserMoney saveOrUpdate(WmUserMoney entity) {
        return wmUserMoneyRepository.saveOrUpdate(entity);
    }

    /**
     * 增加总金额
     * @param userId 用户id
     * @param fund 增加的金额
     * @return
     */
    @Override
    public WmUserMoney increaseTotalFund(Long userId, BigDecimal fund) {
        WmUserMoney userMoney = wmUserMoneyRepository.queryByUser(userId, LockModeType.PESSIMISTIC_WRITE);
        if (userMoney != null) {
            userMoney.setTotalFund(userMoney.getTotalFund() == null? fund: userMoney.getTotalFund().add(fund));
            wmUserMoneyRepository.createOrUpdate(userMoney);
        }
        return userMoney;
    }

    /**
     * 增加总金额和余额
     * @param userId 用户id
     * @param fund 增加的金额
     * @return
     */
    @Override
    public WmUserMoney increaseTotalAndUsableFund(Long userId, BigDecimal fund) {
        WmUserMoney userMoney = wmUserMoneyRepository.queryByUser(userId, LockModeType.PESSIMISTIC_WRITE);
        if (userMoney != null) {
            userMoney.setTotalFund(userMoney.getTotalFund().add(fund));
            userMoney.setUsableFund(userMoney.getUsableFund().add(fund));
            wmUserMoneyRepository.createOrUpdate(userMoney);
        }
        return userMoney;
    }

    /**
     * 增加用户定存到期不复投后的资金
     * @param userId 用户ID
     * @param fund   本金
     * @param income 收益
     */
    @Override
    public WmUserMoney increaseRagularNoRecastFund(Long userId, BigDecimal fund, BigDecimal income) {
        WmUserMoney userMoney = wmUserMoneyRepository.queryByUser(userId, LockModeType.PESSIMISTIC_WRITE);
        if (userMoney != null) {
            //用户资金总额 收益
            userMoney.setTotalFund(userMoney.getTotalFund() == null? income: userMoney.getTotalFund().add(income));
            //用户资金余额 本金 收益
            userMoney.setUsableFund(userMoney.getUsableFund() == null? fund.add(income): userMoney.getUsableFund().add(fund).add(income));
            //更新用户资金总额
            wmUserMoneyRepository.createOrUpdate(userMoney);
        }
        return userMoney;
    }

    /**
     * 增加用户定存到期本息复投的资金
     * @param userId 用户ID
     * @param allIncome   全部收益
     * @param decimalIncome 小数部分收益
     */
    @Override
    public WmUserMoney increaseRagularAllRecastFund(Long userId, BigDecimal allIncome, BigDecimal decimalIncome) {
        WmUserMoney userMoney = wmUserMoneyRepository.queryByUser(userId, LockModeType.PESSIMISTIC_WRITE);
        if (userMoney != null) {
            //用户资金总额 收益
            userMoney.setTotalFund(userMoney.getTotalFund() == null? allIncome: userMoney.getTotalFund().add(allIncome));
            //用户资金余额 本金 收益
            userMoney.setUsableFund(userMoney.getUsableFund() == null? decimalIncome: userMoney.getUsableFund().add(decimalIncome));
            //更新用户资金总额
            wmUserMoneyRepository.createOrUpdate(userMoney);
        }
        return userMoney;
    }

    /**
     * 增加用户定存到期本金复投的资金
     * @param userId 用户ID
     * @param fund 金额
     * @param income 收益
     */
    @Override
    public WmUserMoney increaseRagularPrincipalRecastFund(Long userId, BigDecimal fund, BigDecimal income) {
        WmUserMoney userMoney = wmUserMoneyRepository.queryByUser(userId, LockModeType.PESSIMISTIC_WRITE);
        if (userMoney != null) {
            //用户资金总额 收益
            userMoney.setTotalFund(userMoney.getTotalFund() == null? income: userMoney.getTotalFund().add(income));
            //用户资金余额 本金 收益
            userMoney.setUsableFund(userMoney.getUsableFund() == null? fund.add(income): userMoney.getUsableFund().add(fund).add(income));
            //更新用户资金总额
            wmUserMoneyRepository.createOrUpdate(userMoney);
        }
        return userMoney;
    }

    /**
     * 增加随心存到期的资金
     * @param userId 用户ID
     * @param fund   本金
     * @param income 收益
     */
    @Override
    public WmUserMoney increaseFreeExpireFund(Long userId, BigDecimal fund, BigDecimal income) {
        return increaseRagularNoRecastFund(userId, fund, income);
    }
}
