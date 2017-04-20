package com.ydzb.user.entity;


import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "wm_user_investinfo")
public class UserInvestinfo extends BaseEntity<Long> {

    private static final long serialVersionUID = -8358888218477984298L;

    //用户
    @OneToOne
    @JoinColumn(name = "user_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private User user;

    //投资总额
    @Column(name = "all_invest")
    private BigDecimal allInvest;

    //活期投资总额
    @Column(name = "all_invest_dayloan")
    private BigDecimal allInvestDayloan;

    //定存投资总额
    @Column(name = "all_invest_deposit")
    private BigDecimal allInvestDeposit;

    //稳进宝投资总额
    @Column(name = "all_invest_wjb")
    private BigDecimal allInvestWjb;

    //用户投资汇总
    @Column(name = "all_invest_self")
    private BigDecimal allInvestSelf;

    //债权转让
    @Column(name = "all_invest_transfer")
    private BigDecimal allInvestTransfer;

    /**
     * all_invest_privilege新手宝投资总额
     */
    @Column(name = "all_invest_privilege")
    private BigDecimal allInvestPrivilege;

    /**
     * 随心存投资总额
     */
    @Column(name = "all_invest_free")
    private BigDecimal allInvestFree;

    @Column(name = "all_invest_invest")
    private BigDecimal allInvestInvest;

    @Column(name = "buy_limit")
    private Integer buyLimit;

    @Column(name = "redeem_limit")
    private Integer redeemLimit;

    public UserInvestinfo() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getAllInvest() {
        return allInvest;
    }

    public void setAllInvest(BigDecimal allInvest) {
        this.allInvest = allInvest;
    }

    public BigDecimal getAllInvestDayloan() {
        return allInvestDayloan;
    }

    public void setAllInvestDayloan(BigDecimal allInvestDayloan) {
        this.allInvestDayloan = allInvestDayloan;
    }

    public BigDecimal getAllInvestDeposit() {
        return allInvestDeposit;
    }

    public void setAllInvestDeposit(BigDecimal allInvestDeposit) {
        this.allInvestDeposit = allInvestDeposit;
    }

    public BigDecimal getAllInvestWjb() {
        return allInvestWjb;
    }

    public void setAllInvestWjb(BigDecimal allInvestWjb) {
        this.allInvestWjb = allInvestWjb;
    }

    public BigDecimal getAllInvestSelf() {
        return allInvestSelf;
    }

    public void setAllInvestSelf(BigDecimal allInvestSelf) {
        this.allInvestSelf = allInvestSelf;
    }

    public BigDecimal getAllInvestTransfer() {
        return allInvestTransfer;
    }

    public void setAllInvestTransfer(BigDecimal allInvestTransfer) {
        this.allInvestTransfer = allInvestTransfer;
    }

    public BigDecimal getAllInvestInvest() {
        return allInvestInvest;
    }

    public void setAllInvestInvest(BigDecimal allInvestInvest) {
        this.allInvestInvest = allInvestInvest;
    }

    public Integer getBuyLimit() {
        return buyLimit;
    }

    public void setBuyLimit(Integer buyLimit) {
        this.buyLimit = buyLimit;
    }

    public Integer getRedeemLimit() {
        return redeemLimit;
    }

    public void setRedeemLimit(Integer redeemLimit) {
        this.redeemLimit = redeemLimit;
    }

    public BigDecimal getAllInvestFree() {
        return allInvestFree;
    }

    public void setAllInvestFree(BigDecimal allInvestFree) {
        this.allInvestFree = allInvestFree;
    }

    public BigDecimal getAllInvestPrivilege() {
        return allInvestPrivilege;
    }

    public void setAllInvestPrivilege(BigDecimal allInvestPrivilege) {
        this.allInvestPrivilege = allInvestPrivilege;
    }
}
