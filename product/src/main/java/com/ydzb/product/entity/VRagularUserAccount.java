package com.ydzb.product.entity;

import com.ydzb.user.entity.User;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Administrator on 15-9-15.
 */

/**
 * 用户定存宝记录视图
 */
@Entity
@Table(name = "vi_ragular_account")
public class VRagularUserAccount {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "one_month_fund")
    private BigDecimal oneMonthFund;

    @Column(name = "three_month_fund")
    private BigDecimal threeMonthFund;

    @Column(name = "six_month_fund")
    private BigDecimal sixMonthFund;

    @Column(name = "nine_month_fund")
    private BigDecimal nineMonthFund;

    @Column(name = "twelve_month_fund")
    private BigDecimal twelveMonthFund;

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getThreeMonthFund() {
        return threeMonthFund;
    }

    public void setThreeMonthFund(BigDecimal threeMonthFund) {
        this.threeMonthFund = threeMonthFund;
    }

    public BigDecimal getSixMonthFund() {
        return sixMonthFund;
    }

    public void setSixMonthFund(BigDecimal sixMonthFund) {
        this.sixMonthFund = sixMonthFund;
    }

    public BigDecimal getNineMonthFund() {
        return nineMonthFund;
    }

    public void setNineMonthFund(BigDecimal nineMonthFund) {
        this.nineMonthFund = nineMonthFund;
    }

    public BigDecimal getTwelveMonthFund() {
        return twelveMonthFund;
    }

    public void setTwelveMonthFund(BigDecimal twelveMonthFund) {
        this.twelveMonthFund = twelveMonthFund;
    }

    public BigDecimal getOneMonthFund() {
        return oneMonthFund;
    }

    public void setOneMonthFund(BigDecimal oneMonthFund) {
        this.oneMonthFund = oneMonthFund;
    }
}
