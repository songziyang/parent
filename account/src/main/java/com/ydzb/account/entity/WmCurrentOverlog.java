package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;


@Entity
@Table(name = "wm_current_overlog")
public class WmCurrentOverlog extends BaseEntity<Long> {

    private static final long serialVersionUID = -3537051496328476177L;

    @Column(name = "user_id")
    private Long userId;

    private Integer type;

    @Column(name = "redemption_fund")
    private BigDecimal redemptionFund;

    @Column(name = "redemption_time")
    private Long redemptionTime;

    @Column(name = "exp_fund")
    private BigDecimal expFund;


    public WmCurrentOverlog() {
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getRedemptionFund() {
        return redemptionFund;
    }

    public void setRedemptionFund(BigDecimal redemptionFund) {
        this.redemptionFund = redemptionFund;
    }

    public Long getRedemptionTime() {
        return redemptionTime;
    }

    public void setRedemptionTime(Long redemptionTime) {
        this.redemptionTime = redemptionTime;
    }

    public BigDecimal getExpFund() {
        return expFund;
    }

    public void setExpFund(BigDecimal expFund) {
        this.expFund = expFund;
    }
}