package com.ydzb.account.entity;


import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 用户积分记录
 */
@Entity
@Table(name = "wm_user_integral_record")
public class WmUserIntegralRecord extends BaseEntity<Long> {

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 来源去向
     */
    private String fundflow;

    /**
     * 积分
     */
    private BigDecimal integral;

    /**
     * 积分余额
     */
    private BigDecimal balance;

    /**
     * 类别： 1-获取 2-支出
     */
    private Integer type;

    /**
     * 1、完善信息 2、签到 3、投资4、VIP 21、兑换
     */
    @Column(name = "link_type")
    private Integer linkType;

    /**
     * 外链ID
     */
    @Column(name = "link_id")
    private Long linkId;

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

    public String getFundflow() {
        return fundflow;
    }

    public void setFundflow(String fundflow) {
        this.fundflow = fundflow;
    }

    public BigDecimal getIntegral() {
        return integral;
    }

    public void setIntegral(BigDecimal integral) {
        this.integral = integral;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getLinkType() {
        return linkType;
    }

    public void setLinkType(Integer linkType) {
        this.linkType = linkType;
    }

    public Long getLinkId() {
        return linkId;
    }

    public void setLinkId(Long linkId) {
        this.linkId = linkId;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }
}
