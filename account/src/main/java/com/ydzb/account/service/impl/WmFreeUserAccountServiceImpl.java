package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmFreeUserAccount;
import com.ydzb.account.repository.WmFreeUserAccountRepository;
import com.ydzb.account.service.IWmFreeUserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 随心存账户service实现
 */
@Service
public class WmFreeUserAccountServiceImpl implements IWmFreeUserAccountService {

    @Autowired
    private WmFreeUserAccountRepository freeUserAccountRepository;

    /**
     * 更新状态
     * @param freeUserAccount 自由存账户
     * @param status 状态
     * @return
     */
    @Override
    public WmFreeUserAccount updateStatus(WmFreeUserAccount freeUserAccount, int status) throws Exception {
        if (freeUserAccount != null) {
            freeUserAccount.setStatus(status);
            return freeUserAccountRepository.createOrUpdate(freeUserAccount);
        }
        return null;
    }

    /**
     * 处理随心存到期并且不是最后一期的
     * @param freeUserAccount 随心存账户
     * @param income 收益
     */
    @Override
    public void handleNotLatestStage(WmFreeUserAccount freeUserAccount, BigDecimal income) {
        if (freeUserAccount != null) {
            income = income == null? BigDecimal.ZERO: income;
            //减少预期收益
            BigDecimal predictIncome = freeUserAccount.getPredictIncome() == null? BigDecimal.ZERO: freeUserAccount.getPredictIncome().subtract(income);
            if (predictIncome.doubleValue() < 0) {
                predictIncome = BigDecimal.ZERO;
            }
            freeUserAccount.setPredictIncome(predictIncome);
            //用户已还期数
            freeUserAccount.setRefundCount(freeUserAccount.getRefundCount() == null? 1: freeUserAccount.getRefundCount() + 1);
            //已还款金额
            freeUserAccount.setRefundable(freeUserAccount.getRefundable() == null? income: freeUserAccount.getRefundable().add(income));
            freeUserAccount.setStatus(WmFreeUserAccount.STATUS_NOTEXPIRE);
            //更新用户交易
            freeUserAccountRepository.createOrUpdate(freeUserAccount);
        }
    }
}