package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 新手特权标持有记录
 */
@Entity
@Table(name = "wm_privilege_user_account")
@DynamicInsert
@DynamicUpdate
public class WmPrivilegeUserAccount extends BaseEntity<Long> implements IWmDailyProductAccount {

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 总金额 （含复利）
     */
    @Column(name = "all_fund")
    private BigDecimal allFund;

    private BigDecimal profit;  //复利

    /**
     * 购买时间
     */
    @Column(name = "buy_time")
    private Long buyTime;

    /**
     * 到期日期
     */
    @Column(name = "expire_time")
    private Long expireTime;

    /**
     * 上次结算日期
     */
    @Column(name = "dl_last_settlement_date")
    private Long lastSettlementDate;

    /**
     * 购买冻结金额
     */
    @Column(name = "wait_amount")
    private BigDecimal waitAmount;

    /**
     * 不可赎回金额 处理完成后减掉
     */
    @Column(name = "unuse_amount")
    private BigDecimal unuseAmount;

    /**
     * 赎回冻结金额
     */
    @Column(name = "redem_amount")
    private BigDecimal redeemAmount;

    /**
     * 当日不可赎回金额 当日累加 不减
     */
    @Column(name = "curdate_unuse")
    private BigDecimal curDateUnuse;

    /**
     * 日期 如果是当前日期 curdate_unuse 与 unuse_aamount 取大  如果不是当日 取unuse_aamount
     */
    @Column(name = "curdate")
    private Long curDate;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getAllFund() {
        return allFund;
    }

    public void setAllFund(BigDecimal allFund) {
        this.allFund = allFund;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public Long getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Long buyTime) {
        this.buyTime = buyTime;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public Long getLastSettlementDate() {
        return lastSettlementDate;
    }

    public void setLastSettlementDate(Long lastSettlementDate) {
        this.lastSettlementDate = lastSettlementDate;
    }

    public BigDecimal getWaitAmount() {
        return waitAmount;
    }

    public void setWaitAmount(BigDecimal waitAmount) {
        this.waitAmount = waitAmount;
    }

    public BigDecimal getUnuseAmount() {
        return unuseAmount;
    }

    public void setUnuseAmount(BigDecimal unuseAmount) {
        this.unuseAmount = unuseAmount;
    }

    public BigDecimal getRedeemAmount() {
        return redeemAmount;
    }

    public void setRedeemAmount(BigDecimal redeemAmount) {
        this.redeemAmount = redeemAmount;
    }

    public BigDecimal getCurDateUnuse() {
        return curDateUnuse;
    }

    public void setCurDateUnuse(BigDecimal curDateUnuse) {
        this.curDateUnuse = curDateUnuse;
    }

    public Long getCurDate() {
        return curDate;
    }

    public void setCurDate(Long curDate) {
        this.curDate = curDate;
    }
}