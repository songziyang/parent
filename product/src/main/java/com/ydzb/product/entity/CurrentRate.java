package com.ydzb.product.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 活期宝利率对照
 *
 * @author sy
 */
@Entity
@Table(name = "wm_current_rate")
public class CurrentRate extends BaseEntity<Long> {

    public static final byte STATUS_INUSE = 0;  //状态-使用中
    public static final byte STATUS_DISABLED = 1;   //状态-已停用

    public static final byte TIME_MORNING = 0;  //上午
    public static final byte TIME_AFTERNOON = 1;    //下午

    /**
     * 活期宝发布日期
     */
    @Column(name = "cur_date")
    private Long currentDate;

    /**
     * 活期宝当日利率
     */
    @Column(name = "current_rate")
    private BigDecimal currentRate = BigDecimal.ZERO;

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
     * 时间类型 0-上午    1-下午
     */
    @Column(name = "time_type")
    private Byte timeType = TIME_MORNING;

    /**
     * 符合利率
     */
    @Column(name = "com_rate")
    private BigDecimal comRate;

    /**
     * 是否作为发布份数 0-否  1-是
     */
    @Column(name = "release_type")
    private Byte releaseType = 0;


    @Column(name = "new_copies")
    private Integer newCopies = 0;

    /**
     * 活期宝发布日期
     */
    @Transient
    private Date dCurrentDate;

    /**
     * 创建日期
     */
    @Transient
    private Date createdDate;

    /**
     * 构造方法
     */
    public CurrentRate() {

    }

    /**
     * 计算复合年华
     *
     * @return
     */
    public BigDecimal culculateComRate() {
        //复合年化利率：((1 + 当日年化/365)^365)-1
        BigDecimal currentRate = this.currentRate == null ? BigDecimal.ZERO : this.currentRate;
        return new BigDecimal(Math.pow(currentRate.divide(new BigDecimal(36500), 20, BigDecimal.ROUND_DOWN).add(BigDecimal.ONE).doubleValue(), 365)).subtract(BigDecimal.ONE).setScale(4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
    }

    public Long getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Long currentDate) {
        this.currentDate = currentDate;
    }

    public BigDecimal getCurrentRate() {
        return currentRate;
    }

    public void setCurrentRate(BigDecimal currentRate) {
        this.currentRate = currentRate;
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

    public Date getdCurrentDate() {
        return DateUtil.getSystemTimeMillisecond(currentDate);
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

    public Byte getTimeType() {
        return timeType;
    }

    public void setTimeType(Byte timeType) {
        this.timeType = timeType;
    }

    public BigDecimal getComRate() {
        return comRate == null ? culculateComRate() : comRate;
    }

    public void setComRate(BigDecimal comRate) {
        this.comRate = comRate;
    }

    public Byte getReleaseType() {
        return releaseType;
    }

    public void setReleaseType(Byte releaseType) {
        this.releaseType = releaseType;
    }

    public Integer getNewCopies() {
        return newCopies;
    }

    public void setNewCopies(Integer newCopies) {
        this.newCopies = newCopies;
    }
}