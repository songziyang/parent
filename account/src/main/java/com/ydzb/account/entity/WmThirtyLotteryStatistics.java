package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 30亿活动抽奖统计
 */
@Entity
@Table(name = "wm_thirty_lottery_statistics")
@DynamicInsert
@DynamicUpdate
public class WmThirtyLotteryStatistics extends BaseEntity<Long> {

    @Column(name = "today_count")
    private Integer todayCount;
    @Column(name = "statistics_date")
    private Long statisticsDate;

    public Integer getTodayCount() {
        return todayCount;
    }

    public void setTodayCount(Integer todayCount) {
        this.todayCount = todayCount;
    }

    public Long getStatisticsDate() {
        return statisticsDate;
    }

    public void setStatisticsDate(Long statisticsDate) {
        this.statisticsDate = statisticsDate;
    }
}