package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmPrivilegeUserAccount;
import com.ydzb.account.repository.WmPrivilegeUserAccountRepository;
import com.ydzb.account.service.IWmPrivilegeUserAccountService;
import com.ydzb.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.LockModeType;
import java.math.BigDecimal;

/**
 * 新手特权标持有记录service实现
 */
@Service
public class WmPrivilegeUserAccountServiceImpl implements IWmPrivilegeUserAccountService {

    @Autowired
    private WmPrivilegeUserAccountRepository privilegeUserAccountRepository;

    @Override
    public WmPrivilegeUserAccount increaseProfit(WmPrivilegeUserAccount privilegeUserAccount, BigDecimal fund, Long settlementDate) throws Exception {
        if (privilegeUserAccount != null) {
            privilegeUserAccount.setAllFund(privilegeUserAccount.getAllFund() == null? fund: privilegeUserAccount.getAllFund().add(fund));
            privilegeUserAccount.setProfit(privilegeUserAccount.getProfit() == null? fund: privilegeUserAccount.getProfit().add(fund));
            privilegeUserAccount.setLastSettlementDate(settlementDate);
            privilegeUserAccountRepository.createOrUpdate(privilegeUserAccount);
        }
        return privilegeUserAccount;
    }

    /**
     * 根据主键查询
     * @param id
     * @param lockMode
     * @return
     */
    @Override
    public WmPrivilegeUserAccount queryOne(Long id, LockModeType lockMode) {
        return privilegeUserAccountRepository.queryOne(id, lockMode);
    }

    /**
     * 清空金额
     * @param privilegeUserAccount 新手特权持有记录
     */
    @Override
    public void emptyFund(WmPrivilegeUserAccount privilegeUserAccount) {
        if (privilegeUserAccount != null) {
            privilegeUserAccount.setAllFund(BigDecimal.ZERO);
            privilegeUserAccount.setProfit(BigDecimal.ZERO);
            privilegeUserAccountRepository.createOrUpdate(privilegeUserAccount);
        }
    }

    /**
     * 处理复利复投
     * @param privilegeUserAccount 新手宝账户
     * @param remainingProfit 剩余的复利金额
     * @param buyFund 复投金额
     */
    @Override
    public void handleProfileRecast(WmPrivilegeUserAccount privilegeUserAccount, BigDecimal remainingProfit, BigDecimal buyFund) throws Exception {
        if (privilegeUserAccount != null) {
            Long curDate = DateUtil.getSystemTimeDay(DateUtil.getCurrentDate());  //当前日期
            //重置新手宝总金额
            privilegeUserAccount.setAllFund(privilegeUserAccount.getAllFund() == null? BigDecimal.ZERO: privilegeUserAccount.getAllFund().subtract(buyFund));
            //重置新手宝复利
            privilegeUserAccount.setProfit(remainingProfit);
            //增加购买冻结金额
            privilegeUserAccount.setWaitAmount(privilegeUserAccount.getWaitAmount() == null? buyFund: privilegeUserAccount.getWaitAmount().add(buyFund));
            //增加不可赎回金额
            privilegeUserAccount.setUnuseAmount(privilegeUserAccount.getUnuseAmount() == null? buyFund: privilegeUserAccount.getUnuseAmount().add(buyFund));
            //如果操作日期是今天
            if (curDate.equals(privilegeUserAccount.getCurDate())) {
                //累计当日不可赎回金额
                privilegeUserAccount.setCurDateUnuse(privilegeUserAccount.getCurDateUnuse() == null? buyFund: privilegeUserAccount.getCurDateUnuse().add(buyFund));
            } else {
                //重置当日不可赎回金额
                privilegeUserAccount.setCurDateUnuse(buyFund);
                //重置操作日期
                privilegeUserAccount.setCurDate(curDate);
            }
            privilegeUserAccountRepository.createOrUpdate(privilegeUserAccount);
        }
    }

    /**
     * 处理复利复投成功
     * @param privilegeUserAccount 新手宝账户
     * @param buyFund 复投金额
     */
    @Override
    public void handleProfileRecastSuccess(WmPrivilegeUserAccount privilegeUserAccount, BigDecimal buyFund) throws Exception {
        if (privilegeUserAccount != null) {
            //减少购买冻结金额
            privilegeUserAccount.setWaitAmount(privilegeUserAccount.getWaitAmount() == null? BigDecimal.ZERO: privilegeUserAccount.getWaitAmount().subtract(buyFund));
            //减少不可赎回金额
            privilegeUserAccount.setUnuseAmount(privilegeUserAccount.getUnuseAmount() == null? BigDecimal.ZERO: privilegeUserAccount.getUnuseAmount().subtract(buyFund));
            privilegeUserAccountRepository.createOrUpdate(privilegeUserAccount);
        }
    }
}