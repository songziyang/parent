package com.ydzb.product.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 平台交易统计详细
 * @author sy
 */
@Entity
@Table(name = "wm_platform_trading_deal")
public class PlatformTradingDeal extends BaseEntity<Long> {

    /**
     * 平台交易详细
     */
    @ManyToOne
    @JoinColumn(name = "trade_id")
    private PlatformTrading platformTrading;

    /**
     * 产品名称
     */
    private String name;

    /**
     * 金额
     */
    private BigDecimal fund;

    /**
     * 操作日期毫秒数
     */
    @Column(name = "opdate")
    private Long operationTime;

    /**
     * 操作日期
     */
    @Transient
    private Date operationDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getFund() {
        return fund;
    }

    public void setFund(BigDecimal fund) {
        this.fund = fund;
    }

    public Long getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Long operationTime) {
        this.operationTime = operationTime;
    }

    public Date getOperationDate() {
        return DateUtil.getSystemTimeMillisecond(operationTime);
    }

    public PlatformTrading getPlatformTrading() {
        return platformTrading;
    }

    public void setPlatformTrading(PlatformTrading platformTrading) {
        this.platformTrading = platformTrading;
    }
}