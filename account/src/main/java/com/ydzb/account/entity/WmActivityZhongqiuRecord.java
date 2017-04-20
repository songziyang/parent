package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 中秋节活动
 */
@Entity
@Table(name = "wm_activity_zhongqiu_record")
@DynamicInsert
@DynamicUpdate
public class WmActivityZhongqiuRecord extends BaseEntity<Long> {

    /**
     * 用户
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 类型 1、获得 2、使用3、退款
     */
    private Integer type;

    /**
     * 外链ID 类型1定存记录ID 类型2兑换记录ID
     */
    @Column(name = "link_id")
    private Long linkId;

    /**
     * 使用桂花
     */
    private Integer fund;

    /**
     * 可用桂花
     */
    @Column(name = "usable_fund")
    private Integer usableFund;

    /**
     * 创建时间
     */
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