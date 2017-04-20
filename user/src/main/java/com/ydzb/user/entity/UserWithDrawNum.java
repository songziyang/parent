package com.ydzb.user.entity;


import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "wm_user_withdraw_num")
public class UserWithDrawNum extends BaseEntity<Long> {

    private static final long serialVersionUID = -8358888218477984298L;

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

    //创建时间
    @Transient
    private Date createDate;

    public UserWithDrawNum() {
    }

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

    public Date getCreateDate() {
        return DateUtil.getSystemTimeMillisecond(created);
    }
}