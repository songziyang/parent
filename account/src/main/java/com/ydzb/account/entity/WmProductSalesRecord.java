package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * 产品销售统计数据
 */
@Entity
@Table(name = "wm_product_sales_record")
public class WmProductSalesRecord extends BaseEntity<Long> {

    /**
     * 产品
     */
    @Column(name = "product_id")
    private Long productId;

    /**
     * 发布时间
     */
    @Column(name = "release_time")
    private Long releaseTime;

    /**
     * 发布金额
     */
    @Column(name = "release_amount")
    private Integer releaseAmount;

    /**
     * 销售金额
     */
    @Column(name = "sales_amount")
    private Integer salesAmount;

    /**
     * 售罄时间
     */
    @Column(name = "sales_time")
    private Long salesTime;

    /**
     * 创建时间
     */
    private Long created;

    /**
     * 1、在售 2、售罄
     */
    private Integer status;

    /**
     * 产品天数
     */
    private Integer days;

    /**
     * 产品类型 1 活期 2定存
     */
    private Integer type;


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Long releaseTime) {
        this.releaseTime = releaseTime;
    }

    public Integer getReleaseAmount() {
        return releaseAmount;
    }

    public void setReleaseAmount(Integer releaseAmount) {
        this.releaseAmount = releaseAmount;
    }

    public Integer getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(Integer salesAmount) {
        this.salesAmount = salesAmount;
    }

    public Long getSalesTime() {
        return salesTime;
    }

    public void setSalesTime(Long salesTime) {
        this.salesTime = salesTime;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}