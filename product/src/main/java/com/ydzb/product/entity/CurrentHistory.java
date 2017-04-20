package com.ydzb.product.entity;

import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 活期宝往期记录
 * @author sy
 */
@Entity
@Table(name = "wm_product_current_history")
public class CurrentHistory extends BaseEntity<Long> {

    /**
     * 份数
     */
    private Long copies;

    /**
     * 利率
     */
    private BigDecimal apr;

    /**
     * 产品id
     */
    @Column(name = "product_id")
    private Long productId;

    /**
     * 产品名称
     */
    @Column(name = "product_name")
    private String productName;

    /**
     * 发布日期
     */
    @Column(name = "pub_date")
    private Long pubDate;

    public CurrentHistory() {

    }

    public Long getCopies() {
        return copies;
    }

    public void setCopies(Long copies) {
        this.copies = copies;
    }

    public BigDecimal getApr() {
        return apr;
    }

    public void setApr(BigDecimal apr) {
        this.apr = apr;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getPubDate() {
        return pubDate;
    }

    public void setPubDate(Long pubDate) {
        this.pubDate = pubDate;
    }
}