package com.ydzb.account.entity;

import com.ydzb.core.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;


@Entity
@Table(name = "wm_product_current_history")
public class WmProductCurrentHistory extends BaseEntity<Long> {


    //份数
    private Integer copies;

    //利率
    private BigDecimal apr;

    //产品ID
    @Column(name = "product_id")
    private Long productId;

    //产品名称
    @Column(name = "product_name")
    private String productName;

    //发布日期
    @Column(name = "pub_date")
    private Long pubDate;

    public Integer getCopies() {
        return copies;
    }

    public void setCopies(Integer copies) {
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
