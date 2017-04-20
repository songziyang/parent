package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;


@Entity
@Table(name = "wm_current_settlement")
public class WmCurrentSettlement extends BaseEntity<Long> {
    private static final long serialVersionUID = 1L;


    /**
     * 利率
     */
    private BigDecimal apr;
    /**
     * 本金
     */
    @Column(name = "found")
    private BigDecimal fund;

    /**
     * 收益
     */
    private BigDecimal income;

    /**
     * 投资金额
     */
    @Column(name = "invest_fund")
    private BigDecimal investFund;

    /**
     * 产品ID
     */
    @Column(name = "product_id")
    private Long productId;


    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 日加息券
     */
    @Column(name = "day_apr")
    private BigDecimal daypr;

    /**
     * 月加息券
     */
    @Column(name = "month_apr")
    private BigDecimal monthApr;

    @Column(name = "vip_apr")
    private BigDecimal vipApr;

    /**
     * 结算时间
     */
    @Column(name = "account_date")
    private Long accountDate;

    /**
     * 创建时间
     */
    private Long created;


    public WmCurrentSettlement() {

    }

    public Long getAccountDate() {
        return accountDate;
    }

    public void setAccountDate(Long accountDate) {
        this.accountDate = accountDate;
    }

    public BigDecimal getApr() {
        return apr;
    }

    public void setApr(BigDecimal apr) {
        this.apr = apr;
    }

    public BigDecimal getFund() {
        return fund;
    }

    public void setFund(BigDecimal fund) {
        this.fund = fund;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public BigDecimal getInvestFund() {
        return investFund;
    }

    public void setInvestFund(BigDecimal investFund) {
        this.investFund = investFund;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getDaypr() {
        return daypr;
    }

    public void setDaypr(BigDecimal daypr) {
        this.daypr = daypr;
    }

    public BigDecimal getMonthApr() {
        return monthApr;
    }

    public void setMonthApr(BigDecimal monthApr) {
        this.monthApr = monthApr;
    }

    public BigDecimal getVipApr() {
        return vipApr;
    }

    public void setVipApr(BigDecimal vipApr) {
        this.vipApr = vipApr;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }
}