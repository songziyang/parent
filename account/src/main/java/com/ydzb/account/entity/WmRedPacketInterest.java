package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 红包-加息券
 *
 * @author sy
 */
@Entity
@Table(name = "wm_redpacket_interest")
public class WmRedPacketInterest extends BaseEntity<Long> {

    public static final int TRIGGERTYPE_RAGULARRANK = 61;   //触发类型-定存排行

    /**
     * 红包名称
     */
    private String name;


    @Column(name = "product_type")
    private Long productType;

    /**
     * 触发类型：    0-手动    1-注册    2-充值    3-投资    4-推荐    5-普通推荐  6-VIP推荐 7-升级VIP
     */
    @Column(name = "trigger_type")
    private Integer triggerType;

    /**
     * 活动开始时间
     */
    @Column(name = "activity_begin_time")
    private Long activityBeginTime;

    /**
     * 活动结束时间
     */
    @Column(name = "activity_finish_time")
    private Long activityFinishTime;

    /**
     * 红包类型：    0-旧类型   1-活期宝加息    2-定存宝加息
     */
    @Column(name = "redp_type")
    private Integer redpacketType;

    /**
     * 使用有效天数
     */
    @Column(name = "use_days")
    private Integer useDays;

    /**
     * 投资有效天数
     */
    @Column(name = "invest_days")
    private Integer investDays;

    /**
     * 赠送值
     */
    @Column(name = "give_value")
    private BigDecimal giveValue;

    /**
     * 状态：  0-使用    1-终止    -1-删除
     */
    private Byte status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Long created;

    /**
     * 创建者
     */
    @Column(name = "created_user")
    private String createdUser;

    /**
     * 修改时间
     */
    private Long updated;

    /**
     * 修改者
     */
    @Column(name = "updated_user")
    private String updatedUser;


    /**
     * 构造方法
     */
    public WmRedPacketInterest() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getProductType() {
        return productType;
    }

    public void setProductType(Long productType) {
        this.productType = productType;
    }

    public Integer getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(Integer triggerType) {
        this.triggerType = triggerType;
    }

    public Long getActivityBeginTime() {
        return activityBeginTime;
    }

    public void setActivityBeginTime(Long activityBeginTime) {
        this.activityBeginTime = activityBeginTime;
    }

    public Long getActivityFinishTime() {
        return activityFinishTime;
    }

    public void setActivityFinishTime(Long activityFinishTime) {
        this.activityFinishTime = activityFinishTime;
    }

    public Integer getRedpacketType() {
        return redpacketType;
    }

    public void setRedpacketType(Integer redpacketType) {
        this.redpacketType = redpacketType;
    }

    public Integer getUseDays() {
        return useDays;
    }

    public void setUseDays(Integer useDays) {
        this.useDays = useDays;
    }

    public Integer getInvestDays() {
        return investDays;
    }

    public void setInvestDays(Integer investDays) {
        this.investDays = investDays;
    }

    public BigDecimal getGiveValue() {
        return giveValue;
    }

    public void setGiveValue(BigDecimal giveValue) {
        this.giveValue = giveValue;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public Long getUpdated() {
        return updated;
    }

    public void setUpdated(Long updated) {
        this.updated = updated;
    }

    public String getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser;
    }
}