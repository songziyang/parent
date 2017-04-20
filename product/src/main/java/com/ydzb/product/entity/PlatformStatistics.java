package com.ydzb.product.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 平台统计
 */
@Entity
@Table(name = "wm_platform_statistics")
@DynamicUpdate
public class PlatformStatistics extends BaseEntity<Long> {

    private static final long serialVersionUID = 9052257221813149987L;

    @Column(name = "total_fund")
    private Long totalFund;    //累计交易金额

    @Column(name = "total_revenue")
    private BigDecimal totalRevenue;    //累计收益

    @Column(name = "total_dayloan")
    private Long totalDayloan;    //活期累计交易金额

    @Column(name = "total_deposit")
    private Long totalDeposit;    //定存累计交易金额

    @Column(name = "dayloan_apr")
    private BigDecimal dayloanApr;    //活期年化利率

    @Column(name = "invest_num")
    private Long investNum;    //债权数量

    @Column(name = "invest_fund")
    private Long investFund;    //债权金额

    @Column(name = "platform_num")
    private Long platformNum;    //平台数量

    @Column(name = "invest_persons")
    private Long investPersons;    //投资人数

    @Column(name = "total_date")
    private Long totalTime;    //统计日期 毫秒数

    private Long created;    //创建时间

    @Column(name = "recharge_num")
    private Long rechargeNum;

    @Column(name = "current_persons")
    private Long currentPersons;   //活期宝购买人数

    @Column(name = "ragular_persons")
    private Long ragularPersons;   //定存宝购买人数

    /**
     * 活期宝投资金额一百以上人数
     */
    private Long currents;

    /**
     * 定存宝总人数
     */
    private Long ragulars;

    /**
     * 投资总人数
     */
    @Column(name = "total_persons")
    private Long totalPersons;

    @Transient
    private Date totalDate;    //统计日期


    public Long getTotalFund() {
        return totalFund;
    }

    public void setTotalFund(Long totalFund) {
        this.totalFund = totalFund;
    }

    public Long getTotalDayloan() {
        return totalDayloan;
    }

    public void setTotalDayloan(Long totalDayloan) {
        this.totalDayloan = totalDayloan;
    }

    public Long getTotalDeposit() {
        return totalDeposit;
    }

    public void setTotalDeposit(Long totalDeposit) {
        this.totalDeposit = totalDeposit;
    }

    public BigDecimal getDayloanApr() {
        return dayloanApr;
    }

    public void setDayloanApr(BigDecimal dayloanApr) {
        this.dayloanApr = dayloanApr;
    }

    public Long getInvestNum() {
        return investNum;
    }

    public void setInvestNum(Long investNum) {
        this.investNum = investNum;
    }

    public Long getInvestFund() {
        return investFund;
    }

    public void setInvestFund(Long investFund) {
        this.investFund = investFund;
    }

    public Long getPlatformNum() {
        return platformNum;
    }

    public void setPlatformNum(Long platformNum) {
        this.platformNum = platformNum;
    }

    public Long getInvestPersons() {
        return investPersons;
    }

    public void setInvestPersons(Long investPersons) {
        this.investPersons = investPersons;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Long totalTime) {
        this.totalTime = totalTime;
    }

    public Date getTotalDate() {
        return DateUtil.getSystemTimeMillisecond(totalTime);
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Long getRechargeNum() {
        return rechargeNum;
    }

    public void setRechargeNum(Long rechargeNum) {
        this.rechargeNum = rechargeNum;
    }

    public Long getCurrentPersons() {
        return currentPersons;
    }

    public void setCurrentPersons(Long currentPersons) {
        this.currentPersons = currentPersons;
    }

    public Long getRagularPersons() {
        return ragularPersons;
    }

    public void setRagularPersons(Long ragularPersons) {
        this.ragularPersons = ragularPersons;
    }

    public Long getCurrents() {
        return currents;
    }

    public void setCurrents(Long currents) {
        this.currents = currents;
    }

    public Long getRagulars() {
        return ragulars;
    }

    public void setRagulars(Long ragulars) {
        this.ragulars = ragulars;
    }

    public Long getTotalPersons() {
        return totalPersons;
    }

    public void setTotalPersons(Long totalPersons) {
        this.totalPersons = totalPersons;
    }
}