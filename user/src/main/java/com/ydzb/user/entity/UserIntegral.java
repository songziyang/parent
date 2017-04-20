package com.ydzb.user.entity;


import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 用户积分
 */
@Entity
@Table(name = "wm_user_integral")
public class UserIntegral extends BaseEntity<Long> {

    /**
     * 用户ID
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private User user;

    /**
     * 累计积分
     */
    @Column(name = "total_integral")
    private BigDecimal totalIntegral;

    /**
     * 可用积分
     */
    private BigDecimal integral;

    /**
     * 0、VIP未给 1、VIP已给
     */
    private Integer vip;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getTotalIntegral() {
        return totalIntegral;
    }

    public void setTotalIntegral(BigDecimal totalIntegral) {
        this.totalIntegral = totalIntegral;
    }

    public BigDecimal getIntegral() {
        return integral;
    }

    public void setIntegral(BigDecimal integral) {
        this.integral = integral;
    }

    public Integer getVip() {
        return vip;
    }

    public void setVip(Integer vip) {
        this.vip = vip;
    }
}
