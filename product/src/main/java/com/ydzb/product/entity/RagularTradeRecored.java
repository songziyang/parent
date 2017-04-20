package com.ydzb.product.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.user.entity.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "wm_ragular_traderecord")
public class RagularTradeRecored extends BaseEntity<Long> {

    private static final long serialVersionUID = -3537051496328476177L;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Integer days;

    private BigDecimal fund;

    private String names;

    @Column(name = "buy_time")
    private Long buyTime;

    private Integer type;

    @Column(name = "fund_source")
    private Integer fundSource;

    @Column(name = "log_id")
    private Long logId;

    @Transient
    private Date buyDate;

    //创建时间
    @Transient
    private Date createDate;


    public RagularTradeRecored() {

    }


    public Date getCreateDate() {
        return DateUtil.getSystemTimeMillisecond(buyTime);
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
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

    public Date getBuyDate() {
        return DateUtil.getSystemTimeMillisecond(buyTime);
    }
}