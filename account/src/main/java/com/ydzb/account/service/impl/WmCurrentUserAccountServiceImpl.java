package com.ydzb.account.service.impl;

import com.ydzb.account.entity.WmCurrentUserAccount;
import com.ydzb.account.repository.WmCurrentUserAccountRepository;
import com.ydzb.account.service.IWmCurrentUserAccountService;
import com.ydzb.core.utils.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.LockModeType;
import java.math.BigDecimal;


@Service
public class WmCurrentUserAccountServiceImpl implements IWmCurrentUserAccountService {

    public Logger logger = Logger.getLogger(WmCurrentUserAccountServiceImpl.class);

    @Autowired
    private WmCurrentUserAccountRepository currentUserAccountRepository;

    @Value("${current.newuser.fund}")
    private int currentNewUserFund;


    /**
     * 更新签到概率
     *
     * @throws Exception
     */
    @Override
    public void accountSignProbability() throws Exception {
        int rows = currentUserAccountRepository.accountSignProbability();
        logger.info("===更新签到概率===" + rows);
    }

    /**
     * 更新每天第一次签到
     *
     * @throws Exception
     */
    @Override
    public void accountSign() throws Exception {
        int rows = currentUserAccountRepository.accountSign();
        logger.info("===更新每日签到===" + rows);
    }

    /**
     * 更新用户昨日收益
     *
     * @throws Exception
     */
    @Override
    public void accountYesterdayIncome() throws Exception {
        int rows = currentUserAccountRepository.updateYesterdayIncome();
        logger.info("===更新昨日收益行数===" + rows);
    }

    /**
     * 处理存管用户复利触发复投
     * @param currentUserAccount 用户活期宝账户
     * @param remainingProfit 剩余复利
     * @param buyFund 购买金额
     */
    @Override
    public void handleDepositoryUserProfileReach(WmCurrentUserAccount currentUserAccount, BigDecimal remainingProfit, BigDecimal buyFund) {
        if (currentUserAccount != null) {
            Long curDate = DateUtil.getSystemTimeDay(DateUtil.getCurrentDate());  //当前日期
            //重置活期宝总金额
            currentUserAccount.setAllFund(currentUserAccount.getAllFund() == null? BigDecimal.ZERO: currentUserAccount.getAllFund().subtract(buyFund));
            //重置活期宝复利
            currentUserAccount.setProfit(remainingProfit);
            //增加购买冻结金额
            currentUserAccount.setWaitAmount(currentUserAccount.getWaitAmount() == null? buyFund: currentUserAccount.getWaitAmount().add(buyFund));
            //增加不可赎回金额
            currentUserAccount.setUnuseAmount(currentUserAccount.getUnuseAmount() == null? buyFund: currentUserAccount.getUnuseAmount().add(buyFund));

            //如果操作日期是今天
            if (curDate.equals(currentUserAccount.getCurDate())) {
                //累计当日不可赎回金额
                currentUserAccount.setCurdateUnuse(currentUserAccount.getCurdateUnuse() == null? buyFund: currentUserAccount.getCurdateUnuse().add(buyFund));
            } else {
                //重置当日不可赎回金额
                currentUserAccount.setCurdateUnuse(buyFund);
                //重置操作日期
                currentUserAccount.setCurDate(curDate);
            }
            currentUserAccountRepository.updateCurrentUserAccount(currentUserAccount);
        }
    }

    /**
     * 增加复利
     * @param currentUserAccount 活期宝账户
     * @param fund 金额
     * @param curDate 操作日期
     * @return
     */
    @Override
    public WmCurrentUserAccount increaseProfit(WmCurrentUserAccount currentUserAccount, BigDecimal fund, Long curDate) {
        if (currentUserAccount != null) {
            currentUserAccount.setAllFund(currentUserAccount.getAllFund() == null? fund: currentUserAccount.getAllFund().add(fund));
            currentUserAccount.setProfit(currentUserAccount.getProfit() == null? fund: currentUserAccount.getProfit().add(fund));
            currentUserAccount.setDlLastSettlementDate(curDate);
            currentUserAccountRepository.updateCurrentUserAccount(currentUserAccount);
        }
        return currentUserAccount;
    }

    /**
     * 根据用户查询活期宝账户
     * @param userId 活期id
     * @param lockType 锁类型
     * @return
     */
    @Override
    public WmCurrentUserAccount queryByUser(Long userId, LockModeType lockType) {
        if (lockType == null || lockType == LockModeType.NONE) return currentUserAccountRepository.queryByUser(userId);
        Long id = currentUserAccountRepository.queryIdByUser(userId);
        return currentUserAccountRepository.queryById(id, lockType);
    }

    /**
     * 增加活期宝总金额，如果不存在活期宝则创建
     * @param userId 用户id
     * @param fund 金额
     * @return
     */
    @Override
    public WmCurrentUserAccount increaseAllFund(Long userId, BigDecimal fund) {
        WmCurrentUserAccount currentUserAccount = queryByUser(userId, LockModeType.PESSIMISTIC_WRITE);
        if (currentUserAccount != null) {
            currentUserAccount.setAllFund(currentUserAccount.getAllFund() == null? fund: currentUserAccount.getAllFund().add(fund));
            currentUserAccountRepository.updateCurrentUserAccount(currentUserAccount);
        } else {
            currentUserAccount = new WmCurrentUserAccount();
            currentUserAccount.setUserId(userId);
            currentUserAccount.setAllFund(fund);
            currentUserAccountRepository.getManager().persist(currentUserAccount);
        }
        return currentUserAccount;
    }
}
