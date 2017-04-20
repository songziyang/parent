package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmUserExMoney;
import com.ydzb.account.repository.WmUserExpmoneyRepository;
import com.ydzb.account.service.IWmUserExpmoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.LockModeType;
import java.math.BigDecimal;

/**
 * 用户体验金service实现
 */
@Service
public class WmUserExpmoneyServiceImpl implements IWmUserExpmoneyService {

    @Autowired
    private WmUserExpmoneyRepository wmUserExpmoneyRepository;

    /**
     * 更新资金
     * @param wmUserExMoney
     * @param fund
     * @return
     */
    @Override
    public WmUserExMoney updateAsset(WmUserExMoney wmUserExMoney, BigDecimal fund) {

        if (wmUserExMoney != null) {

            BigDecimal ableMoney = wmUserExMoney.getAbleMoney();
            BigDecimal amount = wmUserExMoney.getAmount();
            fund = fund == null? BigDecimal.ZERO: fund;

            wmUserExMoney.setAbleMoney(ableMoney == null? fund: ableMoney.add(fund));
            wmUserExMoney.setAmount(amount == null? fund: amount.add(fund));

            return wmUserExpmoneyRepository.saveOrUpdate(wmUserExMoney);
        }

        return null;
    }

    /**
     * 根据用户id查询
     * @param userId 用户id
     * @param lockType 锁类型
     * @return
     */
    @Override
    public WmUserExMoney queryOne(Long userId, LockModeType lockType) {

        if (lockType == null || lockType == LockModeType.NONE) {
            return wmUserExpmoneyRepository.queryOneByUser(userId);
        }
        Long id = wmUserExpmoneyRepository.queryIdByUser(userId);
        if (id != null) {
            return wmUserExpmoneyRepository.queryOneById(id, lockType);
        }

        return null;
    }
}