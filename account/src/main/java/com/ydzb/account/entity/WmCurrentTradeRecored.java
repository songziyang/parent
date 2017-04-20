package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;


@Entity
@Table(name = "wm_current_traderecord")
public class WmCurrentTradeRecored extends BaseEntity<Long> {

    private static final long serialVersionUID = -3537051496328476177L;

    public static final int TYPE_PURCHASE = 1;  //类型-购买
    public static final int TYPE_REDEEM = 2;  //类型-赎回
    public static final int FUNDSOURCE_INCOME = 2;  //资金来源-收益
    public static final int FUNDSOURCE_PRIVILEGE = 3;  //资金来源-新手特权

    @Column(name = "user_id")
    private Long userId;

    private BigDecimal fund;

    private String names;

    @Column(name = "buy_time")
    private Long buyTime;

    private Integer type;

    @Column(name = "fund_source")
    private Integer fundSource;

    @Column(name = "log_id")
    private Long logId;


    public WmCurrentTradeRecored() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getFund() {
        return fund;
    }

    public void setFund(BigDecimal fund) {
        this.fund = fund;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public Long getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Long buyTime) {
        this.buyTime = buyTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getFundSource() {
        return fundSource;
    }

    public void setFundSource(Integer fundSource) {
        this.fundSource = fundSource;
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }
}