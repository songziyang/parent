package com.ydzb.user.entity;

import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * VIP等级
 */
@Entity
@Table(name = "wm_vip_grade")
public class VipGrade extends BaseEntity<Long> implements Comparable<VipGrade> {

    /**
     * 等级编码
     */
    @Column(name = "grade_no")
    private Integer gradeNum;

    /**
     * 等级名称
     */
    @Column(name = "grade_name")
    private String gradeName;


    //活期加息利率
    @Column(name = "dayloan_apr")
    private BigDecimal dayloanApr;

    //定存加息利率
    @Column(name = "deposit_apr")
    private BigDecimal depositApr;

    /**
     * 投资总额
     */
    @Column(name = "invest_all_fund")
    private BigDecimal investAllFund;

    /**
     * 购买限额
     */
    @Column(name = "buy_limit")
    private BigDecimal buyLimit;

    /**
     * 赎回限额
     */
    @Column(name = "redeem_limit")
    private BigDecimal redeemLimit;

    /**
     * 过期天数
     */
    @Column(name = "expire_days")
    private Integer expireDays;

    /**
     * 构造方法
     */
    public VipGrade() {

    }

    public VipGrade(Long id) {
        super.setId(id);
    }

    public Integer getGradeNum() {
        return gradeNum;
    }

    public void setGradeNum(Integer gradeNum) {
        this.gradeNum = gradeNum;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public BigDecimal getDayloanApr() {
        return dayloanApr;
    }

    public void setDayloanApr(BigDecimal dayloanApr) {
        this.dayloanApr = dayloanApr;
    }

    public BigDecimal getDepositApr() {
        return depositApr;
    }

    public void setDepositApr(BigDecimal depositApr) {
        this.depositApr = depositApr;
    }

    public BigDecimal getInvestAllFund() {
        return investAllFund;
    }

    public void setInvestAllFund(BigDecimal investAllFund) {
        this.investAllFund = investAllFund;
    }

    public BigDecimal getBuyLimit() {
        return buyLimit;
    }

    public void setBuyLimit(BigDecimal buyLimit) {
        this.buyLimit = buyLimit;
    }

    public BigDecimal getRedeemLimit() {
        return redeemLimit;
    }

    public void setRedeemLimit(BigDecimal redeemLimit) {
        this.redeemLimit = redeemLimit;
    }

    @Override
    public int compareTo(VipGrade o) {
        return this.getGradeNum().compareTo(o.getGradeNum());
    }

    public Integer getExpireDays() {
        return expireDays;
    }

    public void setExpireDays(Integer expireDays) {
        this.expireDays = expireDays;
    }
}