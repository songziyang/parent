package com.ydzb.packet.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.user.entity.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 体验金流水
 */
@Entity
@Table(name = "wm_user_expmoney_record")
public class ExpMoneyRecord extends BaseEntity<Long> {


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

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
     * 类型
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
     * 记录日期
     */
    @Transient
    private Date recordDate;

    public ExpMoneyRecord() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Date getRecordDate() {
        return DateUtil.getSystemTimeMillisecond(recordTime);
    }
}