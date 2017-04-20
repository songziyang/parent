package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;


@Entity
@Table(name = "wm_current_rate")
public class WmCurrentRate extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    //活期宝发布日期
    @Column(name = "cur_date")
    private Long curDate;

    //活期宝当日利率
    @Column(name = "current_rate")
    private BigDecimal currentRate;

    //状态 0、使用中 1、已停用
    private Integer status;

    //份数
    private Integer copies;

    //时间类型 0-上午 1-下午
    @Column(name = "time_type")
    private Integer timeType;

    @Column(name = "release_type")
    private Integer releaseType;

    //新手标份数
    @Column(name = "new_copies")
    private Integer newCopies;

    public WmCurrentRate() {

    }

    public Long getCurDate() {
        return curDate;
    }

    public void setCurDate(Long curDate) {
        this.curDate = curDate;
    }

    public BigDecimal getCurrentRate() {
        return currentRate;
    }

    public void setCurrentRate(BigDecimal currentRate) {
        this.currentRate = currentRate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCopies() {
        return copies;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }

    public Integer getTimeType() {
        return timeType;
    }

    public void setTimeType(Integer timeType) {
        this.timeType = timeType;
    }

    public Integer getReleaseType() {
        return releaseType;
    }

    public void setReleaseType(Integer releaseType) {
        this.releaseType = releaseType;
    }

    public Integer getNewCopies() {
        return newCopies;
    }

    public void setNewCopies(Integer newCopies) {
        this.newCopies = newCopies;
    }
}
