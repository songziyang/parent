package com.ydzb.withdraw.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.user.entity.User;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "wm_withdraw_num")
public class WithdrawNum extends BaseEntity<Long> {

    //用户
    @OneToOne
    @JoinColumn(name = "user_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private User user;

    @Column(name = "count_num")
    private Integer countNum;

    @Column(name = "recharge_num")
    private Integer rechargeNum;

    private Long created;

    @Column(name = "recharge_time")
    private Long rechargeTime;

    @Column(name = "withdraw_time")
    private Long withdrawTime;

    //创建时间
    @Transient
    private Date createDate;

//    @Transient
//    private Date rechargeDate;

    @Transient
    private Date withdrawDate;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getCountNum() {
        return countNum;
    }

    public void setCountNum(Integer countNum) {
        this.countNum = countNum;
    }

    public Integer getRechargeNum() {
        return rechargeNum;
    }

    public void setRechargeNum(Integer rechargeNum) {
        this.rechargeNum = rechargeNum;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getRechargeTime() {
        return rechargeTime;
    }

    public void setRechargeTime(Long rechargeTime) {
        this.rechargeTime = rechargeTime;
    }

    public Long getWithdrawTime() {
        return withdrawTime;
    }

    public void setWithdrawTime(Long withdrawTime) {
        this.withdrawTime = withdrawTime;
    }

    public Date getCreateDate() {
        return DateUtil.getSystemTimeMillisecond(created);
    }

//    public Date getRechargeDate() {
//        return DateUtil.getSystemTimeMillisecond(rechargeTime);
//    }

    public Date getWithdrawDate() {
        return DateUtil.getSystemTimeMillisecond(withdrawTime);
    }
}