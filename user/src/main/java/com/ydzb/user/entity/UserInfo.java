package com.ydzb.user.entity;


import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "wm_user_info")
public class UserInfo extends BaseEntity<Long> {

    private static final long serialVersionUID = -8358888218477984298L;

    //用户
    @OneToOne
    @JoinColumn(name = "user_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private User user;

    //充值
    @Column(name = "all_recharge")
    private BigDecimal allRecharge;

    //提现
    @Column(name = "all_withdraw")
    private BigDecimal alliWthdraw;

    //提现手续费
    @Column(name = "mange_fee")
    private BigDecimal mangeFee;

    private Integer egg;    //绑卡错误次数

    @Column(name = "all_into")
    private BigDecimal allInto;    //总转入

    @Column(name = "all_outto")
    private BigDecimal allOutto;    //总转出

    @Column(name = "withdraw_frees")
    private Integer withdrawFrees;

    @Column(name = "withdraw_times")
    private Integer withdrawTimes;

    //风险测评分数
    private Integer signn;

    public UserInfo() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getAllRecharge() {
        return allRecharge;
    }

    public void setAllRecharge(BigDecimal allRecharge) {
        this.allRecharge = allRecharge;
    }

    public BigDecimal getAlliWthdraw() {
        return alliWthdraw;
    }

    public void setAlliWthdraw(BigDecimal alliWthdraw) {
        this.alliWthdraw = alliWthdraw;
    }

    public BigDecimal getMangeFee() {
        return mangeFee;
    }

    public void setMangeFee(BigDecimal mangeFee) {
        this.mangeFee = mangeFee;
    }

    public Integer getEgg() {
        return egg;
    }

    public void setEgg(Integer egg) {
        this.egg = egg;
    }

    public BigDecimal getAllInto() {
        return allInto;
    }

    public void setAllInto(BigDecimal allInto) {
        this.allInto = allInto;
    }

    public BigDecimal getAllOutto() {
        return allOutto;
    }

    public void setAllOutto(BigDecimal allOutto) {
        this.allOutto = allOutto;
    }

    public Integer getWithdrawFrees() {
        return withdrawFrees;
    }

    public void setWithdrawFrees(Integer withdrawFrees) {
        this.withdrawFrees = withdrawFrees;
    }

    public Integer getWithdrawTimes() {
        return withdrawTimes;
    }

    public void setWithdrawTimes(Integer withdrawTimes) {
        this.withdrawTimes = withdrawTimes;
    }

    public Integer getSignn() {
        return signn;
    }

    public void setSignn(Integer signn) {
        this.signn = signn;
    }

}
