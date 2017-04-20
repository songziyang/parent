package com.ydzb.account.entity;


import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "wm_current_user_account")
@DynamicUpdate
@DynamicInsert
public class WmCurrentUserAccount extends BaseEntity<Long> implements IWmDailyProductAccount {

    private static final long serialVersionUID = 1L;


    @Column(name = "user_id")
    private Long userId;

    // 总金额
    @Column(name = "all_fund")
    private BigDecimal allFund;

    // 体验金
    @Column(name = "exp_fund")
    private BigDecimal expFund;

    //复利
    private BigDecimal profit;

    //上次结算时间
    @Column(name = "dl_last_settlement_date")
    private Long dlLastSettlementDate;

    /**
     * 购买冻结金额
     */
    @Column(name = "wait_amount")
    private BigDecimal waitAmount;

    /**
     * 不可赎回金额
     */
    @Column(name = "unuse_amount")
    private BigDecimal unuseAmount;

    /**
     * 当日不可赎回金额
     */
    @Column(name = "curdate_unuse")
    private BigDecimal curdateUnuse;

    /**
     * 记录日期
     */
    @Column(name = "curdate")
    private Long curDate;

    public WmCurrentUserAccount() {

    }

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

    public BigDecimal getExpFund() {
        return expFund;
    }

    public void setExpFund(BigDecimal expFund) {
        this.expFund = expFund;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public Long getDlLastSettlementDate() {
        return dlLastSettlementDate;
    }

    public void setDlLastSettlementDate(Long dlLastSettlementDate) {
        this.dlLastSettlementDate = dlLastSettlementDate;
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

    public BigDecimal getCurdateUnuse() {
        return curdateUnuse;
    }

    public void setCurdateUnuse(BigDecimal curdateUnuse) {
        this.curdateUnuse = curdateUnuse;
    }

    public Long getCurDate() {
        return curDate;
    }

    public void setCurDate(Long curDate) {
        this.curDate = curDate;
    }
}