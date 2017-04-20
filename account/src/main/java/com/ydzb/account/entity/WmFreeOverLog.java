package com.ydzb.account.entity;


import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "wm_free_overlog")
public class WmFreeOverLog extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    public static final int TYPE_EXPIRE = 0;    //产品到期
    public static final int TYPE_TRANSFER = 1;  //产品转让

    //用户id
    @Column(name = "user_id")
    private Long userId;

    //用户定存记录id
    @Column(name = "account_id")
    private Long accountId;

    //0-产品到期，1-产品转让
    private Integer type;

    private BigDecimal fund;

    //记录时间(带时分秒)
    @Column(name = "log_time")
    private Long logTime;

    @Column(name = "grand_fund")
    private BigDecimal grandFund;

    public WmFreeOverLog() {

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getFund() {
        return fund;
    }

    public void setFund(BigDecimal fund) {
        this.fund = fund;
    }

    public Long getLogTime() {
        return logTime;
    }

    public void setLogTime(Long logTime) {
        this.logTime = logTime;
    }

    public BigDecimal getGrandFund() {
        return grandFund;
    }

    public void setGrandFund(BigDecimal grandFund) {
        this.grandFund = grandFund;
    }
}