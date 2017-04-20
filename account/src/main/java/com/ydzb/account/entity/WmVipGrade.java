package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;


@Entity
@Table(name = "wm_vip_grade")
public class WmVipGrade extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    //等级编码
    @Column(name = "grade_no")
    private Integer gradeNo;

    //等级名称
    @Column(name = "grade_name")
    private String gradeName;

    //活期加息利率
    @Column(name = "dayloan_apr")
    private BigDecimal dayloanApr;

    //定存加息利率
    @Column(name = "deposit_apr")
    private BigDecimal depositApr;

    //投资总额
    @Column(name = "invest_all_fund")
    private BigDecimal investAllFund;

    //购买限额
    @Column(name = "buy_limit")
    private BigDecimal buyLimit;

    //赎回限额
    @Column(name = "redeem_limit")
    private BigDecimal redeemLimit;

    public WmVipGrade(){

    }

    public Integer getGradeNo() {
        return gradeNo;
    }

    public void setGradeNo(Integer gradeNo) {
        this.gradeNo = gradeNo;
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
}
