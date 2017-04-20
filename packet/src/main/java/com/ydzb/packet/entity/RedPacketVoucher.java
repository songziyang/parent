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
 * 红包-代金券
 */
@Entity
@Table(name = "wm_redpack_vouchers")
public class RedPacketVoucher extends BaseEntity<Long> {

    /**
     * 金额
     */
    private BigDecimal fund;

    /**
     * 满可用金额
     */
    @Column(name = "limit_fund")
    private Long limitFund;

    /**
     * 红包名称
     */
    private String name;

    /**
     * 触发类型
     */
    @Column(name = "trigger_type")
    private Byte triggerType;

    /**
     * 使用日期
     */
    @Column(name = "use_days")
    private int useDays;

    /**
     * 活动开始日期
     */
    @Column(name = "begin_date")
    private Long beginTime;

    /**
     * 活动结束日期
     */
    @Column(name = "end_date")
    private Long endTime;

    /**
     * 状态 0-使用  1-终止  -1-删除
     */
    private Byte status;

    /**
     * 创建时间
     */
    private Long created;

    /**
     * 创建用户
     */
    @Column(name = "create_user")
    private Long createUser;

    /**
     * 更新操作用户
     */
    @Column(name = "update_user")
    private Long updateUser;

    private Long updated;

    /**
     * 产品天数
     */
    @Column(name = "product_days")
    private String productDays;

    @Transient
    private Date beginDate;

    @Transient
    private Date endDate;

    public RedPacketVoucher() {

    }

    public BigDecimal getFund() {
        return fund;
    }

    public void setFund(BigDecimal fund) {
        this.fund = fund;
    }

    public Long getLimitFund() {
        return limitFund;
    }

    public void setLimitFund(Long limitFund) {
        this.limitFund = limitFund;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(Byte triggerType) {
        this.triggerType = triggerType;
    }

    public int getUseDays() {
        return useDays;
    }

    public void setUseDays(int useDays) {
        this.useDays = useDays;
    }

    public Long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Long beginTime) {
        this.beginTime = beginTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Date getBeginDate() {
        return DateUtil.getSystemTimeMillisecond(beginTime);
    }

    public Date getEndDate() {
        return DateUtil.getSystemTimeMillisecond(endTime);
    }

    public Long getUpdated() {
        return updated;
    }

    public void setUpdated(Long updated) {
        this.updated = updated;
    }


    public String getProductDays() {
        return productDays;
    }

    public void setProductDays(String productDays) {
        this.productDays = productDays;
    }
}