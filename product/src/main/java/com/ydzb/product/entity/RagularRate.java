package com.ydzb.product.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 定存宝利率对照
 */
@Entity
@Table(name = "wm_ragular_rate")
@DynamicInsert
@DynamicUpdate
public class RagularRate extends BaseEntity<Long> {

    public static final byte STATUS_INUSE = 0;  //状态-使用中
    public static final byte STATUS_DISABLED = 1;   //状态-已停用

    /**
     * 定存宝利率
     */
    @Column(name = "ragular_rate")
    private BigDecimal ragularRate = BigDecimal.ZERO;

    /**
     * 份数
     */
    private Long copies;

    /**
     * 状态：0-使用中 1-已停用
     */
    private Byte status = STATUS_INUSE;

    /**
     * 创建时间
     */
    private Long created;

    /**
     * 创建者id
     */
    @Column(name = "created_user")
    private Long createdUser;

    /**
     * 修改人id
     */
    @Column(name = "updated_user")
    private Long updatedUser;

    /**
     * 是否作为发布份数 0-否  1-是
     */
    @Column(name = "release_type")
    private Byte releaseType = 0;

    private Integer days;   //天数

    /**
     * 活动利率
     */
    @Column(name = "activity_rate")
    private BigDecimal activityRate;


    /**
     * 创建日期
     */
    @Transient
    private Date createdDate;

    /**
     * 构造方法
     */
    public RagularRate() {

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

    public Long getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(Long createdUser) {
        this.createdUser = createdUser;
    }

    public Long getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(Long updatedUser) {
        this.updatedUser = updatedUser;
    }

    public Date getCreatedDate() {
        return DateUtil.getSystemTimeMillisecond(created);
    }

    public Long getCopies() {
        return copies;
    }

    public void setCopies(Long copies) {
        this.copies = copies;
    }

    public Byte getReleaseType() {
        return releaseType;
    }

    public void setReleaseType(Byte releaseType) {
        this.releaseType = releaseType;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public BigDecimal getRagularRate() {
        return ragularRate;
    }

    public void setRagularRate(BigDecimal ragularRate) {
        this.ragularRate = ragularRate;
    }

    public BigDecimal getActivityRate() {
        return activityRate;
    }

    public void setActivityRate(BigDecimal activityRate) {
        this.activityRate = activityRate;
    }
}