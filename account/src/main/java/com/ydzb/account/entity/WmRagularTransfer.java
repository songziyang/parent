package com.ydzb.account.entity;


import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "wm_ragular_transfer")
@DynamicInsert
@DynamicUpdate
public class WmRagularTransfer extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    public static final int STATUS_TRANSFERING = 1; //状态-转让中
    public static final int STATUS_TRANSFER_SUCCESS = 2;    //状态-转让成功
    public static final int STATUS_TRANSFER_FAIL = 3;   //状态-转让失败

    //用户定存记录id
    @Column(name = "account_id")
    private Long accountId;

    //用户id
    @Column(name = "apply_user_id")
    private Long applyUserId;

    //申请时间
    @Column(name = "apply_time")
    private Long applyTime;

    //新定存记录id
    @Column(name = "new_acc_id")
    private Long newAccId;

    //购买人id
    @Column(name = "accept_user_id")
    private Long acceptUserId;

    //操作时间
    @Column(name = "accept_time")
    private Long acceptTime;

    @Column(name = "all_fund")
    private BigDecimal allFund;

    @Column(name = "invest_fund")
    private BigDecimal investFund;

    @Column(name = "trans_fund")
    private BigDecimal transFund;

    @Column(name = "trans_count")
    private Integer transCount;

    private Integer days;

    private BigDecimal revenues;

    @Column(name = "have_refund")
    private BigDecimal haveRefund;

    @Column(name = "have_refund_stage")
    private BigDecimal haveRefundStage;

    //1、转让中、2转让成功3、转让失败
    private Integer status;

    public WmRagularTransfer() {

    }


    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getApplyUserId() {
        return applyUserId;
    }

    public void setApplyUserId(Long applyUserId) {
        this.applyUserId = applyUserId;
    }

    public Long getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Long applyTime) {
        this.applyTime = applyTime;
    }

    public Long getNewAccId() {
        return newAccId;
    }

    public void setNewAccId(Long newAccId) {
        this.newAccId = newAccId;
    }

    public Long getAcceptUserId() {
        return acceptUserId;
    }

    public void setAcceptUserId(Long acceptUserId) {
        this.acceptUserId = acceptUserId;
    }

    public Long getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(Long acceptTime) {
        this.acceptTime = acceptTime;
    }

    public BigDecimal getAllFund() {
        return allFund;
    }

    public void setAllFund(BigDecimal allFund) {
        this.allFund = allFund;
    }

    public BigDecimal getInvestFund() {
        return investFund;
    }

    public void setInvestFund(BigDecimal investFund) {
        this.investFund = investFund;
    }

    public BigDecimal getTransFund() {
        return transFund;
    }

    public void setTransFund(BigDecimal transFund) {
        this.transFund = transFund;
    }

    public Integer getTransCount() {
        return transCount;
    }

    public void setTransCount(Integer transCount) {
        this.transCount = transCount;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public BigDecimal getRevenues() {
        return revenues;
    }

    public void setRevenues(BigDecimal revenues) {
        this.revenues = revenues;
    }

    public BigDecimal getHaveRefund() {
        return haveRefund;
    }

    public void setHaveRefund(BigDecimal haveRefund) {
        this.haveRefund = haveRefund;
    }

    public BigDecimal getHaveRefundStage() {
        return haveRefundStage;
    }

    public void setHaveRefundStage(BigDecimal haveRefundStage) {
        this.haveRefundStage = haveRefundStage;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}