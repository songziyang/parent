package com.ydzb.user.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户银行解绑
 */
@Entity
@Table(name = "wm_user_banks_del")
@DynamicInsert
@DynamicUpdate
public class UserBanksDel extends BaseEntity<Long> {

    public static final int UNDER_VALIDATE = 0; //待审核
    public static final int VALIDATE_SUCCESS = 1; //审核成功
    public static final int VALIDATE_FAILURE = 2;   //审核失败

    /**
     * 用户
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private User user;

    /**
     * 银行卡信息表ID
     */
    @Column(name = "link_id")
    private Long linkId;

    /**
     * 身份证信息图片url
     */
    @Column(name = "idcard_url")
    private String idCardUrl;

    /**
     * 银行卡图片url
     */
    @Column(name = "bank_url")
    private String bankUrl;

    /**
     * 审核状态 0、待审核 1、审核成功 2、审核失败',
     */
    private Integer status;

    private String reason;  //失败原因
    private Long created;   //申请时间

    @Column(name = "lock_version")
    private Integer lockVersion;

    @Transient
    private Date createdDate;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getLinkId() {
        return linkId;
    }

    public void setLinkId(Long linkId) {
        this.linkId = linkId;
    }

    public String getIdCardUrl() {
        return idCardUrl;
    }

    public void setIdCardUrl(String idCardUrl) {
        this.idCardUrl = idCardUrl;
    }

    public String getBankUrl() {
        return bankUrl;
    }

    public void setBankUrl(String bankUrl) {
        this.bankUrl = bankUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Integer getLockVersion() {
        return lockVersion;
    }

    public void setLockVersion(Integer lockVersion) {
        this.lockVersion = lockVersion;
    }

    public Date getCreatedDate() {
        return DateUtil.getSystemTimeMillisecond(created);
    }
}