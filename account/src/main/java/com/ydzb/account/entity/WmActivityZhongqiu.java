package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 中秋节活动
 */
@Entity
@Table(name = "wm_activity_zhongqiu")
@DynamicInsert
@DynamicUpdate
public class WmActivityZhongqiu extends BaseEntity<Long> {

    /**
     * 用户
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 累计桂花
     */
    @Column(name = "total_fund")
    private Integer totalFund;

    /**
     * 可用桂花
     */
    @Column(name = "usable_fund")
    private Integer usableFund;


    /**
     * 一个月余额
     */
    private Integer one;

    /**
     * 三个月余额
     */
    private Integer three;

    /**
     * 六个月余额
     */
    private Integer six;

    /**
     * 十二个月余额
     */
    private Integer twelve;

    /**
     * 创建时间
     */
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

    public Integer getOne() {
        return one;
    }

    public void setOne(Integer one) {
        this.one = one;
    }

    public Integer getThree() {
        return three;
    }

    public void setThree(Integer three) {
        this.three = three;
    }

    public Integer getSix() {
        return six;
    }

    public void setSix(Integer six) {
        this.six = six;
    }

    public Integer getTwelve() {
        return twelve;
    }

    public void setTwelve(Integer twelve) {
        this.twelve = twelve;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }
}