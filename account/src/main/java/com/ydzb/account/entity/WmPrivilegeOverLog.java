package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 新手特权赎回日志
 */
@Entity
@Table(name = "wm_privilege_overlog")
@DynamicInsert
@DynamicUpdate
public class WmPrivilegeOverLog extends BaseEntity<Long> {

    public static final int TYPE_REDEEM_MANUAL = 0;     //类型-手动赎回
    public static final int TYPE_REDEEM_EXPIRE = 1;     //类型-到期赎回

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;
    private Integer type;   //类型
    /**
     * 赎回金额
     */
    @Column(name = "redemption_fund")
    private BigDecimal redemptionFund;

    /**
     * 赎回时间（带时分秒）
     */
    @Column(name = "redemption_time")
    private Long redemptionTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
}