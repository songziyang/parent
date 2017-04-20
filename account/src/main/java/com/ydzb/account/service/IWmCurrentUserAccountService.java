package com.ydzb.account.service;


import com.ydzb.account.entity.WmCurrentUserAccount;

import javax.persistence.LockModeType;
import java.math.BigDecimal;

public interface IWmCurrentUserAccountService {


    /**
     * 更新签到概率
     *
     * @throws Exception
     */
    public void accountSignProbability() throws Exception;


    /**
     * 更新每天第一次签到
     *
     * @throws Exception
     */
    public void accountSign() throws Exception;

    /**
     * 更新用户昨日收益
     *
     * @throws Exception
     */
    public void accountYesterdayIncome() throws Exception;


    /**
     * 增加复利
     * @param currentUserAccount 活期宝账户
     * @param fund 金额
     * @param curDate 操作日期
     * @return
     */
    WmCurrentUserAccount increaseProfit(WmCurrentUserAccount currentUserAccount, BigDecimal fund, Long curDate);

    /**
     * 处理存管用户复利触发复投流程
     * @param currentUserAccount 用户活期宝账户
     * @param remainingProfit 剩余复利
     * @param buyFund 购买金额
     */
    void handleDepositoryUserProfileReach(WmCurrentUserAccount currentUserAccount, BigDecimal remainingProfit, BigDecimal buyFund);

    /**
     * 根据用户查询活期宝账户
     * @param userId 活期id
     * @param lockType 锁类型
     * @return
     */
    WmCurrentUserAccount queryByUser(Long userId, LockModeType lockType);

    /**
     * 增加活期宝总金额，如果不存在活期宝则创建
     * @param userId 用户id
     * @param fund 金额
     * @return
     */
    WmCurrentUserAccount increaseAllFund(Long userId, BigDecimal fund) throws Exception;
}
