package com.ydzb.user.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 撤资用户  累计充值1000  账户总额100
 */
@Entity
@Table(name = "vi_divestment_user")
public class ViDivestmentUser extends BaseEntity<Long> {


    //用户
    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private User user;


    //累计提现
    @Column(name = "total_fund")
    private BigDecimal totalFund;


    //累计充值
    @Column(name = "all_recharge")
    private BigDecimal allRecharge;


    //撤资时间
    @Column(name = "application_time")
    private Long applicationTime;

    //撤资日期
    @Transient
    private Date applicationDate;


    public Date getApplicationDate() {
        return DateUtil.getSystemTimeMillisecond(applicationTime);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getTotalFund() {
        return totalFund;
    }

    public void setTotalFund(BigDecimal totalFund) {
        this.totalFund = totalFund;
    }

    public BigDecimal getAllRecharge() {
        return allRecharge;
    }

    public void setAllRecharge(BigDecimal allRecharge) {
        this.allRecharge = allRecharge;
    }

    public Long getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(Long applicationTime) {
        this.applicationTime = applicationTime;
    }
}