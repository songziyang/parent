package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 30亿活动兑换统计
 */
@Entity
@Table(name = "wm_thirty_exchange_statistics")
@DynamicInsert
@DynamicUpdate
public class WmThirtyExchangeStatistics extends BaseEntity<Long> {

    /**
     * 总兑换人数
     */
    @Column(name = "all_count")
    private Integer allCount;

    /**
     * 总兑换人数
     */
    @Column(name = "goods_count")
    private Integer goodsCount;

    /**
     * 统计日期
     */
    @Column(name = "statistics_date")
    private Long statisticsDate;

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
}