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
 * 其他平台用户
 */
@Entity
@Table(name = "wm_otherplatform_user")
@DynamicInsert
@DynamicUpdate
public class OtherPlatformUser extends BaseEntity<Long> {

    public static final int REGFLAG_NOTREGIST = 0;  //未注册
    public static final int REGFLAG_REGIST = 1;  //已注册

    /**
     * 用户
     */
    @OneToOne
    @JoinColumn(name = "user_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private User user;
    /**
     * 注册标识
     */
    @Column(name = "reg_flag")
    private Integer regFlag;

    private String mobile;  //手机号
    private String realname;    //真实姓名

    @OneToOne
    @JoinColumn(name = "platform_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private OtherPlatform otherPlatform;

    private Long created;   //创建时间

    /**
     * 注册时间
     */
    @Column(name = "reg_time")
    private Long regTime;

    @Transient
    private Date regDate;

    @Transient
    private Date createdDate;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getRegFlag() {
        return regFlag;
    }

    public void setRegFlag(Integer regFlag) {
        this.regFlag = regFlag;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public OtherPlatform getOtherPlatform() {
        return otherPlatform;
    }

    public void setOtherPlatform(OtherPlatform otherPlatform) {
        this.otherPlatform = otherPlatform;
    }

    public Date getCreatedDate() {
        return DateUtil.getSystemTimeMillisecond(created);
    }

    public Long getRegTime() {
        return regTime;
    }

    public void setRegTime(Long regTime) {
        this.regTime = regTime;
    }

    public Date getRegDate() {
        return DateUtil.getSystemTimeMillisecond(regTime);
    }
}