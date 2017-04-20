package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 新手特权标每日结算
 */
@Entity
@Table(name = "wm_privilege_settlement")
@DynamicInsert
@DynamicUpdate
public class WmPrivilegeSettlement extends BaseEntity<Long> {

    /**
     * 用户Id
     */
    @Column(name = "user_id")
    private Long userId;
    /**
     * 产品id
     */
    @Column(name = "product_id")
    private Long productId;

    private BigDecimal fund;    //总资金
    private BigDecimal apr; //利率
    private BigDecimal income; //收益
    /**
     * 结算时间
     */
    @Column(name = "account_date")
    private Long accountDate;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getFund() {
        return fund;
    }

    public void setFund(BigDecimal fund) {
        this.fund = fund;
    }

    public BigDecimal getApr() {
        return apr;
    }

    public void setApr(BigDecimal apr) {
        this.apr = apr;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public Long getAccountDate() {
        return accountDate;
    }

    public void setAccountDate(Long accountDate) {
        this.accountDate = accountDate;
    }
}