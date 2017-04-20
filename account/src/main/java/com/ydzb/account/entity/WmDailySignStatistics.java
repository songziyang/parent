package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 每日签到统计
 */
@Entity
@Table(name = "wm_dailysign_statistics")
@DynamicInsert
@DynamicUpdate
public class WmDailySignStatistics extends BaseEntity<Long> {

    /**
     * 签到次数
     */
    @Column(name = "sign_count")
    private Integer signCount;

    /**
     * 统计日期
     */
    @Column(name = "statistics_date")
    private Long statisticsDate;


    public WmDailySignStatistics() {
    }

    public WmDailySignStatistics(Integer signCount, Long statisticsDate) {
        this.signCount = signCount;
        this.statisticsDate = statisticsDate;
    }

    public Integer getSignCount() {
        return signCount;
    }

    public void setSignCount(Integer signCount) {
        this.signCount = signCount;
    }

    public Long getStatisticsDate() {
        return statisticsDate;
    }

    public void setStatisticsDate(Long statisticsDate) {
        this.statisticsDate = statisticsDate;
    }
}