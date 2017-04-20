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
 * 30亿活动兑换统计
 */
@Entity
@Table(name = "wm_thirty_exchange_statistics")
@DynamicInsert
@DynamicUpdate
public class ThirtyExchangeStatistics extends BaseEntity<Long> {

    /**
     * '总兑换人数'
     */
    @Column(name = "all_count")
    private Integer allCount;

    /**
     * '实物兑换人数'
     */
    @Column(name = "goods_count")
    private Integer goodsCount;

    /**
     * '统计日期'
     */
    @Column(name = "statistics_date")
    private Long statisticsDate;

    @Transient
    private Date staDate;

    public Integer getAllCount() {
        return allCount;
    }

    public void setAllCount(Integer allCount) {
        this.allCount = allCount;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
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