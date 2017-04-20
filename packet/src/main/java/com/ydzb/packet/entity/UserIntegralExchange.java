package com.ydzb.packet.entity;

import com.ydzb.admin.entity.Admin;
import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.user.entity.User;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户积分兑换记录
 */
@Entity
@Table(name = "wm_user_integral_exchange")
public class UserIntegralExchange extends BaseEntity<Long> {

    /**
     * 用户
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private User user;

    /**
     * 使用的积分
     */
    private BigDecimal integral;

    /**
     * 产品
     */
    @Column(name = "product_name")
    private String productName;

    /**
     * 姓名
     */
    private String realname;

    /**
     * 地址
     */
    private String address;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 状态 1、已受理 2、已发货3、确认收货4、取消订单
     */
    private Integer status;

    /**
     * 1、实物2、活期加息券3、定存加息券
     */
    private Integer type;

    /**
     * 备注说明
     */
    private String remark;

    @Version
    private Integer version;

    /**
     * 兑换时间
     */
    private Long created;

    /**
     * 发货时间
     */
    private Long sended;

    /**
     * 兑换数量
     */
    private Integer number = 1;

    @ManyToOne
    @JoinColumn(name = "operation_user")
    private Admin operationUser;

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

    public BigDecimal getIntegral() {
        return integral;
    }

    public void setIntegral(BigDecimal integral) {
        this.integral = integral;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Date getCreatedDate() {
        return DateUtil.getSystemTimeMillisecond(created);
    }

    public Date getSendedDate() {
        return DateUtil.getSystemTimeMillisecond(sended);
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Admin getOperationUser() {
        return operationUser;
    }

    public void setOperationUser(Admin operationUser) {
        this.operationUser = operationUser;
    }
}