package com.ydzb.product.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.user.entity.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "wm_current_traderecord")
public class CurrentTradeRecored extends BaseEntity<Long> {

    private static final long serialVersionUID = -3537051496328476177L;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String names;

    private BigDecimal fund;

    @Column(name = "buy_time")
    private Long buyTime;

    private Integer type;

    @Column(name = "fund_source")
    private Integer fundSource;

    @Transient
    private Date buyDate;

    //创建时间
    @Transient
    private Date createDate;

    public CurrentTradeRecored() {
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

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public BigDecimal getFund() {
        return fund;
    }

    public void setFund(BigDecimal fund) {
        this.fund = fund;
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

    public Date getBuyDate() {
        return DateUtil.getSystemTimeMillisecond(buyTime);
    }
}