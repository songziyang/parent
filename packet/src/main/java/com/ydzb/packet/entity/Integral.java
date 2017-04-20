package com.ydzb.packet.entity;

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
 * 积分
 */
@Entity
@Table(name = "wm_integral")
@DynamicInsert
@DynamicUpdate
public class Integral extends BaseEntity<Long> {

    public static final int STATE_INUSE = 0;   //状态-使用中
    public static final int STATE_DISABLED = 1;    //状态-终止/停用
    public static final int STATE_DELETED = -1;    //状态-删除

    private String name;    //名称
    private BigDecimal integral;    //积分值
    private String fundflow;    //来源去向
    private Long created;   //创建时间
    /**
     * 创建用户
     */
    @Column(name = "created_user")
    private String createdUser;
    //更新时间
    private Long updated;
    /**
     * 更新用户
     */
    @Column(name = "updated_user")
    private String updatedUser;

    private Integer status;

    @Transient
    private Date createdDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getIntegral() {
        return integral;
    }

    public void setIntegral(BigDecimal integral) {
        this.integral = integral;
    }

    public String getFundflow() {
        return fundflow;
    }

    public void setFundflow(String fundflow) {
        this.fundflow = fundflow;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return DateUtil.getSystemTimeMillisecond(created);
    }
}