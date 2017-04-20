package com.ydzb.product.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.user.entity.User;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 活期宝排队表
 */
@Entity
@Table(name = "wm_current_queue")
public class CurrentQueue extends BaseEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 活期宝预投
     */
    @Column(name = "dayloan_prepay_id")
    private Long currentPrepayId;

    /**
     * 活期宝预投份数
     */
    @Column(name = "prepay_copies")
    private BigDecimal prepayCopies;

    /**
     * 优先级别
     */
    private Byte priority = 0;

    @Column(name = "enqueue_time")
    private Long enqueueTime;

    /**
     * 类型：0-本金  1-体验金
     */
    private Byte type;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getCurrentPrepayId() {
        return currentPrepayId;
    }

    public void setCurrentPrepayId(Long currentPrepayId) {
        this.currentPrepayId = currentPrepayId;
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

    public Long getEnqueueTime() {
        return enqueueTime;
    }

    public void setEnqueueTime(Long enqueueTime) {
        this.enqueueTime = enqueueTime;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }
}
