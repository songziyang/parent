package com.ydzb.user.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "wm_user_recharge")
public class UserRecharge extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private BigDecimal money;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "recharge_succeed_time")
    private Long rechargeSucceedTime;

    @Column(name = "recharge_time")
    private Long rechargeTime;

    private String status;

    private Long created;

    @Column(name = "merorderid")
    private String merorderId;

    @Column(name = "custid")
    private String custId;

    @Column(name = "phonetoken")
    private String phoneToken;

    @Column(name = "bank_id")
    private Long bankId;

    @Column(name = "real_name")
    private String realName;

    @Column(name = "id_card")
    private String idCard;

    private String mobile;

    /**
     * ADD BY CRF
     * 2017.03.07
     */
    //充值类型 1、电子账户充值 0/其他 、银多账户充值
    private Integer rechargetype;
    //1、线上充值 0/其他线下充值
    private Integer onlines;


    @Transient
    private Date rechargeDate;

    @Transient
    private Date rechargeSuccessDate;

    public UserRecharge() {
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Long getRechargeSucceedTime() {
        return rechargeSucceedTime;
    }

    public void setRechargeSucceedTime(Long rechargeSucceedTime) {
        this.rechargeSucceedTime = rechargeSucceedTime;
    }

    public Long getRechargeTime() {
        return rechargeTime;
    }

    public void setRechargeTime(Long rechargeTime) {
        this.rechargeTime = rechargeTime;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getRechargeDate() {
        return DateUtil.getSystemTimeMillisecond(rechargeTime);
    }

    public Date getRechargeSuccessDate() {
        return DateUtil.getSystemTimeMillisecond(rechargeSucceedTime);
    }

    public String getMerorderId() {
        return merorderId;
    }

    public void setMerorderId(String merorderId) {
        this.merorderId = merorderId;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getPhoneToken() {
        return phoneToken;
    }

    public void setPhoneToken(String phoneToken) {
        this.phoneToken = phoneToken;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getRechargetype() {
        return rechargetype;
    }
    public void setRechargetype(Integer rechargetype) {
        this.rechargetype = rechargetype;
    }
    public Integer getOnlines() {
        return onlines;
    }
    public void setOnlines(Integer onlines) {
        this.onlines = onlines;
    }
}