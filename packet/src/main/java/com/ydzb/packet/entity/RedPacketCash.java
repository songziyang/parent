package com.ydzb.packet.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 红包-现金
 */
@Entity
@Table(name = "wm_redpack_cash")
public class RedPacketCash extends BaseEntity<Long> {

    public static final byte STATE_INUSE = 0;   //状态-使用中
    public static final byte STATE_DISABLED = 1;    //状态-终止/停用
    public static final byte STATE_DELETED = -1;    //状态-删除

    /**
     * 名称
     */
    private String name;

    /**
     * 金额
     */
    private BigDecimal fund;

    /**
     * 触发类型：    0-手动    1-注册    2-充值    3-投资    4-推荐    5-普通推荐  6-VIP推荐 7-升级VIP
     */
    @Column(name = "trigger_type")
    private Byte triggerType;

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

    /**
     * 更新日期
     */
    private Long updated;

    /**
     * 更新用户
     */
    @Column(name = "update_user")
    private Long updateUser;

    private Byte status = STATE_INUSE;

    /**
     * 活动开始日期
     */
    @Transient
    private Date beginDate;

    /**
     * 活动结束日期
     */
    @Transient
    private Date finishDate;

    /**
     * 构造函数
     */
    public RedPacketCash() {

    }

    public BigDecimal getFund() {
        return fund;
    }

    public void setFund(BigDecimal fund) {
        this.fund = fund;
    }

    public Byte getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(Byte triggerType) {
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

    public Long getUpdated() {
        return updated;
    }

    public void setUpdated(Long updated) {
        this.updated = updated;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getBeginDate() {
        return DateUtil.getSystemTimeMillisecond(beginTime);
    }

    public Date getFinishDate() {
        return DateUtil.getSystemTimeMillisecond(finishTime);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}