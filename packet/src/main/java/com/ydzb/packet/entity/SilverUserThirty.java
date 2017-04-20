package com.ydzb.packet.entity;

import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 30亿活动用户银多币
 */
@Entity
@Table(name = "wm_user_silver_thirty")
@DynamicInsert
@DynamicUpdate
public class SilverUserThirty extends BaseEntity<Long> {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "total_fund")
    private Integer totalFund;

    @Column(name = "usable_fund")
    private Integer usableFund;

    private Long created;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }
}