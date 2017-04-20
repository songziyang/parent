package com.ydzb.user.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 小额充值
 */
@Entity
@Table(name = "vi_recharge_penny")
@DynamicInsert
@DynamicUpdate
public class ViRechargePenny extends BaseEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private User user;

    @Column(name = "serial_number")
    private String serialNumber;

    private BigDecimal money;

    @Column(name = "recharge_time")
    private Long rechargeTime;

    @Transient
    private Date rechargeSucceedDate;

    /**
     * ADD BY CRF
     * 2017.03.08
     */
    private Integer rechargetype;

    private Integer onlines;
    /**
     * END
     */


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Date getRechargeSucceedDate() {
        return DateUtil.getSystemTimeMillisecond(rechargeTime);
    }

    public Long getRechargeTime() {
        return rechargeTime;
    }

    public void setRechargeTime(Long rechargeTime) {
        this.rechargeTime = rechargeTime;
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