package com.ydzb.product.entity;

import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 定存宝每日支付收益
 */
@Entity
@Table(name = "wm_ragular_every_income")
public class RegularEveryIncome extends BaseEntity<Long> {

    private BigDecimal fund;

    private String sdate;

    public BigDecimal getFund() {
        return fund;
    }

    public void setFund(BigDecimal fund) {
        this.fund = fund;
    }

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }
}
