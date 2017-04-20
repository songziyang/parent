package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;


@Entity
@Table(name = "wm_seven_days")
public class WmSevenDays extends BaseEntity<Long> {

    private static final long serialVersionUID = 5297202145011996845L;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "total_revenue")
    private BigDecimal totalRevenue;

    @Column(name = "day_revenue")
    private BigDecimal dayRevenue;

    @Column(name = "des_revenue")
    private BigDecimal desRevenue;

    private Integer status;

    private Long created;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public BigDecimal getDayRevenue() {
        return dayRevenue;
    }

    public void setDayRevenue(BigDecimal dayRevenue) {
        this.dayRevenue = dayRevenue;
    }

    public BigDecimal getDesRevenue() {
        return desRevenue;
    }

    public void setDesRevenue(BigDecimal desRevenue) {
        this.desRevenue = desRevenue;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

}
