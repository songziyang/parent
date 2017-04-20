package com.ydzb.user.entity;

import com.ydzb.core.utils.DateUtil;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 频繁充值提现
 */
@Entity
@Table(name = "vi_often_recharge_withdraw")
@DynamicInsert
@DynamicUpdate
public class ViOftenRechargeWithdraw {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private User user;

    @Column(name = "recharge_money")
    private BigDecimal rechargeMoney;

    @Column(name = "recharge_time")
    private Long rechargeTime;

    @Column(name = "withdraw_money")
    private BigDecimal withdrawMoney;

    @Column(name = "withdraw_time")
    private Long withdrawTime;

    @Transient
    private Date rechargeDate;

    @Transient
    private Date withdrawDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getRechargeMoney() {
        return rechargeMoney;
    }

    public void setRechargeMoney(BigDecimal rechargeMoney) {
        this.rechargeMoney = rechargeMoney;
    }

    public Long getRechargeTime() {
        return rechargeTime;
    }

    public void setRechargeTime(Long rechargeTime) {
        this.rechargeTime = rechargeTime;
    }

    public BigDecimal getWithdrawMoney() {
        return withdrawMoney;
    }

    public void setWithdrawMoney(BigDecimal withdrawMoney) {
        this.withdrawMoney = withdrawMoney;
    }

    public Long getWithdrawTime() {
        return withdrawTime;
    }

    public void setWithdrawTime(Long withdrawTime) {
        this.withdrawTime = withdrawTime;
    }

    public Date getRechargeDate() {
        return DateUtil.getSystemTimeMillisecond(rechargeTime);
    }

    public Date getWithdrawDate() {
        return DateUtil.getSystemTimeMillisecond(withdrawTime);
    }
}