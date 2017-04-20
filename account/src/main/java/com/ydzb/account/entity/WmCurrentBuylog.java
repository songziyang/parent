package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;


@Entity
@Table(name = "wm_current_buylog")
public class WmCurrentBuylog extends BaseEntity<Long> {

    private static final long serialVersionUID = -3537051496328476177L;

    public static final int DEVICE_SYSTEM = 4;  //设备类型-系统

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "buy_fund")
    private BigDecimal buyFund;

    @Column(name =  "exp_fund")
    private BigDecimal expFund;

    @Column(name = "buy_copies")
    private Integer buyCopies;

    private BigDecimal apr;

    @Column(name =  "grand_apr")
    private BigDecimal grandApr;

    @Column(name = "buy_time")
    private Long buyTime;

    private Integer device;

    public WmCurrentBuylog() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getBuyFund() {
        return buyFund;
    }

    public void setBuyFund(BigDecimal buyFund) {
        this.buyFund = buyFund;
    }

    public BigDecimal getExpFund() {
        return expFund;
    }

    public void setExpFund(BigDecimal expFund) {
        this.expFund = expFund;
    }

    public Integer getBuyCopies() {
        return buyCopies;
    }

    public void setBuyCopies(Integer buyCopies) {
        this.buyCopies = buyCopies;
    }

    public BigDecimal getApr() {
        return apr;
    }

    public void setApr(BigDecimal apr) {
        this.apr = apr;
    }

    public BigDecimal getGrandApr() {
        return grandApr;
    }

    public void setGrandApr(BigDecimal grandApr) {
        this.grandApr = grandApr;
    }

    public Long getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Long buyTime) {
        this.buyTime = buyTime;
    }

    public Integer getDevice() {
        return device;
    }

    public void setDevice(Integer device) {
        this.device = device;
    }
}