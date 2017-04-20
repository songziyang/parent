package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;


@Entity
@Table(name = "wm_platform_trading_deal")
public class WmPlatformTradingDeal extends BaseEntity<Long> {


    /**
     * 平台交易ID
     */
    @Column(name = "trade_id")
    private Long tradeId;

    /**
     * 产品名称
     */
    private String name;

    /**
     * 金额
     */
    private BigDecimal fund;

    /**
     * 操作日期
     */
    private Long opdate;


    public WmPlatformTradingDeal() {

    }

    public Long getTradeId() {
        return tradeId;
    }

    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
    }

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

    public Long getOpdate() {
        return opdate;
    }

    public void setOpdate(Long opdate) {
        this.opdate = opdate;
    }
}
