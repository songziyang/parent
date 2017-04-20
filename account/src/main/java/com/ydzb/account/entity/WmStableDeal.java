package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "wm_stable_deal")
public class WmStableDeal extends BaseEntity<Long> {

    private static final long serialVersionUID = 2474401648514579648L;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "stable_id")
    private Long stableId;

    private Integer copies;

    private Long btime;

    private Integer state;

    //收益
    private BigDecimal income;

    public Integer getCopies() {
        return copies;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }

    public Long getBtime() {
        return btime;
    }

    public void setBtime(Long btime) {
        this.btime = btime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getBuyDate() {
        return DateUtil.getSystemTimeMillisecond(btime);
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getStableId() {
        return stableId;
    }

    public void setStableId(Long stableId) {
        this.stableId = stableId;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }
}