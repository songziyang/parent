package com.ydzb.user.entity;

import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * vip设置记录
 */
@Entity
@Table(name = "wm_vip_set_record")
@DynamicInsert
@DynamicUpdate
public class VipSetRecord extends BaseEntity<Long> {

    public static final int STATUS_NOTEXPIRE = 1;   //未到期
    public static final int STATUS_EXPIRE = 2;  //状态-已到期
    public static final int STATUS_CANCEL = 3;  //状态-已废除

    /**
     * 用户
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * vip等级
     */
    @ManyToOne
    @JoinColumn(name = "vip_id")
    private VipGrade vipGrade;

    private Long created;

    /**
     * 创建用户
     */
    @Column(name = "created_user")
    private String createdUser;

    /**
     * 到期时间
     */
    @Column(name = "expire_time")
    private Long expireTime;

    private Integer status; //状态 1-未到期 2-已到期 3-已作废

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public VipGrade getVipGrade() {
        return vipGrade;
    }

    public void setVipGrade(VipGrade vipGrade) {
        this.vipGrade = vipGrade;
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

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}