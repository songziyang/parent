package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 国庆活动
 */
@Entity
@Table(name = "wm_activity_shiyi")
@DynamicInsert
@DynamicUpdate
public class WmActivityGuoqing extends BaseEntity<Long> {

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 累计获得福袋
     */
    @Column(name = "total_fund")
    private Integer totalFund;

    /**
     * 可用福袋
     */
    @Column(name = "usable_fund")
    private Integer usableFund;

    /**
     * 金福袋
     */
    @Column(name = "gold_fund")
    private Integer goldFund;

    private Integer one = 0;    //多余金额

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

    public Integer getOne() {
        return one;
    }

    public void setOne(Integer one) {
        this.one = one;
    }

    public Integer getGoldFund() {
        return goldFund;
    }

    public void setGoldFund(Integer goldFund) {
        this.goldFund = goldFund;
    }
}