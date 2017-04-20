package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;


@Entity
@Table(name = "wm_platform_apr")
public class WmPlatformApr extends BaseEntity<Long> {

    //活期宝总收益
    @Column(name = "all_dayloan_income")
    private BigDecimal allDayloanIncome;

    //活期宝投资总额
    @Column(name =  "all_dayloan_fund")
    private BigDecimal allDayloanFund;

    //活期宝综合年化
    @Column(name =  "dayloan_apr")
    private BigDecimal dayloanApr;

    //定存宝总收益
    @Column(name =  "all_deposit_income")
    private BigDecimal allDepositIncome;

    //定存宝投资总额
    @Column(name =  "all_deposit_fund")
    private BigDecimal allDepositFund;

    //定存宝综合年化
    @Column(name =  "deposit_apr")
    private BigDecimal depositApr;

    //综合年化
    private BigDecimal apr;

    //创建时间
    private Long created;

    /**
     * 投资总人数
     */
    @Column(name = "total_persons")
    private Long totalPersons;

    public WmPlatformApr() {
    }

    public BigDecimal getAllDayloanIncome() {
        return allDayloanIncome;
    }

    public void setAllDayloanIncome(BigDecimal allDayloanIncome) {
        this.allDayloanIncome = allDayloanIncome;
    }

    public BigDecimal getAllDayloanFund() {
        return allDayloanFund;
    }

    public void setAllDayloanFund(BigDecimal allDayloanFund) {
        this.allDayloanFund = allDayloanFund;
    }

    public BigDecimal getDayloanApr() {
        return dayloanApr;
    }

    public void setDayloanApr(BigDecimal dayloanApr) {
        this.dayloanApr = dayloanApr;
    }

    public BigDecimal getAllDepositIncome() {
        return allDepositIncome;
    }

    public void setAllDepositIncome(BigDecimal allDepositIncome) {
        this.allDepositIncome = allDepositIncome;
    }

    public BigDecimal getAllDepositFund() {
        return allDepositFund;
    }

    public void setAllDepositFund(BigDecimal allDepositFund) {
        this.allDepositFund = allDepositFund;
    }

    public BigDecimal getDepositApr() {
        return depositApr;
    }

    public void setDepositApr(BigDecimal depositApr) {
        this.depositApr = depositApr;
    }

    public BigDecimal getApr() {
        return apr;
    }

    public void setApr(BigDecimal apr) {
        this.apr = apr;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getTotalPersons() {
        return totalPersons;
    }

    public void setTotalPersons(Long totalPersons) {
        this.totalPersons = totalPersons;
    }
}