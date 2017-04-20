package com.ydzb.user.entity;


import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "wm_user_expmoney")
public class UserExMoney extends BaseEntity<Long> {

    private static final long serialVersionUID = -8358888218477984298L;

    //用户
    @OneToOne
    @JoinColumn(name = "user_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private User user;


    //体验金总额
    private BigDecimal amount;

    //体验金余额
    @Column(name = "able_money")
    private BigDecimal ableMoney;

    //体验金已用
    @Column(name = "used_money")
    private BigDecimal usedMoney;

    @Column(name = "blocked_money")
    private BigDecimal blockedMoney;

    public UserExMoney() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public BigDecimal getUsedMoney() {
        return usedMoney;
    }

    public void setUsedMoney(BigDecimal usedMoney) {
        this.usedMoney = usedMoney;
    }

    public BigDecimal getBlockedMoney() {
        return blockedMoney;
    }

    public void setBlockedMoney(BigDecimal blockedMoney) {
        this.blockedMoney = blockedMoney;
    }
}
