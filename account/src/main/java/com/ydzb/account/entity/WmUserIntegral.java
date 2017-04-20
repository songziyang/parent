package com.ydzb.account.entity;


import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 用户积分
 */
@Entity
@Table(name = "wm_user_integral")
public class WmUserIntegral extends BaseEntity<Long> {

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;

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

    public WmUserIntegral() {

    }

    public WmUserIntegral(Long userId, Integer vip, BigDecimal integral, BigDecimal totalIntegral) {
        this.userId = userId;
        this.vip = vip;
        this.integral = integral;
        this.totalIntegral = totalIntegral;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
