package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 金秋十月红包活动
 */
@Entity
@Table(name = "wm_activity_october_redpacket")
@DynamicInsert
@DynamicUpdate
public class WmActivityOctoberRedpacket extends BaseEntity<Long> {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "buy_fund")
    private BigDecimal buyFund;

    private Integer days;

    @Column(name = "buy_type")
    private Integer buyType;
    @Column(name = "redpacket_fund")
    private BigDecimal redpacketFund;

    @Column(name = "redpacket_user_id")
    private Long redpacketUserId;

    @Column(name = "account_id")
    private Long accountId;
    private Long created;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getBuyFund() {
        return buyFund;
    }

    public void setBuyFund(BigDecimal buyFund) {
        this.buyFund = buyFund;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getBuyType() {
        return buyType;
    }

    public void setBuyType(Integer buyType) {
        this.buyType = buyType;
    }

    public BigDecimal getRedpacketFund() {
        return redpacketFund;
    }

    public void setRedpacketFund(BigDecimal redpacketFund) {
        this.redpacketFund = redpacketFund;
    }

    public Long getRedpacketUserId() {
        return redpacketUserId;
    }

    public void setRedpacketUserId(Long redpacketUserId) {
        this.redpacketUserId = redpacketUserId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }
}