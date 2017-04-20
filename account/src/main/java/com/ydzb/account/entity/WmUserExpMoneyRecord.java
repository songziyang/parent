package com.ydzb.account.entity;


import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;


@Entity
@Table(name = "wm_user_expmoney_record")
public class WmUserExpMoneyRecord extends BaseEntity<Long> {

    public static final int TYPE_FUNDIN = 1;    //类型-进账


    @Column(name = "user_id")
    private Long userId;

    //来源去向
    private String fundflow;

    //金额
    private BigDecimal fund;

    //类型 0出账1进账2冻结3解冻
    private Integer type;

    //余额
    private BigDecimal balance;

    //操作时间
    @Column(name = "record_time")
    private Long recordTime;

    @Column(name = "log_id")
    private Long logId;


    public WmUserExpMoneyRecord() {

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
