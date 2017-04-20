package com.ydzb.user.entity;


import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Entity
@Table(name = "wm_channel_manager")
@DynamicInsert
@DynamicUpdate
public class ChannelManager extends BaseEntity<Long> {

    //类型 1、充值2、提现
    private Integer type;

    //限制银行名称多个用,隔开
    @Column(name = "limit_info")
    private String limitInfo;

    //提示信息
    @Column(name = "prompt_info")
    private String promptInfo;

    //1、使用 2、禁止
    private Integer status;

    //创建时间
    private Long created;

    //创建人
    @Column(name = "created_user")
    private String createdUser;

    @Column(name = "updated_user")
    private String updatedUser;

    //创建时间
    @Transient
    private Date createDate;

    public Date getCreateDate() {
        return DateUtil.getSystemTimeMillisecond(created);
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getLimitInfo() {
        return limitInfo;
    }

    public void setLimitInfo(String limitInfo) {
        this.limitInfo = limitInfo;
    }

    public String getPromptInfo() {
        return promptInfo;
    }

    public void setPromptInfo(String promptInfo) {
        this.promptInfo = promptInfo;
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

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public String getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser;
    }
}
