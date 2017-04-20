package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 国庆活动记录
 */
@Entity
@Table(name = "wm_activity_shiyi_record")
@DynamicInsert
@DynamicUpdate
public class WmActivityGuoqingRecord extends BaseEntity<Long> {

    public static final int TYPE_GET = 1;   //获得
    public static final int TYPE_USE = 2;   //使用
    public static final int TYPE_EXCHANGE_INTEGRAL = 4; //兑换积分

    private Integer type;
    private Integer fund;
    private Long created;

    @Column(name = "user_id")
    private Long userId;
    @Column(name = "link_id")
    private Long linkId;
    @Column(name = "usable_fund")
    private Integer usableFund;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getFund() {
        return fund;
    }

    public void setFund(Integer fund) {
        this.fund = fund;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getLinkId() {
        return linkId;
    }

    public void setLinkId(Long linkId) {
        this.linkId = linkId;
    }

    public Integer getUsableFund() {
        return usableFund;
    }

    public void setUsableFund(Integer usableFund) {
        this.usableFund = usableFund;
    }
}