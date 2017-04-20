package com.ydzb.user.entity;

import com.ydzb.admin.entity.Admin;
import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "wm_manual_recharge")
public class UserManualRecharge extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private User user;

    private BigDecimal fund;

    @Column(name = "usable_fund")
    private BigDecimal usableFund;

    @Column(name = "operate_time")
    private Long operateTime;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private Admin admin;

    private String remark;

    @Transient
    private Date operateDate;

    public UserManualRecharge() {
    }

    public Date getOperateDate() {
        return DateUtil.getSystemTimeMillisecond(operateTime);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getFund() {
        return fund;
    }

    public void setFund(BigDecimal fund) {
        this.fund = fund;
    }

    public BigDecimal getUsableFund() {
        return usableFund;
    }

    public void setUsableFund(BigDecimal usableFund) {
        this.usableFund = usableFund;
    }

    public Long getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Long operateTime) {
        this.operateTime = operateTime;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
