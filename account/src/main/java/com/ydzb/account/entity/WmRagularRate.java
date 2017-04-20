package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 定存宝利率对照
 */
@Entity
@Table(name = "wm_ragular_rate")
public class WmRagularRate extends BaseEntity<Long> {

    public static final byte STATUS_INUSE = 0;  //状态-使用中
    public static final byte STATUS_DISABLED = 1;   //状态-已停用

    /**
     * 定存宝利率
     */
    @Column(name = "ragular_rate")
    private BigDecimal ragularRate;

    /**
     * 活动利率
     */
    @Column(name = "activity_rate")
    private BigDecimal activityRate;


    /**
     * 份数
     */
    private Integer copies;

    /**
     * 状态：0-使用中 1-已停用
     */
    private Byte status;

    /**
     * 天数
     */
    private Integer days;

    /**
     * 构造方法
     */
    public WmRagularRate() {

    }

    public BigDecimal getRagularRate() {
        return ragularRate;
    }

    public void setRagularRate(BigDecimal ragularRate) {
        this.ragularRate = ragularRate;
    }

    public Integer getCopies() {
        return copies;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public BigDecimal getActivityRate() {
        return activityRate;
    }

    public void setActivityRate(BigDecimal activityRate) {
        this.activityRate = activityRate;
    }


}