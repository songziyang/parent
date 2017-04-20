package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 新手特权标交易记录
 */
@Entity
@Table(name = "wm_privilege_traderecord")
@DynamicInsert
@DynamicUpdate
public class WmPrivilegeTradeRecord extends BaseEntity<Long> {

    public static final int TYPE_PURCHASE = 1;  //类型-购买
    public static final int TYPE_REDEEM = 2;  //类型-赎回
    public static final int FUNDSOURCE_USABLE = 0;  //资金来源-资金余额
    public static final int FUNDSOURCE_INCOME = 1;  //资金来源-收益
    public static final int FUNDSOURCE_EXPIREFUND = 2;  //资金来源-到期本息

    /**
     * 用户Id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 购买时间
     */
    @Column(name = "buy_time")
    private Long buyTime;

    /**
     * 资金来源
     */
    @Column(name = "fund_source")
    private Integer fundSource;
    /**
     * 日志id
     */
    @Column(name = "log_id")
    private Long logId;

    private String names;   //来源名称
    private BigDecimal fund;    //金额
    private Integer type;   //类型

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Long buyTime) {
        this.buyTime = buyTime;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}