package com.ydzb.account.entity;


import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 用户投资信息
 */
@Entity
@Table(name = "wm_user_investinfo")
@DynamicUpdate
@DynamicInsert
public class WmUserInvestinfo extends BaseEntity<Long> {

    private static final long serialVersionUID = -8358888218477984298L;

    //用户
    @Column(name = "user_id")
    private Long userId;

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

    //体验金投资总额
    @Column(name = "all_invest_invest")
    private BigDecimal allInvestInvest;

    //新手标投资总额
    @Column(name = "all_new_hand")
    private BigDecimal allNewHand;

    //自由定存投资总额
    @Column(name = "all_invest_free")
    private BigDecimal allInvestFree;

    /**
     * 新手特权投资总额
     */
    @Column(name = "all_invest_privilege")
    private BigDecimal allInvestPrivilege;

    /**
     * 定存类购买冻结金额
     */
    @Column(name = "all_buy_freeze")
    private BigDecimal allBuyFreeze;

    /**
     * 定存类赎回冻结金额
     */
    @Column(name = "all_redeem_freeze")
    private BigDecimal allRedeemFreeze;

    public WmUserInvestinfo() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public BigDecimal getAllNewHand() {
        return allNewHand;
    }

    public void setAllNewHand(BigDecimal allNewHand) {
        this.allNewHand = allNewHand;
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

    public BigDecimal getAllRedeemFreeze() {
        return allRedeemFreeze;
    }

    public void setAllRedeemFreeze(BigDecimal allRedeemFreeze) {
        this.allRedeemFreeze = allRedeemFreeze;
    }

    public BigDecimal getAllBuyFreeze() {
        return allBuyFreeze;
    }

    public void setAllBuyFreeze(BigDecimal allBuyFreeze) {
        this.allBuyFreeze = allBuyFreeze;
    }
}
