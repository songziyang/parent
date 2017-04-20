package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 用户购买队列表
 */
@Entity
@Table(name = "cms_buy_queue")
@DynamicInsert
@DynamicUpdate
public class WmCmsBuyQueue extends BaseEntity<Long> {

    public static final int TYPE_PROFIT = 5;    //购买类型-普通用户复利
    public static final int STATUS_UNDERMATCH = 0;  //状态-待匹配

    /**
     * 用户id
     */
    @Column(name = "userid")
    private Long userId;

    /**
     * 交易冻结记录ID
     */
    @Column(name = "bId")
    private Long bid;

    /**
     * 债权到期ID
     */
    @Column(name = "eqid")
    private Long eqId;

    /**
     * 翟同伙收的债权ID。type为2cid存ucrid。,type为4cid为到期的债权
     */
    @Column(name = "cid")
    private Long cId;

    /**
     * type为2kid存key
     */
    @Column(name = "kid")
    private String kId;

    private BigDecimal amount;  //购买金额
    private Integer type; //购买类型
    private Integer status; //状态

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBid() {
        return bid;
    }

    public void setBid(Long bid) {
        this.bid = bid;
    }

    public Long getEqId() {
        return eqId;
    }

    public void setEqId(Long eqId) {
        this.eqId = eqId;
    }

    public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }

    public String getkId() {
        return kId;
    }

    public void setkId(String kId) {
        this.kId = kId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}