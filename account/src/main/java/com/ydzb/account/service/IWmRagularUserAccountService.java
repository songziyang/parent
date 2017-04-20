package com.ydzb.account.service;


import com.ydzb.account.entity.WmJxFreezeRecord;
import com.ydzb.account.entity.WmRagularUserAccount;

import java.math.BigDecimal;
import java.util.List;

public interface IWmRagularUserAccountService {


    /**
     * 定存宝转让结算
     *
     * @param userId 用户ID
     * @throws Exception
     */
    void accountRagularTransfer(Long userId) throws Exception;


    List<Object[]> queryRagularInfoBetweenTime(Long startTime, Long endTime);

    /**
     * 查询未使用代金券并且不是债权转让的定存信息
     * @param startTime 购买开始时间
     * @param endTime 购买结束时间
     * @return
     */
    List<Object[]> queryWithoutVoucherAndNotTransfer(Long startTime, Long endTime);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    WmRagularUserAccount queryById(Long id);

    /**
     * 查询复投
     * @param startTime 购买开始时间
     * @param endTime 购买结束时间
     * @return
     */
    List<Object[]> queryRebuy(Long startTime, Long endTime);

    void accountRagularUserAccountByFreeRecord(WmJxFreezeRecord freezeRecord) throws Exception;

    /**
     * 减少定存宝结算账户金额
     * @param ragularUserAccount 定存宝账户
     * @param fund 金额
     * @param income 收益
     * @param status 状态
     */
    void decreaseRagularSettlementAccount(WmRagularUserAccount ragularUserAccount, BigDecimal fund, BigDecimal income, Integer status);


    /**
     * 创建
     * @param userId userId
     * @param productId 产品id
     * @param allFund 到期本息总额
     * @param buyTime 购买时间
     * @param buyFund 购买资金
     * @param grandFund 各种方式赠与的资金
     * @param interestFund 利息收益总额
     * @param buyCopies 购买份数
     * @param grandApr 加息利率
     * @param vipApr vip加息利率
     * @param apr 年利率
     * @param expireTime 到期日期
     * @param expireMode 复投模式
     * @param incomeMode 利息是否复投
     * @param buyType 购买类型
     * @param days 产品天数
     * @param status 状态
     * @param predictIncome 预期收益
     * @param expireNum 转让次数
     * @param curProductId 当前产品id
     * @return
     */
    WmRagularUserAccount createOne(Long userId, Long productId, BigDecimal allFund, Long buyTime,
            BigDecimal buyFund, BigDecimal grandFund, BigDecimal interestFund, Integer buyCopies,
            BigDecimal grandApr, BigDecimal vipApr, BigDecimal apr, Long expireTime, Integer expireMode,
            Integer incomeMode, Integer buyType, Integer days, Integer status, BigDecimal predictIncome,
            Integer expireNum, Long curProductId) throws Exception;

    /**
     * 更新状态
     * @param ragularUserAccount 定存宝账户
     * @param status 目标状态
     * @return
     */
     WmRagularUserAccount updateStatus(WmRagularUserAccount ragularUserAccount, Integer status) throws Exception;
}
