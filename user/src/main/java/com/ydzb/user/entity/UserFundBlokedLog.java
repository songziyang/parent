package com.ydzb.user.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "wm_user_fund_blokedlog")
public class UserFundBlokedLog extends BaseEntity<Long> {

    private static final long serialVersionUID = 5948485083977619500L;

    @Column(name = "user_id")
    private Long userId;


    private BigDecimal fund;

    // 冻结外链ID
    @Column(name = "link_id")
    private Long linkId;

    // 类型 1冻结,2解冻
    private Integer type;


    // 外链类型:1提现
    @Column(name = "link_type")
    private Integer linkType;

    @Column(name = "operation_time")
    private Long operationTime;


    @Column(name = "usable_fund")
    private BigDecimal usableFund;

    private String remark;

    //创建时间
    @Transient
    private Date createDate;

    public UserFundBlokedLog() {
    }


    public Date getCreateDate() {
        return DateUtil.getSystemTimeMillisecond(operationTime);
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getFund() {
        return fund;
    }

    public void setFund(BigDecimal fund) {
        this.fund = fund;
    }

    public Long getLinkId() {
        return linkId;
    }

    public void setLinkId(Long linkId) {
        this.linkId = linkId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getLinkType() {
        return linkType;
    }

    public void setLinkType(Integer linkType) {
        this.linkType = linkType;
    }

    public Long getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Long operationTime) {
        this.operationTime = operationTime;
    }

    public BigDecimal getUsableFund() {
        return usableFund;
    }

    public void setUsableFund(BigDecimal usableFund) {
        this.usableFund = usableFund;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}