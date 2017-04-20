package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;


@Entity
@Table(name = "wm_current_prepay")
public class WmCurrentPrepay extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    // 购买份数
    @Column(name = "buy_copies")
    private Integer buyCopies;

    // 购买资金
    @Column(name = "buy_fund")
    private BigDecimal buyFund;

    // 预投时间
    @Column(name = "prepay_time")
    private Long prepayTime;

    // 状态（0排队中,1购买成功,2取消预投）
    private Integer status;

    @Column(name = "user_id")
    private Long userId;

    // 0是本金 1 是投资金
    private Integer type;

    public WmCurrentPrepay() {
    }


    public Integer getBuyCopies() {
        return buyCopies;
    }

    public void setBuyCopies(Integer buyCopies) {
        this.buyCopies = buyCopies;
    }

    public BigDecimal getBuyFund() {
        return buyFund;
    }

    public void setBuyFund(BigDecimal buyFund) {
        this.buyFund = buyFund;
    }

    public Long getPrepayTime() {
        return prepayTime;
    }

    public void setPrepayTime(Long prepayTime) {
        this.prepayTime = prepayTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}