package com.ydzb.packet.entity;

import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "wm_user_silver")
public class SilverUser extends BaseEntity<Long> {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "total_fund")
    private Integer totalFund;

    @Column(name = "usable_fund")
    private Integer usableFund;


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
}