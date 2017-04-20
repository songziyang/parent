package com.ydzb.account.entity;

import java.math.BigDecimal;

/**
 * 收益pojo
 */
public class WmIncomeEntity implements java.io.Serializable {

    BigDecimal income;  //收益

    BigDecimal expIncome;   //体验金收益

    BigDecimal vipIncome;   //vip加息收益

    BigDecimal interestIncome; //加息收益

    WmAprEntity aprEntity;  //利率实体

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public BigDecimal getVipIncome() {
        return vipIncome;
    }

    public void setVipIncome(BigDecimal vipIncome) {
        this.vipIncome = vipIncome;
    }

    public BigDecimal getInterestIncome() {
        return interestIncome;
    }

    public void setInterestIncome(BigDecimal interestIncome) {
        this.interestIncome = interestIncome;
    }

    public WmAprEntity getAprEntity() {
        return aprEntity;
    }

    public void setAprEntity(WmAprEntity aprEntity) {
        this.aprEntity = aprEntity;
    }

    public BigDecimal getExpIncome() {
        return expIncome;
    }

    public void setExpIncome(BigDecimal expIncome) {
        this.expIncome = expIncome;
    }
}