package com.ydzb.user.entity;


import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "wm_user_fund_record")
public class UserFundRecord extends BaseEntity<Long> {


    @Column(name = "user_id")
    private Long userId;

    //来源去向
    private String fundflow;

    //金额
    private BigDecimal fund;

    //类型 0-出账 （红色） 1-入账（蓝色）
    private Integer type;

    // 资金类别 0-充值 1-体现 2－收益 3-本金赎回 4-活动收入（现金红包）
    @Column(name = "fund_type")
    private Integer fundType;

    //余额
    private BigDecimal balance;

    //操作时间
    @Column(name = "record_time")
    private Long recordTime;

    @Column(name = "log_id")
    private Long logId;

    //创建时间
    @Transient
    private Date recordDate;

    public Date getRecordDate() {
        return DateUtil.getSystemTimeMillisecond(recordTime);
    }

    public UserFundRecord() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFundflow() {
        return fundflow;
    }

    public void setFundflow(String fundflow) {
        this.fundflow = fundflow;
    }

    public BigDecimal getFund() {
        return fund;
    }

    public void setFund(BigDecimal fund) {
        this.fund = fund;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getFundType() {
        return fundType;
    }

    public void setFundType(Integer fundType) {
        this.fundType = fundType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Long getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Long recordTime) {
        this.recordTime = recordTime;
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }
}
