package com.ydzb.packet.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.user.entity.User;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 金秋十月红包活动
 */
@Entity
@Table(name = "wm_activity_october_redpacket")
@DynamicInsert
@DynamicUpdate
public class ActivityOctoberRedpacket extends BaseEntity<Long> {

    /**
     * 用户
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private User user;

    /**
     * 购买金额
     */
    @Column(name = "buy_fund")
    private BigDecimal buyFund;

    private Integer days;   //天数

    /**
     * 购买类型
     */
    @Column(name = "buy_type")
    private Integer buyType;

    /**
     * 红包金额
     */
    @Column(name = "redpacket_fund")
    private BigDecimal redpacketFund;

    private Long created;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }
}