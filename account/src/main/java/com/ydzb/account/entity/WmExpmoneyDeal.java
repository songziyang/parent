package com.ydzb.account.entity;


import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "wm_expmoney_deal")
public class WmExpmoneyDeal extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    public static final int TYPE_ACTIVITY_3BILLION = 43; //类型-30亿活动

    @Column(name = "user_id")
    private Long userId;

    private BigDecimal fund;

    private Long created;

    private Integer status;

    private Integer type;

    @Column(name = "close_date")
    private Long closeDate;

    public WmExpmoneyDeal() {

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getFund() {
        return fund;
    }

    public void setFund(BigDecimal fund) {
        this.fund = fund;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Long closeDate) {
        this.closeDate = closeDate;
    }
}
