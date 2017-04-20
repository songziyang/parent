package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 */
@Entity
@Table(name = "av_referuser_redpacket")
@DynamicInsert
@DynamicUpdate
public class WmActivityReferUser extends BaseEntity<Long> {

    /**
     * 推荐人Id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 被推荐人id
     */
    @Column(name = "referee_user_id")
    private Long refereeUserId;

    /**
     * 投资总额
     */
    @Column(name = "all_investment")
    private BigDecimal allInvestment;

    private BigDecimal percentage;  //推荐返现比例

    /**
     * 活期收益
     */
    @Column(name = "dayloan_income")
    private BigDecimal dayloanIncome;

    private Long opdate;    //操作日期
    private Long created;   //创建时间

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRefereeUserId() {
        return refereeUserId;
    }

    public void setRefereeUserId(Long refereeUserId) {
        this.refereeUserId = refereeUserId;
    }

    public BigDecimal getAllInvestment() {
        return allInvestment;
    }

    public void setAllInvestment(BigDecimal allInvestment) {
        this.allInvestment = allInvestment;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public BigDecimal getDayloanIncome() {
        return dayloanIncome;
    }

    public void setDayloanIncome(BigDecimal dayloanIncome) {
        this.dayloanIncome = dayloanIncome;
    }

    public Long getOpdate() {
        return opdate;
    }

    public void setOpdate(Long opdate) {
        this.opdate = opdate;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }
}