package com.ydzb.user.entity;


import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "wm_user_income")
public class UserIncome extends BaseEntity<Long> {

    private static final long serialVersionUID = -8358888218477984298L;

    //用户
    @OneToOne
    @JoinColumn(name = "user_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private User user;


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

    @Column(name = "all_income_vip")
    private BigDecimal allIncomeVip;

    public UserIncome() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public BigDecimal getAllIncomeVip() {
        return allIncomeVip;
    }

    public void setAllIncomeVip(BigDecimal allIncomeVip) {
        this.allIncomeVip = allIncomeVip;
    }
}
