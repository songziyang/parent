package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 用户红包
 */
@Entity
@Table(name = "wm_redpacket_users")
@DynamicInsert
@DynamicUpdate
public class WmRedpacketUser extends BaseEntity<Long> {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "product_trade_id")
    private Long productTradeId;

    @Column(name = "redpacket_id")
    private Long redpacketId;

    @Column(name = "redpacket_name")
    private String redpacketName;

    @Column(name = "product_type")
    private Integer productType;

    @Column(name = "redpacket_type")
    private Integer redpacketType;

    @Column(name = "trigger_type")
    private Integer triggerType;

    @Column(name = "begin_time")
    private Long beginTime;

    @Column(name = "finish_time")
    private Long finishTime;

    @Column(name = "get_time")
    private Long getTime;

    @Column(name = "use_finish_time")
    private Long useFinishTime;

    @Column(name = "user_use_time")
    private Long userUseTime;

    @Column(name = "invest_days")
    private Integer investDays;

    @Column(name = "invest_finish_time")
    private Long investFinishTime;

    @Column(name = "give_value")
    private BigDecimal giveValue;

    private Integer status;

    @Column(name = "give_user_id")
    private Long giveUserId;

    private Long created;
    @Column(name = "created_user")
    private String createdUser;
    @Column(name = "limit_fund")
    private Integer limitFund;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductTradeId() {
        return productTradeId;
    }

    public void setProductTradeId(Long productTradeId) {
        this.productTradeId = productTradeId;
    }

    public Long getRedpacketId() {
        return redpacketId;
    }

    public void setRedpacketId(Long redpacketId) {
        this.redpacketId = redpacketId;
    }

    public String getRedpacketName() {
        return redpacketName;
    }

    public void setRedpacketName(String redpacketName) {
        this.redpacketName = redpacketName;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public Integer getRedpacketType() {
        return redpacketType;
    }

    public void setRedpacketType(Integer redpacketType) {
        this.redpacketType = redpacketType;
    }

    public Integer getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(Integer triggerType) {
        this.triggerType = triggerType;
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

    public Long getGetTime() {
        return getTime;
    }

    public void setGetTime(Long getTime) {
        this.getTime = getTime;
    }

    public Long getUseFinishTime() {
        return useFinishTime;
    }

    public void setUseFinishTime(Long useFinishTime) {
        this.useFinishTime = useFinishTime;
    }

    public Long getUserUseTime() {
        return userUseTime;
    }

    public void setUserUseTime(Long userUseTime) {
        this.userUseTime = userUseTime;
    }

    public Integer getInvestDays() {
        return investDays;
    }

    public void setInvestDays(Integer investDays) {
        this.investDays = investDays;
    }

    public Long getInvestFinishTime() {
        return investFinishTime;
    }

    public void setInvestFinishTime(Long investFinishTime) {
        this.investFinishTime = investFinishTime;
    }

    public BigDecimal getGiveValue() {
        return giveValue;
    }

    public void setGiveValue(BigDecimal giveValue) {
        this.giveValue = giveValue;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getGiveUserId() {
        return giveUserId;
    }

    public void setGiveUserId(Long giveUserId) {
        this.giveUserId = giveUserId;
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

    public Integer getLimitFund() {
        return limitFund;
    }

    public void setLimitFund(Integer limitFund) {
        this.limitFund = limitFund;
    }
}