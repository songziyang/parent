package com.ydzb.account.entity;


import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "wm_user_money")
public class WmUserMoney extends BaseEntity<Long> {

    private static final long serialVersionUID = -8358888218477984298L;

    //用户
    @Column(name = "user_id")
    private Long userId;

    //账号总额
    @Column(name = "total_fund")
    private BigDecimal totalFund;

    //账号余额
    @Column(name = "usable_fund")
    private BigDecimal usableFund;

    //冻结金额
    @Column(name = "freeze_fund")
    private BigDecimal freezeFund;

    public WmUserMoney() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalFund() {
        return totalFund;
    }

    public void setTotalFund(BigDecimal totalFund) {
        this.totalFund = totalFund;
    }

    public BigDecimal getUsableFund() {
        return usableFund;
    }

    public void setUsableFund(BigDecimal usableFund) {
        this.usableFund = usableFund;
    }

    public BigDecimal getFreezeFund() {
        return freezeFund;
    }

    public void setFreezeFund(BigDecimal freezeFund) {
        this.freezeFund = freezeFund;
    }
}
