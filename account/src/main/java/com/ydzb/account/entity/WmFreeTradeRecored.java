package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;


@Entity
@Table(name = "wm_free_traderecord")
public class WmFreeTradeRecored extends BaseEntity<Long> {

    private static final long serialVersionUID = -3537051496328476177L;

    public static final int TYPE_PURCHASE = 1;  //类型-购买
    public static final int TYPE_EXPIRE = 2;  //类型-到期
    public static final int TYPE_RECAST = 3;  //类型-复投
    public static final int TYPE_TRANSFER = 4;  //类型-债权转让

    public static final int FUNDSOURCE_UNSABLE = 0; //资金来源-资金余额

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

    private Integer days;


    public WmFreeTradeRecored() {
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

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }
}