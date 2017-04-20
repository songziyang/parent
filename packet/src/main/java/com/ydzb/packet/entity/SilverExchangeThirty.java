package com.ydzb.packet.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.user.entity.User;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 30亿活动兑换
 */
@Entity
@Table(name = "wm_silver_exchange_thirty")
@DynamicInsert
@DynamicUpdate
public class SilverExchangeThirty extends BaseEntity<Long> {

    public static final int LEVEL_GOODSMIN = 1; //实物最小level
    public static final int LEVEL_GOODSMAX = 7; //实物最大level

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "product_name")
    private String productName;

    private String realname;
    private String address;
    private String mobile;
    private Integer status;
    private Long created;
    private Long winning;   //中奖时间
    private Long sended;    //发货时间
    private String remark;
    private Integer level;

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public Long getWinning() {
        return winning;
    }

    public void setWinning(Long winning) {
        this.winning = winning;
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

    public Date getCreatedDate() {
        return DateUtil.getSystemTimeMillisecond(created);
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getSendedDate() {
        return DateUtil.getSystemTimeMillisecond(sended);
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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
