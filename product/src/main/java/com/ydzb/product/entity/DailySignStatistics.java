package com.ydzb.product.entity;

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
 * 每日签到统计
 */
@Entity
@Table(name = "wm_dailysign_statistics")
@DynamicInsert
@DynamicUpdate
public class DailySignStatistics extends BaseEntity<Long> {

    /**
     * 签到次数
     */
    @Column(name = "sign_count")
    private Integer signCount;

    /**
     * 统计时间
     */
    @Column(name = "statistics_date")
    private Long statisticsDate;

    @Transient
    private Date staDate;

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

    public Date getStaDate() {
        return DateUtil.getSystemTimeMillisecond(statisticsDate);
    }
}