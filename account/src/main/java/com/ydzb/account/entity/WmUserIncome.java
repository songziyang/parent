package com.ydzb.account.entity;


import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "wm_user_income")
public class WmUserIncome extends BaseEntity<Long> {

    private static final long serialVersionUID = -8358888218477984298L;

    //用户
    @Column(name = "user_id")
    private Long userId;

    //累计收益总额
    @Column(name = "all_income")
    private BigDecimal allIncome;

    //活期收益
    @Column(name = "all_income_dayloan")
    private BigDecimal allIncomeDayloan;

    //定存收益
    @Column(name = "all_income_deposit")
    private BigDecimal allIncomeDeposit;

    //稳进宝收益
    @Column(name = "all_income_wjb")
    private BigDecimal allIncomeWjb;

    //私人定制收益
    @Column(name = "all_income_self")
    private BigDecimal allIncomeSelf;

    //活动收益
    @Column(name = "all_income_activity")
    private BigDecimal allIncomeActivity;

    //加息收益
    @Column(name = "all_income_interest")
    private BigDecimal allIncomeInterest;

    //体验金收益
    @Column(name = "all_income_invest")
    private BigDecimal allIncomeInvest;

    //昨日收益
    @Column(name = "yesterday_income")
    private BigDecimal yesterdayIncome;

    //预期收益
    @Column(name = "predict_income")
    private BigDecimal predictIncome;

    //VIP收益
    @Column(name = "all_income_vip")
    private BigDecimal allIncomeVip;

    //推荐人收益
    @Column(name = "all_income_recommend")
    private BigDecimal allIncomeRecommend;

    //代金券收益
    @Column(name = "all_income_vouchers")
    private BigDecimal allIncomeVouchers;

    //自由定存收益
    @Column(name = "all_income_free")
    private BigDecimal allIncomeFree;

    //自由定存预期收益
    @Column(name = "free_income")
    private BigDecimal freeIncome;

    /**
     * 新手特权收益
     */
    @Column(name = "all_income_privilege")
    private BigDecimal allIncomePrivilege;


    public WmUserIncome() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getAllIncome() {
        return allIncome;
    }

    public void setAllIncome(BigDecimal allIncome) {
        this.allIncome = allIncome;
    }

    public BigDecimal getAllIncomeDayloan() {
        return allIncomeDayloan;
    }

    public void setAllIncomeDayloan(BigDecimal allIncomeDayloan) {
        this.allIncomeDayloan = allIncomeDayloan;
    }

    public BigDecimal getAllIncomeDeposit() {
        return allIncomeDeposit;
    }

    public void setAllIncomeDeposit(BigDecimal allIncomeDeposit) {
        this.allIncomeDeposit = allIncomeDeposit;
    }

    public BigDecimal getAllIncomeWjb() {
        return allIncomeWjb;
    }

    public void setAllIncomeWjb(BigDecimal allIncomeWjb) {
        this.allIncomeWjb = allIncomeWjb;
    }

    public BigDecimal getAllIncomeSelf() {
        return allIncomeSelf;
    }

    public void setAllIncomeSelf(BigDecimal allIncomeSelf) {
        this.allIncomeSelf = allIncomeSelf;
    }

    public BigDecimal getAllIncomeActivity() {
        return allIncomeActivity;
    }

    public void setAllIncomeActivity(BigDecimal allIncomeActivity) {
        this.allIncomeActivity = allIncomeActivity;
    }

    public BigDecimal getAllIncomeInterest() {
        return allIncomeInterest;
    }

    public void setAllIncomeInterest(BigDecimal allIncomeInterest) {
        this.allIncomeInterest = allIncomeInterest;
    }

    public BigDecimal getAllIncomeInvest() {
        return allIncomeInvest;
    }

    public void setAllIncomeInvest(BigDecimal allIncomeInvest) {
        this.allIncomeInvest = allIncomeInvest;
    }

    public BigDecimal getYesterdayIncome() {
        return yesterdayIncome;
    }

    public void setYesterdayIncome(BigDecimal yesterdayIncome) {
        this.yesterdayIncome = yesterdayIncome;
    }

    public BigDecimal getPredictIncome() {
        return predictIncome;
    }

    public void setPredictIncome(BigDecimal predictIncome) {
        this.predictIncome = predictIncome;
    }

    public BigDecimal getAllIncomeVip() {
        return allIncomeVip;
    }

    public void setAllIncomeVip(BigDecimal allIncomeVip) {
        this.allIncomeVip = allIncomeVip;
    }

    public BigDecimal getAllIncomeRecommend() {
        return allIncomeRecommend;
    }

    public void setAllIncomeRecommend(BigDecimal allIncomeRecommend) {
        this.allIncomeRecommend = allIncomeRecommend;
    }

    public BigDecimal getAllIncomeVouchers() {
        return allIncomeVouchers;
    }

    public void setAllIncomeVouchers(BigDecimal allIncomeVouchers) {
        this.allIncomeVouchers = allIncomeVouchers;
    }

    public BigDecimal getAllIncomeFree() {
        return allIncomeFree;
    }

    public void setAllIncomeFree(BigDecimal allIncomeFree) {
        this.allIncomeFree = allIncomeFree;
    }

    public BigDecimal getFreeIncome() {
        return freeIncome;
    }

    public void setFreeIncome(BigDecimal freeIncome) {
        this.freeIncome = freeIncome;
    }

    public BigDecimal getAllIncomePrivilege() {
        return allIncomePrivilege;
    }

    public void setAllIncomePrivilege(BigDecimal allIncomePrivilege) {
        this.allIncomePrivilege = allIncomePrivilege;
    }
}
