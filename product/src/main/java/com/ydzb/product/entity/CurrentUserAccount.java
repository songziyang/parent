package com.ydzb.product.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.user.entity.User;

import javax.persistence.*;
import java.math.BigDecimal;


/**
 * 天标用户投资表
 */
@Entity
@Table(name = "wm_current_user_account")
public class CurrentUserAccount extends BaseEntity<Long> {


    // 用户ID
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    //总金额
    @Column(name = "all_fund")
    private BigDecimal allFund;

    //体验金
    @Column(name = "exp_fund")
    private BigDecimal expFund;

    //复利
    private BigDecimal profit;

    @Column(name = "dl_last_settlement_date")
    private Long dlLastSettlementDate;

    public CurrentUserAccount() {
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
}