package com.ydzb.packet.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.user.entity.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 活动视图
 */
@Entity
@Table(name = "vi_silver_exchange")
public class ViSilverExchange extends BaseEntity<Long> {

    public static final int SILVER = 0;    //银币活动兑换
    public static final int GOLD_EGG = 1;  //双十二砸金蛋
    public static final int MOBILE_RECHARGE = 2;   //手机充值

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private SilverProduct silverProduct;

    private String realname;

    private String address;

    private String mobile;

    private Integer status;

    private Long created;

    private Long sended;

    private String remark;

    @Version
    private Integer version;

    private Byte type;  //活动类型  0-银币活动兑换    1-双十二砸金蛋    2-手机充值

    private BigDecimal fund;    //获得金额

    @Transient
    private Date createdDate;

    @Transient
    private Date sendedDate;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SilverProduct getSilverProduct() {
        return silverProduct;
    }

    public void setSilverProduct(SilverProduct silverProduct) {
        this.silverProduct = silverProduct;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public Long getSended() {
        return sended;
    }

    public void setSended(Long sended) {
        this.sended = sended;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public BigDecimal getFund() {
        return fund;
    }

    public void setFund(BigDecimal fund) {
        this.fund = fund;
    }

    public Date getCreatedDate() {
        return DateUtil.getSystemTimeMillisecond(created);
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getSendedDate() {
        return DateUtil.getSystemTimeMillisecond(sended);
    }

    public void setSendedDate(Date sendedDate) {
        this.sendedDate = sendedDate;
    }
}