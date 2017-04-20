package com.ydzb.packet.entity;


import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.user.entity.User;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户积分记录
 */
@Entity
@Table(name = "wm_user_integral_record")
public class UserIntegralRecord extends BaseEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private User user;

    //来源去向
    private String fundflow;

    //金额
    private BigDecimal integral;

    //余额
    private BigDecimal balance;

    //类型1-获取 2-支出
    private Integer type;

    // 1、完善信息 2、签到 3、投资4、VIP 21、兑换
    @Column(name = "link_type")
    private Integer linkType;

    //操作时间
    @Column(name = "created")
    private Long created;

    @Column(name = "link_id")
    private Long linkId;    //外链Id

    //创建时间
    @Transient
    private Date createdDate;


    public UserIntegralRecord() {
    }

    public Date getCreatedDate() {
        return DateUtil.getSystemTimeMillisecond(created);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFundflow() {
        return fundflow;
    }

    public void setFundflow(String fundflow) {
        this.fundflow = fundflow;
    }

    public BigDecimal getIntegral() {
        return integral;
    }

    public void setIntegral(BigDecimal integral) {
        this.integral = integral;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
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

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getLinkId() {
        return linkId;
    }

    public void setLinkId(Long linkId) {
        this.linkId = linkId;
    }
}
