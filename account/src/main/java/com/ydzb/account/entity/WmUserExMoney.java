package com.ydzb.account.entity;


import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "wm_user_expmoney")
public class WmUserExMoney extends BaseEntity<Long> {

    private static final long serialVersionUID = -8358888218477984298L;

    //用户
    @Column(name = "user_id")
    private Long userId;

    //体验金总额
    private BigDecimal amount;

    //体验金余额
    @Column(name = "able_money")
    private BigDecimal ableMoney;

    @Column(name = "blocked_money")
    private BigDecimal blockedMoney;

    //体验金已用
    @Column(name = "used_money")
    private BigDecimal usedMoney;

    public WmUserExMoney() {

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAbleMoney() {
        return ableMoney;
    }

    public void setAbleMoney(BigDecimal ableMoney) {
        this.ableMoney = ableMoney;
    }

    public BigDecimal getBlockedMoney() {
        return blockedMoney;
    }

    public void setBlockedMoney(BigDecimal blockedMoney) {
        this.blockedMoney = blockedMoney;
    }

    public BigDecimal getUsedMoney() {
        return usedMoney;
    }

    public void setUsedMoney(BigDecimal usedMoney) {
        this.usedMoney = usedMoney;
    }
}
