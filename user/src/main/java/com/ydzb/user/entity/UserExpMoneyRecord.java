package com.ydzb.user.entity;

import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 用户体验金记录(流水)
 * @author sy
 */
@Entity
@Table(name = "wm_user_expmoney_record")
public class UserExpMoneyRecord extends BaseEntity<Long> {

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 来源去向
     */
    @Column(name = "fundflow")
    private String fundFlow;

    /**
     * 体验金金额
     */
    private BigDecimal fund;

    /**
     * 用户体验金余额
     */
    private BigDecimal balance;

    /**
     * 类型   0-出账 1-进账   2-冻结    3-解冻
     */
    private Byte type;

    /**
     * 记录时间
     */
    @Column(name = "record_time")
    private Long recordTime;

    /**
     * 用户进账日志id
     */
    @Column(name = "log_id")
    private Long logId;

    /**
     * 构造函数
     */
    public UserExpMoneyRecord() {

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFundFlow() {
        return fundFlow;
    }

    public void setFundFlow(String fundFlow) {
        this.fundFlow = fundFlow;
    }

    public BigDecimal getFund() {
        return fund;
    }

    public void setFund(BigDecimal fund) {
        this.fund = fund;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
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