package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 30亿活动银多币交易记录
 */
@Entity
@Table(name = "wm_silver_trace_record_thirty")
@DynamicInsert
@DynamicUpdate
public class WmSilverTradeRecordThirty extends BaseEntity<Long> {

    public static final int TYPE_GET = 1;   //类型-获得
    public static final int TYPE_USE = 2;   //类型-使用

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    private Integer type;   //类型 1、获得 2、使用3、退款

    /**
     * 外链ID 类型1定存记录ID 类型2兑换记录ID
     */
    @Column(name = "link_id")
    private Long linkId;

    private Integer fund;
    /**
     * 剩余金额
     */
    @Column(name = "usable_fund")
    private Integer usableFund;
    private Long created;

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

    public Long getLinkId() {
        return linkId;
    }

    public void setLinkId(Long linkId) {
        this.linkId = linkId;
    }

    public Integer getFund() {
        return fund;
    }

    public void setFund(Integer fund) {
        this.fund = fund;
    }

    public Integer getUsableFund() {
        return usableFund;
    }

    public void setUsableFund(Integer usableFund) {
        this.usableFund = usableFund;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }
}