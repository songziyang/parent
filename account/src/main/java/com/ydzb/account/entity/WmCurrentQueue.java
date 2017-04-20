package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;


@Entity
@Table(name = "wm_current_queue")
public class WmCurrentQueue extends BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    // 天标预投ID
    @Column(name = "dayloan_prepay_id")
    private Long dayloanPrepayId;

    // 入队时间（带时分秒）
    @Column(name = "enqueue_time")
    private Long enqueueTime;

    // 预投份数
    @Column(name = "prepay_copies")
    private BigDecimal prepayCopies;

    //1是利息
    private Byte priority;

    // 用户ID
    @Column(name = "user_id")
    private Long userId;

    //0、本金 1、体验金
    private Integer type;

    public WmCurrentQueue() {
    }

    public Long getDayloanPrepayId() {
        return dayloanPrepayId;
    }

    public void setDayloanPrepayId(Long dayloanPrepayId) {
        this.dayloanPrepayId = dayloanPrepayId;
    }

    public Long getEnqueueTime() {
        return enqueueTime;
    }

    public void setEnqueueTime(Long enqueueTime) {
        this.enqueueTime = enqueueTime;
    }

    public BigDecimal getPrepayCopies() {
        return prepayCopies;
    }

    public void setPrepayCopies(BigDecimal prepayCopies) {
        this.prepayCopies = prepayCopies;
    }

    public Byte getPriority() {
        return priority;
    }

    public void setPriority(Byte priority) {
        this.priority = priority;
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