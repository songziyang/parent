package com.ydzb.packet.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.user.entity.User;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

/**
 * 国庆活动
 */
@Entity
@Table(name = "wm_activity_shiyi")
@DynamicInsert
@DynamicUpdate
public class ActivityGuoqing extends BaseEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private User user;

    @Column(name = "total_fund")
    private Integer totalFund;

    @Column(name = "usable_fund")
    private Integer usableFund;

    @Column(name = "gold_fund")
    private Integer goldFund;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getTotalFund() {
        return totalFund;
    }

    public void setTotalFund(Integer totalFund) {
        this.totalFund = totalFund;
    }

    public Integer getUsableFund() {
        return usableFund;
    }

    public void setUsableFund(Integer usableFund) {
        this.usableFund = usableFund;
    }

    public Integer getGoldFund() {
        return goldFund;
    }

    public void setGoldFund(Integer goldFund) {
        this.goldFund = goldFund;
    }
}