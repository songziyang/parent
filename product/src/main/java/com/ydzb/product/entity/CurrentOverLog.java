package com.ydzb.product.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.user.entity.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "wm_current_overlog")
public class CurrentOverLog extends BaseEntity<Long> {

    private static final long serialVersionUID = -3537051496328476177L;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    // 类型（ 0 投资赎回 1 体验金赎回 ）
    private Integer type;

    // 赎回金额
    @Column(name = "redemption_fund")
    private BigDecimal redemptionFund;


    // 赎回时间(带时分秒)
    @Column(name = "redemption_time")
    private Long redemptionTime;

    // 赎回贡献值
    @Column(name = "exp_fund")
    private Integer expFund;


    @Transient
    private Date redemptionDate;


    public CurrentOverLog() {
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getRedemptionFund() {
        return redemptionFund;
    }

    public void setRedemptionFund(BigDecimal redemptionFund) {
        this.redemptionFund = redemptionFund;
    }

    public Long getRedemptionTime() {
        return redemptionTime;
    }

    public void setRedemptionTime(Long redemptionTime) {
        this.redemptionTime = redemptionTime;
    }

    public Integer getExpFund() {
        return expFund;
    }

    public void setExpFund(Integer expFund) {
        this.expFund = expFund;
    }

    public Date getRedemptionDate() {
        return DateUtil.getSystemTimeMillisecond(redemptionTime);
    }


}