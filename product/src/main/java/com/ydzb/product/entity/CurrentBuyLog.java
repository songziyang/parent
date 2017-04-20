package com.ydzb.product.entity;

import com.ydzb.core.entity.BaseEntity;
import com.ydzb.core.utils.DateUtil;
import com.ydzb.user.entity.User;
import javassist.NotFoundException;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 活期宝购买日志
 * @author sy
 */
@Entity
@Table(name = "wm_current_buylog")
public class CurrentBuyLog extends BaseEntity<Long> {

    public static final byte MOBILE = 1;    //移动
    public static final byte WEBSITE = 2;   //网站
    public static final byte WECHAT = 3;    //微信
    public static final byte SYSTEM = 4;    //系统

    /**
     * 对应产品
     */
    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private ProductInfo productInfo;

    /**
     * 用户
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private User user;

    /**
     * 购买金额
     */
    @Column(name = "buy_fund")
    private BigDecimal buyFund;

    /**
     * 体验金购买金额
     */
    @Column(name = "exp_fund")
    private BigDecimal expFund;

    /**
     * 购买份数
     */
    @Column(name = "buy_copies")
    private Integer buyCopies;

    /**
     * 利率
     */
    private BigDecimal apr;

    /**
     * 加息利率
     */
    @Column(name = "grand_apr")
    private BigDecimal grandApr;

    /**
     * 购买时间
     */
    @Column(name = "buy_time")
    private Long buyTime;

    @Transient
    private Date buyDate;

    /**
     * 设备类型：1-移动    2-网站    3-微信    4-系统
     */
    private Byte device;

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Byte getDevice() {
        return device;
    }

    public void setDevice(Byte device) {
        this.device = device;
    }

    public Date getBuyDate() {
        return DateUtil.getSystemTimeMillisecond(buyTime);
    }
}