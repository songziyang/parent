package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 红包-现金
 */
@Entity
@Table(name = "wm_redpack_cash")
@DynamicInsert
@DynamicUpdate
public class WmRedPacketCash extends BaseEntity<Long> {

    public static final int TRIGGERTYPE_DOUBLEELEVENCASH = 45;  //双十一活动-现金红包

    /**
     * 金额
     */
    private BigDecimal fund;

    /**
     * 触发类型：    0-手动    1-注册    2-充值    3-投资    4-推荐    5-普通推荐  6-VIP推荐 7-升级VIP 8
     */
    @Column(name = "trigger_type")
    private Integer triggerType;

    /**
     * 使用有效天数
     */
    @Column(name = "use_days")
    private Integer useDays;

    /**
     * 活动开始时间
     */
    @Column(name = "begin_date")
    private Long beginTime;

    /**
     * 活动结束时间
     */
    @Column(name = "end_date")
    private Long finishTime;

    /**
     * 创建时间
     */
    private Long created;

    /**
     * 创建用户
     */
    @Column(name = "create_user")
    private Long createdUser;


    private Integer status;

    private String name;

    /**
     * 构造函数
     */
    public WmRedPacketCash() {

    }

    public BigDecimal getFund() {
        return fund;
    }

    public void setFund(BigDecimal fund) {
        this.fund = fund;
    }

    public Integer getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(Integer triggerType) {
        this.triggerType = triggerType;
    }

    public Integer getUseDays() {
        return useDays;
    }

    public void setUseDays(Integer useDays) {
        this.useDays = useDays;
    }

    public Long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Long beginTime) {
        this.beginTime = beginTime;
    }

    public Long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Long finishTime) {
        this.finishTime = finishTime;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(Long createdUser) {
        this.createdUser = createdUser;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}