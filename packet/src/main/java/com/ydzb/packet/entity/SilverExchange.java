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
 * 体验金流水
 */
@Entity
@Table(name = "wm_silver_exchange")
public class SilverExchange extends BaseEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotFound(action = NotFoundAction.IGNORE)
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

    @Transient
    private Date createdDate;

    @Transient
    private Date sendedDate;

    private Integer type;

    private BigDecimal fund;

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

    public Date getCreatedDate() {
        return DateUtil.getSystemTimeMillisecond(created);
    }

    public Date getSendedDate() {
        return DateUtil.getSystemTimeMillisecond(sended);
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getFund() {
        return fund;
    }

    public void setFund(BigDecimal fund) {
        this.fund = fund;
    }
}