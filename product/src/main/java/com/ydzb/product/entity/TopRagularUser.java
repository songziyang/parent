package com.ydzb.product.entity;

import com.ydzb.core.utils.DateUtil;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 定存排行实体（2016年1月活动）
 */
@Entity
public class TopRagularUser implements java.io.Serializable {

    @Id
    private Long userId;

    private String realName;

    private String username;

    private String mobile;

    private String remark;

    private BigDecimal totalFund;

    private BigDecimal usableFund;

    private BigDecimal buyFund;

    private Long created;

    @Transient
    private Date createdDate;


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public BigDecimal getTotalFund() {
        return totalFund;
    }

    public void setTotalFund(BigDecimal totalFund) {
        this.totalFund = totalFund;
    }

    public BigDecimal getUsableFund() {
        return usableFund;
    }

    public void setUsableFund(BigDecimal usableFund) {
        this.usableFund = usableFund;
    }

    public BigDecimal getBuyFund() {
        return buyFund;
    }

    public void setBuyFund(BigDecimal buyFund) {
        this.buyFund = buyFund;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Date getCreatedDate() {
        return DateUtil.getSystemTimeMillisecond(created);
    }
}