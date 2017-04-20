package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 中秋节活动
 */
@Entity
@Table(name = "wm_deposit_week_sort")
@DynamicInsert
@DynamicUpdate
public class WmDepositWeekSort extends BaseEntity<Long> {

    /**
     * 用户
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 总金融
     */
    @Column(name = "total_fund")
    private BigDecimal totalFund;

    /**
     * 排名
     */
    private Integer ranking;

    /**
     * 操作日期
     */
    private Long optime;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalFund() {
        return totalFund;
    }

    public void setTotalFund(BigDecimal totalFund) {
        this.totalFund = totalFund;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Long getOptime() {
        return optime;
    }

    public void setOptime(Long optime) {
        this.optime = optime;
    }
}