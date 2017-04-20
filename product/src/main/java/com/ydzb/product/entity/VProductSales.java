package com.ydzb.product.entity;


import com.ydzb.core.utils.DateUtil;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

/**
 * 产品售出
 * @author sy
 */
@Entity
@Table(name = "vi_product_sales")
public class VProductSales {

    @Id
    private String id;

    /**
     * 购买份额
     */
    @Column(name = "buy_copies")
    private Integer buyCopies;

    /**
     * 产品信息
     */
    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private ProductInfo productInfo;

    /**
     * 购买日期
     */
    @Column(name = "buy_date")
    private Long buyDate;

    @Transient
    private Date buyTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getBuyCopies() {
        return buyCopies;
    }

    public void setBuyCopies(Integer buyCopies) {
        this.buyCopies = buyCopies;
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    public Long getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Long buyDate) {
        this.buyDate = buyDate;
    }

    public Date getBuyTime() {
        return DateUtil.getSystemTimeMillisecond(buyDate);
    }
}
