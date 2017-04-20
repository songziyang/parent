package com.ydzb.user.entity;

import com.ydzb.core.entity.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 用户赠与红包收益记录
 */
@Table
@Entity(name = "wm_user_func_grand_profit_record")
public class UserProfitRecord extends BaseEntity<Long> {

    /**
     * 用户
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 产品
     */
    @Column(name = "product_id")
    private Long productId;

    private BigDecimal fund;    //金额

    /**
     * 类型 1、活期宝 2、定存宝
     */
    @Column(name = "ptype")
    private Integer pType;

    private Integer type;   //类别 1-加息券 2-体验机

    /**
     * 记录日期
     */
    @Column(name = "record_date")
    private Long recordDate;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getFund() {
        return fund;
    }

    public void setFund(BigDecimal fund) {
        this.fund = fund;
    }

    public Integer getpType() {
        return pType;
    }

    public void setpType(Integer pType) {
        this.pType = pType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Long recordDate) {
        this.recordDate = recordDate;
    }
}