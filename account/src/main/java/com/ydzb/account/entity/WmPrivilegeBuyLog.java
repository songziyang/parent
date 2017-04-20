package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 新手特权购买日志
 */
@Entity
@Table(name = "wm_privilege_buylog")
@DynamicInsert
@DynamicUpdate
public class WmPrivilegeBuyLog extends BaseEntity<Long> {

    public static final int DEVICE_SYSTEM = 4;  //设备-系统

    /**
     * 产品id
     */
    @Column(name = "product_id")
    private Long productId;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 购买资金
     */
    @Column(name = "buy_fund")
    private BigDecimal buyFund;

    /**
     * 购买份数
     */
    @Column(name = "buy_copies")
    private Integer buyCopies;

    /**
     * 购买时间
     */
    @Column(name = "buy_time")
    private Long buyTime;

    private BigDecimal apr; //利率
    private Integer device; //设备

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getBuyFund() {
        return buyFund;
    }

    public void setBuyFund(BigDecimal buyFund) {
        this.buyFund = buyFund;
    }

    public Integer getBuyCopies() {
        return buyCopies;
    }

    public void setBuyCopies(Integer buyCopies) {
        this.buyCopies = buyCopies;
    }

    public Long getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Long buyTime) {
        this.buyTime = buyTime;
    }

    public BigDecimal getApr() {
        return apr;
    }

    public void setApr(BigDecimal apr) {
        this.apr = apr;
    }

    public Integer getDevice() {
        return device;
    }

    public void setDevice(Integer device) {
        this.device = device;
    }
}