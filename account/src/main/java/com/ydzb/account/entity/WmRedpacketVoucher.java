package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 红包－代金券
 */
@Entity
@Table(name = "wm_redpack_vouchers")
@DynamicInsert
@DynamicUpdate
public class WmRedpacketVoucher extends BaseEntity<Long> {

    public static final int TRIGGERTYPE_OCTREDPACKET = 60;  //触发类型-10月红包活动

    private BigDecimal fund;
    @Column(name = "limit_fund")
    private Integer limitFund;
    private String name;
    @Column(name = "trigger_type")
    private Integer triggerType;
    @Column(name = "use_days")
    private Integer useDays;
    @Column(name = "begin_date")
    private Long beginDate;
    @Column(name = "end_date")
    private Long endDate;
    private Integer status;
    private Long created;
    @Column(name = "create_user")
    private Long createUser;

    public BigDecimal getFund() {
        return fund;
    }

    public void setFund(BigDecimal fund) {
        this.fund = fund;
    }

    public Integer getLimitFund() {
        return limitFund;
    }

    public void setLimitFund(Integer limitFund) {
        this.limitFund = limitFund;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Long getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Long beginDate) {
        this.beginDate = beginDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }
}