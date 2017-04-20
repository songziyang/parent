package com.ydzb.packet.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * 30亿活动抽奖统计
 */
@Entity
@Table(name = "wm_thirty_lottery_statistics")
@DynamicInsert
@DynamicUpdate
public class ThirtyLotteryStatistics extends BaseEntity<Long> {

    /**
     * '今日抽奖人数'
     */
    @Column(name = "today_count")
    private Integer todayCount;

    /**
     * '统计日期'
     */
    @Column(name = "statistics_date")
    private Long statisticsDate;

    @Transient
    private Date staDate;

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

    public Date getStaDate() {
        return DateUtil.getSystemTimeMillisecond(statisticsDate);
    }
}