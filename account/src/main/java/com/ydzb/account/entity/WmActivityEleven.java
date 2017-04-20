package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 中秋节活动
 */
@Entity
@Table(name = "wm_activity_eleven")
@DynamicInsert
@DynamicUpdate
public class WmActivityEleven extends BaseEntity<Long> {

    /**
     * 用户
     */
    @Column(name = "user_id")
    private Long userId;

    private String name;

    private BigDecimal money;

    private BigDecimal redpacket;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getRedpacket() {
        return redpacket;
    }

    public void setRedpacket(BigDecimal redpacket) {
        this.redpacket = redpacket;
    }
}